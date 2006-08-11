/*
 * Apple.java
 *
 * Created on July 22, 2006, 6:54 PM
 */
package com.ourbabywolf.apples2apples;

/**
 * Represents an abstract apple in the Apples2Apples game.
 *
 * @author joe@ourbabywolf.com
 */
public abstract class Apple {
    
    /** The primary word on the apple. */
    private final String word;
    
    /** Creates a new instance of apple with the given word */
    protected Apple(String word) {
    	if (word == null || "".equals(word.trim())) {
    		throw new IllegalArgumentException("Invalid word.");
    	}
    	this.word = word.trim();
    }
    
    /** Returns the word. */
    public String getWord() {
        return word;
    }
    
    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return word == null ? Apple.class.hashCode() : word.toUpperCase().hashCode();
	}

}
