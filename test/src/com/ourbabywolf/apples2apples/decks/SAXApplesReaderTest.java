/*
 * SAXApplesReaderTest.java - created Aug 7, 2006 3:43:10 PM
 * $Id$
 */
package com.ourbabywolf.apples2apples.decks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import com.ourbabywolf.apples2apples.Apple;
import com.ourbabywolf.apples2apples.GreenApple;
import com.ourbabywolf.apples2apples.RedApple;

/**
 * @author joe@ourbabywolf.com
 *
 */
public class SAXApplesReaderTest {
	
	public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(SAXApplesReaderTest.class);
    }
	
	private final Logger log = Logger.getLogger(SAXApplesReaderTest.class.getName());
	
	@Test
	public void testParse() throws Exception {
		SAXApplesReader reader = new SAXApplesReader(
				this.getClass().getClassLoader().getResourceAsStream("red-basic-2001.xml"));
		Logger l = LogManager.getLogManager().getLogger(SAXApplesReader.class.getName());
		l.setLevel(Level.FINER);
		assertNotNull("Apples shouldn't be null.", reader.getRedApples());
		assertFalse("There should be some apples.", reader.getRedApples().isEmpty());
		log.info("*****************************");
		for (Apple a : reader.getRedApples()) {
			log.info(a.getClass().getName() + "\t" + a.getWord());
			if (a instanceof RedApple) {
				log.info(((RedApple)a).getQuip());
			} else if (a instanceof GreenApple) {
				log.info("" + ((GreenApple)a).getSynonyms());
			}
		}
		
	}
}
