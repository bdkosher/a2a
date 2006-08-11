/**
 * 
 */
package com.ourbabywolf.apples2apples;

/**
 * A cheater is a player with the ability to add cards at will to his
 * hand.
 * 
 * @author joe@ourbabywolf.com
 *
 */
public class Cheater extends Player {

	/**
	 * Creates a cheater with the given id.
	 * 
	 * @param id
	 */
	public Cheater(String id) {
		super(id);
	}

	/**
	 * Creates a cheater with the given id and nickname.
	 * 
	 * @param id
	 * @param nick
	 */
	public Cheater(String id, String nick) {
		super(id, nick);
	}
	
	/**
	 * Generates a new RedApple using the given word and adds
	 * it to your hand.
	 * 
	 * @param word
	 */
	public void addRedAppleToHand(String word) {
		if (word == null || "".equals(word.trim())) {
			throw new IllegalArgumentException("Inavlid word.");
		}
		addRedAppleToHand(new RedApple(word));
	}
	
	/**
	 * Adds a RedApple to the players hand by publically exposing
	 * the protected <code>dealApple</code> method.
	 * @param apple
	 */
	public void addRedAppleToHand(RedApple apple) {
		super.dealApple(apple);
	}
	
	

}
