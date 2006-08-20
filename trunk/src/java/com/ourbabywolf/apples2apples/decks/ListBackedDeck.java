package com.ourbabywolf.apples2apples.decks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ourbabywolf.apples2apples.Apple;
import com.ourbabywolf.apples2apples.Deck;

/**
 * A Deck implementation that supports all AppleTypes backed by a List.
 * 
 * @author joe@ourbabywolf.com
 *
 * @param <AppleType>
 */
public class ListBackedDeck<AppleType extends Apple> implements Deck<AppleType> {
	
	/** Holds the apples. */
	private final List<AppleType> apples;
	
	/** Points to the "top" card of the deck. */
	private Iterator<AppleType> it;
	
	/** The description of the Deck. */
	private String description = null;
	
	/** Indicates if the deck is self-replenishing. */
	private boolean selfReplenishing = true;
	
	/** 
	 * Creates a new deck w/o a description.
	 * @param selfReplenishing - if true, the apples will never run out
	 */
	public ListBackedDeck(boolean selfReplenishing) {
		this (selfReplenishing, -1, null);
	}
	
	/** 
	 * Creates a new deck w/o a description.  The given int is used
	 * to indicate the capacity of the underlying list supporting
	 * the deck.
	 * 
	 * @param selfReplenishing - if true, the apples will never run out
	 * @param capacity - the capacity of the underlying List supporting the deck
	 */
	public ListBackedDeck(boolean selfReplenishing, int capacity) {
		this (selfReplenishing, capacity, null);
	}
	
	/** 
	 * Creates a new deck w/ the given description.  The default capacity
	 * is used for the underlying list supporting the deck.
	 * 
	 * @param selfReplenishing - if true, the apples will never run out
	 * @param description - the deck's description.
	 */
	public ListBackedDeck(boolean selfReplenishing, String description) {
		this (selfReplenishing, -1, description);
	}
	
	/**
	 * Creates a new Deck with the given description.
	 * @param description - the deck's description.
	 * @param selfReplenishing - if true, the apples will never run out
	 * @param capacity - the capacity of the underlying List supporting the deck 
	 */
	public ListBackedDeck(boolean selfReplenishing, int capacity, String description) {
		this.apples = capacity < 0 ? new ArrayList<AppleType>() : new ArrayList<AppleType>(capacity);
		this.selfReplenishing = selfReplenishing;
		this.description = description;
	}
	
	/**
	 * Adds an apple to the deck.
	 * 
	 * @param apple
	 */
	public void add(AppleType apple) {
		apples.add(apple);
	}

	/**
	 * Returns the next element in the list. 
	 * @return an apple
	 */
	public AppleType draw() {
		if (selfReplenishing) {
			if (it == null || !it.hasNext()) {
				it = apples.iterator();
			}
			return it.next();
		} else {
			return !apples.isEmpty() ? apples.remove(0) : null;
		}
	}

	/**
	 * Returns a description of this deck.
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Indicates if there are no more apples in this deck.
	 */
	public boolean isExhausted() {
		return selfReplenishing ? false : !it.hasNext();
	}

	/** 
	 * Randomizes the ordering of the deck,  If the
     * deck is self replenishing, all cards will be reshuffled,
     * including those that have been drawn already.  If not,
     * only the remaining cards are shuffled.
	 */
	public void shuffle() {
		Collections.shuffle(apples);
		it = apples.iterator();
	}

}
