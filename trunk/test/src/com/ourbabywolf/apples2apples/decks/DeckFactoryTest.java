/*
 * DeckFactoryTest.java - created Aug 26, 2006 5:03:14 PM
 * $Id$
 */
package com.ourbabywolf.apples2apples.decks;

import static org.junit.Assert.assertNotNull;

import java.util.logging.Logger;

import org.junit.Test;

import com.ourbabywolf.apples2apples.Deck;
import com.ourbabywolf.apples2apples.GreenApple;
import com.ourbabywolf.apples2apples.RedApple;

/**
 * @author joe@ourbabywolf.com
 *
 */
public class DeckFactoryTest {
	
	private static final Logger log = Logger.getLogger(DeckFactoryTest.class.toString());
	
	private void logGreenDeck(Deck<GreenApple> deck) {
		if (deck != null) {
			log.info("-------------------------------");
			log.info("-------GreenApple Deck---------");
			log.info(deck.getDescription());
			while (!deck.isExhausted()) {
				GreenApple apple = deck.draw();
				log.info("\t" + apple.getWord() + " - " + apple.getSynonyms());
			}
		}
	}
	
	private void logRedDeck(Deck<RedApple> deck) {
		if (deck != null) {
			log.info("-------------------------------");
			log.info("--------RedApple Deck----------");
			log.info(deck.getDescription());
			while (!deck.isExhausted()) {
				RedApple apple = deck.draw();
				log.info("\t" + apple.getWord() + " - " + apple.getQuip());
			}
			log.info("-------------------------------");
		}
	}

	@Test
	public void testBasic1999Decks() {
		Deck<GreenApple> green = 
				DeckFactory.getGreenDeck(DeckFactory.DeckSet.BASIC_1999, false);
		assertNotNull("Green Deck should't be null.", green);
		logGreenDeck(green);
		Deck<RedApple> red = 
			DeckFactory.getRedDeck(DeckFactory.DeckSet.BASIC_1999, false);
		assertNotNull("Red Deck shouldn't be null.", red);
		logRedDeck(red);
	}
	
	@Test
	public void testBasic2001Decks() {
		Deck<GreenApple> green = 
			DeckFactory.getGreenDeck(DeckFactory.DeckSet.BASIC_2001, false);
		assertNotNull("Green Deck shouldn't be null.", green);
		logGreenDeck(green);
		Deck<RedApple> red = 
			DeckFactory.getRedDeck(DeckFactory.DeckSet.BASIC_2001, false);
		assertNotNull("Red Deck shouldn't be null.", red);
		logRedDeck(red);
	}
	
	@Test
	public void testExpansion11999Decks() {
		Deck<GreenApple> green = 
			DeckFactory.getGreenDeck(DeckFactory.DeckSet.EXPANSION1_1999, false);
		assertNotNull("Green Deck shouldn't be null.", green);
		logGreenDeck(green);
		Deck<RedApple> red = 
			DeckFactory.getRedDeck(DeckFactory.DeckSet.EXPANSION1_1999, false);
		assertNotNull("Red Deck shouldn't be null.", red);
		logRedDeck(red);
	}
	
	@Test
	public void testExpansion12001Decks() {
		Deck<GreenApple> green = 
			DeckFactory.getGreenDeck(DeckFactory.DeckSet.EXPANSION1_2001, false);
		assertNotNull("Green Deck shouldn't be null.", green);
		logGreenDeck(green);
		Deck<RedApple> red = 
			DeckFactory.getRedDeck(DeckFactory.DeckSet.EXPANSION1_2001, false);
		assertNotNull("Red Deck shouldn't be null.", red);
		logRedDeck(red);
	}
	
	@Test
	public void testExpansion22000Decks() {
		Deck<GreenApple> green = 
			DeckFactory.getGreenDeck(DeckFactory.DeckSet.EXPANSION2_2000, false);
		assertNotNull("Green Deck shouldn't be null.", green);
		logGreenDeck(green);
		Deck<RedApple> red = 
			DeckFactory.getRedDeck(DeckFactory.DeckSet.EXPANSION2_2000, false);
		assertNotNull("Red Deck shouldn't be null.", red);
		logRedDeck(red);
	}
	
