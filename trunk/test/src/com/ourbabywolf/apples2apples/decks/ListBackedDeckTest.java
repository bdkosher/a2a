package com.ourbabywolf.apples2apples.decks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import org.junit.Test;

import com.ourbabywolf.apples2apples.RedApple;

/**
 * 
 * @author joe@ourbabywolf.com
 *
 */
public class ListBackedDeckTest {
	
	private static final Logger log = Logger.getLogger(ListBackedDeckTest.class.toString());

	@Test
	public void testSelfReplenishing() {
		ListBackedDeck<RedApple> deck = new ListBackedDeck<RedApple>(true);
		deck.add(new RedApple("word1"));
		deck.add(new RedApple("word2"));
		deck.shuffle();
		
		for (int i = 0; i < 20; ++i) {
			RedApple apple = deck.draw();
			log.finer(apple.toString());
			assertNotNull("Apple " + i + " shouldn't be null.", apple);
			assertFalse("Deck shouldn't be exhausted.", deck.isExhausted());
		}
	}
	
	@Test
	public void testNonSelfReplenishing() {
		ListBackedDeck<RedApple> deck = new ListBackedDeck<RedApple>(false);
		deck.add(new RedApple("word1"));
		deck.add(new RedApple("word2"));
		deck.shuffle();
		
		RedApple apple = deck.draw();
		log.finer(apple.toString());
		assertNotNull("Apple 1 shouldn't be null.", apple);
		assertFalse("Deck shouldn't be exhausted.", deck.isExhausted());
		apple = deck.draw();
		log.finer(apple.toString());
		assertNotNull("Apple 2 shouldn't be null.", apple);
		assertNull("Apple 3 should be null.", deck.draw());
		assertTrue("Deck should be exhausted.", deck.isExhausted());
	}
	
	@Test
	public void testDescription() {
		String description = "test";
		ListBackedDeck<RedApple> deck = new ListBackedDeck<RedApple>(false, 1, description);
		assertEquals("Description should be set.", description, deck.getDescription());
	}
}
