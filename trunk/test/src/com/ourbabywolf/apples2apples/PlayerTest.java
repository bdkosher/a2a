/*
 * PlayerTest.java - created Jul 29, 2006 12:23:00 AM
 * $Id$
 */
package com.ourbabywolf.apples2apples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * 
 * @author joe@ourbabywolf.com
 *
 */
public class PlayerTest {

	@Test
	public void testEquality() {
		Player p1 = new Player("joe");
		Player p2 = new Player("joe");
		Player p3 = new Player("katie");
		assertEquals("Players w/ same id should equal.", p1, p2);
		assertFalse("Players w/ diff ids should not equal.", p2.equals(p3));
	}
	
	@Test
	public void testHashCode() {
		Player p1 = new Player("joe");
		Player p2 = new Player("joe");
		Player p3 = new Player("katie");
		assertTrue("Same players should have same hash.", p1.hashCode() == p2.hashCode());
		assertFalse("Same players should have same hash.", p1.hashCode() == p3.hashCode());
		
		Set<Player> players = new HashSet<Player>(3);
		assertTrue("Can add joe 1", players.add(p1));
		assertFalse("Can't add joe 2", players.add(p2));
		assertTrue("Can add katie", players.add(p3));
	}
	
	@Test
	public void testPlayerSetting() {
		Set<Player> players = new HashSet<Player>();
		assertTrue("Player should be addable to set.", players.add(new Player("joe")));
		assertTrue("Set of players contains player joe.", players.contains(new Player("joe")));
		assertFalse("Adding identical player to set shouldn't change it.", players.add(new Player("joe")));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullId() {
		new Player(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEmptyId() {
		new Player("");
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testGetHandUnmodifiability() {
		Player p = new Player("p");
		p.getHand().add(new RedApple("test"));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testGetGreenApplesUnmodifiability() {
		Player p = new Player("p");
		p.getGreenApples().add(new GreenApple("test"));
	}
	
	@Test
	public void testNick() {
		Player caleb = new Player("caleb", null);
		assertNotNull("Nick should never be null.", caleb.getNick());
		assertEquals("If null nick, nick should be id.", caleb.getId(), caleb.getNick());
		caleb.setNick("Super Captain Caleb");
		assertFalse("Differing nick and id should be different.", caleb.getId().equals(caleb.getNick()));
	}
	
	@Test
	public void testHandModification() {
		Player joshua = new Player("joshua");
		assertTrue("Hand should be empty.", joshua.getHand().isEmpty());
		
		RedApple ra = new RedApple("word");
		joshua.dealApple(ra);
		assertEquals("Unexpected deal hand size. Should be 1", 1, joshua.getHand().size());
		joshua.dealApple(ra);
		assertEquals("Unexpected deal hand size. Should be 2. Dups allowed", 2, joshua.getHand().size());
		joshua.dealApple(new RedApple("different"));
		assertEquals("Unexpected deal hand size. Should be 3. Non dups allowed", 3, joshua.getHand().size());
		
		joshua.playApple(ra);
		assertEquals("Unexpected play hand size. Should be 2.", 2, joshua.getHand().size());
		joshua.playApple(ra);
		assertEquals("Unexpected play hand size. Should be 1.", 1, joshua.getHand().size());
		joshua.playApple(new RedApple("different"));
		assertEquals("Unexpected play hand size. Should be 0.", 0, joshua.getHand().size());
		
		assertTrue("Hand should be empty after exhausted.", joshua.getHand().isEmpty());
	}
	
	@Test
	public void testHandInspection() {
		Player katie = new Player("katie");
		assertNull("Null, never index out of bounds.", katie.getAppleFromHand(1));
		String word = "Mommy";
		RedApple ra = new RedApple(word);
		katie.dealApple(ra);
		assertNotNull("one apple hand shouldn't return null by idx 0.", katie.getAppleFromHand(0));
		assertEquals("successful inspection by index shouldn't change hand size", 1, katie.getHand().size());
		assertEquals("one apple hand should return correct apple by idx 0.", ra, katie.getAppleFromHand(0));
		assertNull("one apple hand should return null by idx 1.", katie.getAppleFromHand(1));
		assertEquals("unsuccessful inspection by index shouldn't change hand size", 1, katie.getHand().size());
		assertNotNull("one apple hand shouldn't return null by word.", katie.getAppleFromHand(word));
		assertEquals("successful inspection by word shouldn't change hand size", 1, katie.getHand().size());
		assertEquals("one apple hand should return correct apple by word.", ra, katie.getAppleFromHand(word));
		assertNull("one apple hand should return null by unkown word.", katie.getAppleFromHand(word + "bad"));
		assertEquals("unsuccessful inspection by word shouldn't change hand size", 1, katie.getHand().size());
		assertEquals("indexing and by word should yield same result.", katie.getAppleFromHand(0), katie.getAppleFromHand(word));
		
		katie.dealApple(ra);
		assertEquals("Duplicate red apples should be allowed.", 2, katie.getHand().size());
		assertEquals("two apple hand w/ dups should return correct apple by idx 0.", ra, katie.getAppleFromHand(0));
		assertEquals("two apple hand w/ dups should return correct apple by idx 1.", ra, katie.getAppleFromHand(1));
		assertEquals("two apple hand w/ dups should return correct apple by word.", ra, katie.getAppleFromHand(word));
	}
	
	@Test
	public void testGetWinningRedApples() {
		Player p = new Player("bob");
		assertNull(p.getWinningRedApple(new GreenApple("bogus")));
		int size = 0;
		Map<GreenApple, RedApple> map = new HashMap<GreenApple, RedApple>(size);
		for (; size < 3; ++size) {
			String word = "word" + size;
			GreenApple green = new GreenApple(word);
			RedApple red = new RedApple(word); 
			map.put(green, red);
			p.awardPoint(green, red);
			assertEquals(size + ": getWinningRed() apple returned wrong value.", red, p.getWinningRedApple(green));
		}
	}
	
	@Test
	public void testGetPoints() {
		Player p = new Player("foo");
		assertEquals("Player should have no points.", 0, p.getPoints());
		p.awardPoint(new GreenApple("hello"), new RedApple("yo"));
		assertEquals("Player should have one point.", 1, p.getPoints());
		assertEquals("Player should have won 'hello'", new GreenApple("hello"), p.getGreenApples().get(0));
		p.awardPoint(new GreenApple("goodbye"), new RedApple("yo"));
		assertEquals("Player should have two points.", 2, p.getPoints());
		assertEquals("Player should have won 'goodbye'", new GreenApple("goodbye"), p.getGreenApples().get(1));
		
	}
	
}
