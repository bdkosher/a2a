/*
 * DeckFactory.java - created Aug 26, 2006 12:32:20 AM
 * $Id$
 */
package com.ourbabywolf.apples2apples.decks;

import com.ourbabywolf.apples2apples.Deck;
import com.ourbabywolf.apples2apples.GreenApple;
import com.ourbabywolf.apples2apples.RedApple;

/**
 * Convenience class for obtaining decks for an a2a game.
 * 
 * @author joe@ourbabywolf.com
 *
 */
public class DeckFactory {
	
	/**
	 * An enumeration of the various standard Apple2Apple deck sets,
	 * plus some custom decks.
	 * 
	 * @author joe@ourbabywolf.com
	 */
	public enum DeckSet {
		BASIC_1999("basic-1999"),
		BASIC_2001("basic-2001"),
		EXPANSION1_1999("expansion1-1999"),
		EXPANSION1_2001("expansion1-2001"),
		EXPANSION2_2000("expansion2-2000"),
		EXPANSION3_2001("expansion3-2001"),
		EXPANSION4_2002("expansion4-2002"),
		JUNIOR_2002("junior-2002"),
		JUNIOR_2004("junior-2004"),
		JUNIOR9PLUS_2001("junior9plus-2001"),
		JUNIOR9PLUS_2005("junior9plus2nd-2005"),
		PARTY_2004("party-2004"),
		PARTYEXPANSION1_2004("partyexpansion1-2004"),
		JOESCUSTOM("joecustom");
		
		/** The portion of the fileName unique to the deck set. */
		private String fileNamePart;
		
		/**
		 * @param fileNamePart
		 */
		private DeckSet(String fileNamePart) {
			this.fileNamePart = fileNamePart;
		}
	}
	
	/**
	 * Uses the information parsed in the reader to generate a description
	 * for the deck.
	 * 
	 * @param reader
	 * @return
	 */
	private static String getDeckDescription(SAXApplesReader reader) {
		return reader.getVersion() + " (" + reader.getPrinting() + " printing)";
	}
	
	/**
	 * Obtains a reader, wrapping any exceptions with a RuntimeException.
	 * 
	 * @param fileName
	 * @return
	 */
	private static SAXApplesReader getReader(String fileName) {
		try {
			return new SAXApplesReader(
					DeckFactory.class.getClassLoader().getResourceAsStream(fileName));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Returns a deck of Red Apples from the given file name.  If selfReplenishing
	 * is true, the deck returned will not be exhausted.
	 * 
	 * @param fileName
	 * @param selfReplenishing
	 * @return deck
	 */
	public static Deck<RedApple> getRedDeck(String fileName, boolean selfReplenishing) {
		SAXApplesReader reader = getReader(fileName);
		ListBackedDeck<RedApple> deck = 
				new ListBackedDeck<RedApple>(selfReplenishing, 
						getDeckDescription(reader));
		deck.addAll(reader.getRedApples());
		return deck;
	}
	
	/**
	 * Returns a deck of Green Apples from the given file name.  If selfReplenishing
	 * is true, the deck returned will not be exhausted.
	 * 
	 * @param fileName
	 * @param selfReplenishing
	 * @return deck
	 */
	public static Deck<GreenApple> getGreenDeck(String fileName, boolean selfReplenishing) {
		SAXApplesReader reader = getReader(fileName);
		ListBackedDeck<GreenApple> deck = 
				new ListBackedDeck<GreenApple>(selfReplenishing, 
						getDeckDescription(reader));
		deck.addAll(reader.getGreenApples());
		return deck;
	}

	/**
	 * Returns a deck of Red Apples from the given A2A set.  If selfReplenishing
	 * is true, the deck returned will not be exhausted.
	 * 
	 * @param deck set
	 * @param selfReplenishing
	 * @return deck
	 */
	public static Deck<RedApple> getRedDeck(DeckSet set, boolean selfReplenishing) {
		return getRedDeck("red-" + set.fileNamePart + ".xml", selfReplenishing);
	}
	
	/**
	 * Returns a deck of Green Apples from the given A2A set.  If selfReplenishing
	 * is true, the deck returned will not be exhausted.
	 * 
	 * @param deck set
	 * @param selfReplenishing
	 * @return deck
	 */
	public static Deck<GreenApple> getGreenDeck(DeckSet set, boolean selfReplenishing) {
		return getGreenDeck("green-" + set.fileNamePart + ".xml", selfReplenishing);
	}
	
}
