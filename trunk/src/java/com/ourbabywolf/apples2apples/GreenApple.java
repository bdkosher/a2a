/*
 * GreenApple.java
 *
 * Created on July 22, 2006, 6:56 PM
 */
package com.ourbabywolf.apples2apples;

import java.util.Collection;
import java.util.HashSet;

/**
 * Represents the green ajdective cards in apples2apples that are won by the
 * players.
 *
 * @author joe@ourbabywolf.com
 */
public class GreenApple extends Apple {
    
    /** Synonyms for the adjective. */
    private Collection<String> synonyms;
    
    /** Creates a new instance of GreenApple with the given word */
    public GreenApple(String word) {
    	super(word);
    }
    
    /** Gets the synonyms. */
    public Collection<String> getSynonyms() {
        return synonyms;
    }
    
    /** Sets the synonyms. */
    public void setSynonyms(Collection<String> synonyms) {
        this.synonyms = synonyms;
    }
    
    /** Adds a single synonym to the synonym collection */
    public void addSynonym(String synonym) {
        if (synonyms == null) {
            synonyms = new HashSet<String>();
        }
        synonyms.add(synonym);
    }
    
    /**
	 * Returns true if the words match
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GreenApple)) {
			return false;
		}
		GreenApple apple = (GreenApple)obj;
		return getWord().equalsIgnoreCase(apple.getWord());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GreenApple: " + getWord();
	}
    
}
