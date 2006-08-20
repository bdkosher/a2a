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
public class ListBackedDeck<AppleType extends Apple> implements Deck {
	
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
		this (selfReplenishing, null);
	}
	
	/**
	 * Creates a new Deck with the given description.
	 * @param description - the deck's description.
	 * @param selfReplenishing - if true, the apples will never run out
	 */
	public ListBackedDeck(boolean selfReplenishing, String description) {
		this.apples = new ArrayList<AppleType>();
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
			return it.hasNext() ? it.next() : null;
		} else {
			if (it.hasNext()) {
				AppleType apple = it.next();
				it.remove();
				return apple;
			} else {
				return null;
			}
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