	@Test
	public void testExpansion32001Decks() {
		Deck<GreenApple> green = 
			DeckFactory.getGreenDeck(DeckFactory.DeckSet.EXPANSION3_2001, false);
		assertNotNull("Green Deck shouldn't be null.", green);
		logGreenDeck(green);
		Deck<RedApple> red = 
			DeckFactory.getRedDeck(DeckFactory.DeckSet.EXPANSION3_2001, false);
		assertNotNull("Red Deck shouldn't be null.", red);
		logRedDeck(red);
	}
	
	@Test
	public void testExpansion42002Decks() {
		Deck<GreenApple> green = 
			DeckFactory.getGreenDeck(DeckFactory.DeckSet.EXPANSION4_2002, false);
		assertNotNull("Green Deck shouldn't be null.", green);
		logGreenDeck(green);
		Deck<RedApple> red = 
			DeckFactory.getRedDeck(DeckFactory.DeckSet.EXPANSION4_2002, false);
		assertNotNull("Red Deck shouldn't be null.", red);
		logRedDeck(red);
	}
	
	@Test
	public void testJunior2002Decks() {
		Deck<GreenApple> green = 
			DeckFactory.getGreenDeck(DeckFactory.DeckSet.JUNIOR_2002, false);
		assertNotNull("Green Deck shouldn't be null.", green);
		logGreenDeck(green);
		Deck<RedApple> red = 
			DeckFactory.getRedDeck(DeckFactory.DeckSet.JUNIOR_2002, false);
		assertNotNull("Red Deck shouldn't be null.", red);
		logRedDeck(red);
	}
	
	@Test
	public void testJunior2005Decks() {
		Deck<GreenApple> green = 
			DeckFactory.getGreenDeck(DeckFactory.DeckSet.JUNIOR_2004, false);
		assertNotNull("Green Deck shouldn't be null.", green);
		logGreenDeck(green);
		Deck<RedApple> red = 
			DeckFactory.getRedDeck(DeckFactory.DeckSet.JUNIOR_2004, false);
		assertNotNull("Red Deck shouldn't be null.", red);
		logRedDeck(red);
	}
	
	@Test
	public void testJunior9Plus2001Decks() {
		Deck<GreenApple> green = 
			DeckFactory.getGreenDeck(DeckFactory.DeckSet.JUNIOR9PLUS_2001, false);
		assertNotNull("Green Deck shouldn't be null.", green);
		logGreenDeck(green);
		Deck<RedApple> red = 
			DeckFactory.getRedDeck(DeckFactory.DeckSet.JUNIOR9PLUS_2001, false);
		assertNotNull("Red Deck shouldn't be null.", red);
		logRedDeck(red);
	}
	
	@Test
	public void testJunior9Plus2005Decks() {
		Deck<GreenApple> green = 
			DeckFactory.getGreenDeck(DeckFactory.DeckSet.JUNIOR9PLUS_2005, false);
		assertNotNull("Green Deck shouldn't be null.", green);
		logGreenDeck(green);
		Deck<RedApple> red = 
			DeckFactory.getRedDeck(DeckFactory.DeckSet.JUNIOR9PLUS_2005, false);
		assertNotNull("Red Deck shouldn't be null.", red);
		logRedDeck(red);
	}
	
	@Test
	public void testParty2004Decks() {
		Deck<GreenApple> green = 
			DeckFactory.getGreenDeck(DeckFactory.DeckSet.PARTY_2004, false);
		assertNotNull("Green Deck shouldn't be null.", green);
		logGreenDeck(green);
		Deck<RedApple> red = 
			DeckFactory.getRedDeck(DeckFactory.DeckSet.PARTY_2004, false);
		assertNotNull("Red Deck shouldn't be null.", red);
		logRedDeck(red);
	}
	
	@Test
	public void testPartyExpansion12004Decks() {
		Deck<GreenApple> green = 
			DeckFactory.getGreenDeck(DeckFactory.DeckSet.PARTYEXPANSION1_2004, false);
		assertNotNull("Green Deck shouldn't be null.", green);
		logGreenDeck(green);
		Deck<RedApple> red = 
			DeckFactory.getRedDeck(DeckFactory.DeckSet.PARTYEXPANSION1_2004, false);
		assertNotNull("Red Deck shouldn't be null.", red);
		logRedDeck(red);
	}
}
