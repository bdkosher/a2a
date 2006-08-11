/*
 * RedApple.java
 *
 * Created on July 22, 2006, 6:55 PM
 */
package com.ourbabywolf.apples2apples;

/**
 * Represents the Apples2Apples cards containing nouns.  These are the cards
 * that make up a player's hand.
 *
 * @author joe@ourbabywolf.com
 */
public class RedApple extends Apple {
    
    /** The oft humerous quip about the noun on the card. */
    private String quip;
    
    /** Creates a new instance of RedApple with the given word */
    public RedApple(String word) {
    	super(word);
    }
    
    /** Returns the quip. */
    public String getQuip() {
        return quip;
    }
    
    /** Sets the quip. */
    public void setQuip(String quip) {
        this.quip = quip;
    }
    
    /**
	 * Returns true if the words match
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RedApple)) {
			return false;
		}
		RedApple apple = (RedApple)obj;
		return getWord().equalsIgnoreCase(apple.getWord());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RedApple: " + getWord();
	}
    
}
