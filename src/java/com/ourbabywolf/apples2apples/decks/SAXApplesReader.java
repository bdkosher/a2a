/*
 * XMLAppleReader.java - created Aug 7, 2006 11:38:47 AM
 * $Id$
 */
package com.ourbabywolf.apples2apples.decks;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ourbabywolf.apples2apples.Apple;
import com.ourbabywolf.apples2apples.GreenApple;
import com.ourbabywolf.apples2apples.RedApple;

/**
 * Uses SAX to parse the XML apple files
 * 
 * @author joe@ourbabywolf.com
 *
 */
public class SAXApplesReader {
	
	/** Logger */
	private final Logger log = Logger.getLogger(SAXApplesReader.class.getName());
	
	/** Holds the value of the version attribute of the XML doc. */
	private String version = null;
	
	/** Holds the value of the printing attribute of the XML doc. */
	private String printing = null;
	
	/** The list of apples generated from the XML input source. */
	private final List<Apple> apples; 
	
	/*private class LoggingHandler extends DefaultHandler {

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			StringBuilder sb = new StringBuilder();
			sb.append("characters\n\t");
			sb.append(new String(ch, start, length));
			log.finer(sb.toString());
		}

		@Override
		public void endDocument() throws SAXException {
			log.finer("EndDocument");
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			StringBuilder sb = new StringBuilder();
			sb.append("EndElement\n\t");
			sb.append("URI: ").append(uri);
			sb.append("\n\tLocalName: ").append(localName);
			sb.append("\n\tQName: ").append(qName);
			log.finer(sb.toString());
		}

		@Override
		public void startDocument() throws SAXException {
			log.finer("StartDocument");
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			StringBuilder sb = new StringBuilder();
			sb.append("StartElement\n\t");
			sb.append("URI: ").append(uri);
			sb.append("\n\tLocalName: ").append(localName);
			sb.append("\n\tQName: ").append(qName);
			sb.append("\n\tAttributes:");
			if (attributes == null) {
				sb.append("null");
			} else {
				for (int i = 0; i < attributes.getLength(); ++i) {
					sb.append(attributes.getValue(i)).append(" ");
				}
			}
			log.finer(sb.toString());
		}
		
	}*/
	
	/**
	 * Contains the rules used to generate Apples from XML markup.
	 * 
	 * @author joe@ourbabywolf.com
	 */
	private class AppleHandler extends DefaultHandler {

		/** The Apple that's being generated. */
		private Apple apple = null;
		
		/** Buffers the chars and the markup inside of the red apple's quip. */
		private StringBuilder quipBuilder = null;
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			log.finer("Encountered XML element " + qName);
			if ("redapples".equalsIgnoreCase(qName) || "greenapples".equalsIgnoreCase(qName)) {
				version = attributes.getValue("version");
				log.finer("Set version to " + version);
				printing = attributes.getValue("printing");
				log.finer("Set printing to " + printing);
			} else if ("red".equalsIgnoreCase(qName)) {
				log.finer("Generating new RedApple");
				apple = new RedApple(attributes.getValue("word"));
				quipBuilder = new StringBuilder();
			} else if ("green".equalsIgnoreCase(qName)) {
				log.finer("Generating new GreenApple");
				apple = new GreenApple(attributes.getValue("word"));
			} else if (apple != null && apple instanceof RedApple) {
				log.finer("Encountered quip markup " + qName);
				/* Processing of <br> elements delayed until endElement method. */
				if ("i".equalsIgnoreCase(qName) || "b".equalsIgnoreCase(qName)) {
					log.finer("Writing " + qName + " start tag to quip builder.");
					quipBuilder.append("<").append(qName).append(">");
				}
			} else {
				log.finer("Unanticipated element " + qName + "--ignoring");
			}
		}
		
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (apple != null) {
				if (apple instanceof RedApple) {
					if ("br".equals(qName)) {
						log.finer("Writing br tag to quip builder.");
						quipBuilder.append("<br/>");
					} else if ("i".equalsIgnoreCase(qName) || "b".equalsIgnoreCase(qName)) {
						log.finer("Writing " + localName + " end tag to quip builder.");
						quipBuilder.append("</").append(qName).append(">");
					} else if ("red".equalsIgnoreCase(qName)) {
						RedApple a = (RedApple)apple;
						a.setQuip(quipBuilder.toString());
						log.finer("Finalizing RedApple \"" + a.getWord() + "\"and adding to list. Quip=\n\t" + a.getQuip());
						apples.add(apple);
						apple = null;
						quipBuilder = null;
					}
				} else if (apple instanceof GreenApple 
						&& "green".equalsIgnoreCase(qName)) {
					log.finer("Finalizing GreenApple and adding to list.");
					apples.add(apple);
					apple = null;
				}
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (apple != null) {
				if (apple instanceof RedApple) {
					/* chars within red apple are part of quip. */
					log.finer("Appending characters " + new String(ch) + " to quip.");
					quipBuilder.append(new String(ch, start, length));
				} else if (apple instanceof GreenApple) {
					log.finer("Adding synonyms " + new String(ch) + " to GreenApple.");
					/* chars withing green apple are comma-separated list of synonyms. */
					GreenApple a = (GreenApple)apple;
					for (StringTokenizer st = new StringTokenizer(new String(ch), ","); 
							st.hasMoreTokens(); 
							a.addSynonym(st.nextToken()));
				} else {
					/* don't know what these characters are for. */
					log.finer("Unexpected characters encountered: " + new String(ch));
				}
			} else {
				log.finer("Unexpected characters encountered outside of apple markup: " + new String(ch));
			}
		}
		
	}

	/**
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * 
	 */
	public SAXApplesReader(InputStream source) 
			throws ParserConfigurationException, SAXException, IOException {
		this.apples = new ArrayList<Apple>();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(false);
		factory.setXIncludeAware(false);
		SAXParser parser = factory.newSAXParser();
		parser.parse(source, new AppleHandler());
	}

	/**
	 * @return the apples
	 */
	public List<Apple> getApples() {
		return apples;
	}

	/**
	 * @return the printing
	 */
	public String getPrinting() {
		return printing;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	
}
