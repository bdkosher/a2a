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
	
	/** The list of red apples generated from the XML input source. */
	private final List<RedApple> redApples;
	
	/** The list of red apples generated from the XML input source. */
	private final List<GreenApple> greenApples;
	
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
						redApples.add(a);
						apple = null;
						quipBuilder = null;
					}
				} else if (apple instanceof GreenApple 
						&& "green".equalsIgnoreCase(qName)) {
					log.finer("Finalizing GreenApple and adding to list.");
					greenApples.add((GreenApple)apple);
					apple = null;
				}
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (apple != null) {
				if (apple instanceof RedApple) {
					String chars = new String(ch, start, length).trim();
					/* chars within red apple are part of quip. */
					log.finer("Appending characters " + chars + " to quip.");
					quipBuilder.append(chars);
				} else if (apple instanceof GreenApple) {
					String chars = new String(ch, start, length).trim();
					log.finer("Adding synonyms " + chars + " to GreenApple.");
					/* chars withing green apple are comma-separated list of synonyms. */
					GreenApple a = (GreenApple)apple;
					for (StringTokenizer st = new StringTokenizer(chars, ","); 
							st.hasMoreTokens(); 
							a.addSynonym(st.nextToken().trim()));
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
		this.redApples = new ArrayList<RedApple>();
		this.greenApples = new ArrayList<GreenApple>();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(false);
		factory.setXIncludeAware(false);
		SAXParser parser = factory.newSAXParser();
		parser.parse(source, new AppleHandler());
	}

	/**
	 * @return the red apples
	 */
	public List<RedApple> getRedApples() {
		return redApples;
	}
	
	/**
	 * @return the green apples
	 */
	public List<GreenApple> getGreenApples() {
		return greenApples;
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
