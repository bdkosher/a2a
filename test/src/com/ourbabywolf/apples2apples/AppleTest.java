/*
 * AppleTest.java - created Jul 28, 2006 10:28:27 PM
 * $Id$
 */
package com.ourbabywolf.apples2apples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;


/**
 * Test case for red and green apples.
 * 
 * @author joe@ourbabywolf.com
 *
 */
public class AppleTest {

	@Test
	public void testGreenAppleEquality() {
		GreenApple ga1 = new GreenApple("yo");
		GreenApple ga2 = new GreenApple("yo");
		GreenApple ga3 = new GreenApple("YO");
		GreenApple ga4 = new GreenApple("poo");
		assertEquals("Same word green apples should be equal.", ga1, ga2);
		assertEquals("Same word green apples w/ diff case should be equal.", ga2, ga3);
		assertFalse("Different word green apples w/ diff case should not be equal.", ga3.equals(ga4));
	}
	
	@Test
	public void testRedAppleEquality() {
		RedApple ra1 = new RedApple("yo");
		RedApple ra2 = new RedApple("yo");
		RedApple ra3 = new RedApple("YO");
		RedApple ra4 = new RedApple("poo");
		assertEquals("Same word red apples should be equal.", ra1, ra2);
		assertEquals("Same word red apples w/ diff case should be equal.", ra2, ra3);
		assertFalse("Different word red apples w/ diff case should not be equal.", ra3.equals(ra4));
	}
	
	@Test
	public void testAppleEquality() {
		String word = "poo";
		assertFalse("Same word red and green apples should not be equal.", new GreenApple(word).equals(new RedApple(word)));
	}
	
	@Test
	public void testGreenAppleHashCodes() {
		GreenApple ga1 = new GreenApple("yo");
		GreenApple ga2 = new GreenApple("yo");
		GreenApple ga3 = new GreenApple("YO");
		GreenApple ga4 = new GreenApple("poo");
		assertTrue("Same word green apples should have same hash.", ga1.hashCode() == ga2.hashCode());
		assertTrue("Same word green apples w/ diff case should have same hash.", ga2.hashCode() == ga3.hashCode());
		assertFalse("Different word green apples w/ diff case should not have same hash.", ga3.hashCode() == ga4.hashCode());
		
		Set<GreenApple> apples = new HashSet<GreenApple>(4);
		assertTrue("Can add yo 1", apples.add(ga1));
		assertFalse("Can't add yo 2", apples.add(ga2));
		assertFalse("Can't add YO", apples.add(ga3));
		assertTrue("Can add poo", apples.add(ga4));
	}
	
	public void testRedAppleHashCodes() {
		RedApple ra1 = new RedApple("yo");
		RedApple ra2 = new RedApple("yo");
		RedApple ra3 = new RedApple("YO");
		RedApple ra4 = new RedApple("poo");
		assertTrue("Same word red apples should have same hash.", ra1.hashCode() == ra2.hashCode());
		assertTrue("Same word red apples w/ diff case should have same hash.", ra2.hashCode() == ra3.hashCode());
		assertFalse("Different word red apples w/ diff case should not have same hash.", ra3.hashCode() == ra4.hashCode());
		
		Set<RedApple> apples = new HashSet<RedApple>(4);
		assertTrue("Can add yo 1", apples.add(ra1));
		assertFalse("Can't add yo 2", apples.add(ra2));
		assertFalse("Can't add YO", apples.add(ra3));
		assertTrue("Can add poo", apples.add(ra4));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGreenAppleNullWord() {
		new GreenApple(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRedAppleNullWord() {
		new RedApple(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGreenAppleEmptyWord() {
		new GreenApple("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRedAppleEmptyWord() {
		new RedApple("");
	}
	
	@Test
	public void testAddSynonyms() {
		GreenApple ga = new GreenApple("word");
		assertNull(ga.getSynonyms());
		ga.addSynonym("bubba");
		assertNotNull(ga.getSynonyms());
		assertEquals("Unexpected nbr of synonyms", 1, ga.getSynonyms().size());
		ga.setSynonyms(new ArrayList<String>(0));
		assertEquals("Unexpected nbr of synonyms after set", 0, ga.getSynonyms().size());
	}
}
