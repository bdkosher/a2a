/*
 * Deck.java
 *
 * Created on July 22, 2006, 10:47 PM
 */
package com.ourbabywolf.apples2apples;

/**
 * Represents a deck of Apples2Apples cards.
 * @author joe@ourbabywolf.com
 */
public interface Deck<AppleType extends Apple> {
	
	/** 
	 * A description of the deck being used, e.g. 
	 * "Basic Set (2001 printing) w/ Expansions 1 - 3" 
	 * @return a textual description of the deck being used 
	 */
	public String getDescription();
    
    /** 
     * Randomizes the ordering of the cards in the deck.  Should
     * be called prior to providing as a parameter to the Game
     * object.
     */
    public void shuffle();
    
    /** 
     * Takes a card off of the top of the deck or null if there are no cards left. 
     * @return the drawn card 
     */
    public AppleType draw();
    
    /** 
     * Returns true if there are no more cards to draw. 
     * @return true if the draw() method will return null 
     */
    public boolean isExhausted();
    
    /**
     * Adds the contents of the given deck to this deck.  Implementations
     * may have side effects.
     * 
     * @param deck
     */
    public void combine(Deck<AppleType> deck);
    
}
