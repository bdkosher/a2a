/*
 * Player.java
 *
 * Created on July 22, 2006, 6:51 PM
 */
package com.ourbabywolf.apples2apples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a player in the Apples2Apples game and keeps track of the
 * cards the player has: his hand, the cards he's won, and the cards he
 * must judge.  This class also keeps minor statistics about the player
 * such as the cards won and the rounds played.
 *
 * @author joe@ourbabywolf.com
 */
public class Player {
    
    /** The player's unique identifier (e.g. the gmail login) */
    private final String id;
    
    /** The player's nickname. */
    private String nick;
        
    /** The RedApples (nouns) comprising the players playable hand. */
    private final List<RedApple> hand;
    
    /** The GreenApple (adjectives) the player has won. */
    private final List<GreenApple> greenApples;
    
    /** The RedApples player has played resulting in earning GreenApple points. */
    private final List<RedApple> winningRedApples;
    
    /** The number of rounds the player has played. */
    private int roundsPlayed = 0;
    
    /** Indicates that the player is still playing. */
    private boolean active = true;
    
    /** Indicates if the player is allowed to play or not. */
    private boolean ableToPlay = true;
    
    /**
     * Creates a new player with the given id.
     * @param id
     */
    public Player(String id) {
        this(id, null);
    }
    
    /**
     * Creates a new player with the given id and nickname.
     * @param id
     * @param nick
     */
    public Player(String id, String nick) {
    	if (isInvalidPlayerId(id)) {
    		throw new IllegalArgumentException("Invalid id.");
    	}
        this.id = id;
        this.nick = nick;
        this.hand = new ArrayList<RedApple>();
        this.greenApples = new ArrayList<GreenApple>();
        this.winningRedApples = new ArrayList<RedApple>();
    }
    
    /** 
     * Returns the player's id. 
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the nick if it's not null.  Otherwise, the id.
	 * @return the nick
	 */
	public String getNick() {
		return nick == null ? id : nick;
	}

	/**
	 * @param nick the nick to set
	 */
	protected void setNick(String nick) {
		this.nick = nick;
	}

	/** 
     * Indicates if the player is active. 
     */
    public boolean isActive() {
        return active;
    }

    /** 
     * Sets player's active status. 
     */
    protected void setActive(boolean active) {
        this.active = active;
    }

    /**
	 * @return the ableToPlay
	 */
	public boolean isAbleToPlay() {
		return ableToPlay;
	}

	/**
	 * @param ableToPlay the ableToPlay to set
	 */
	protected void setAbleToPlay(boolean ableToPlay) {
		this.ableToPlay = ableToPlay;
	}

	/** 
     * Returns the number of rounds played. 
     */
    public int getRoundsPlayed() {
        return roundsPlayed;
    }
    
    /** 
     * Increments the rounds played counter.
     */
    protected void incrementRoundsPlayed() {
        roundsPlayed++;
    }
    
    /**
     * Resets the rounds played counter back to 0.
     */
    protected void resetRoundsPlayed() {
    	roundsPlayed = 0;
	}
  
    /**
     * Returns all the RedApples in the player's hand.
     */
    public List<RedApple> getHand() {
        return Collections.unmodifiableList(hand);
    }
    
    /**
     * Sorts players hand using given comparator.
     */
    public void sortHand(Comparator<RedApple> sorter) {
        Collections.sort(hand, sorter);
    }
    
    /**
     * Uses the index to retrieve apple from player's hand.  Apple is
     * not removed from hand.  If no such apple exists (i.e index
     * not within range), null is returned.  Zero-based indexing.
     */
    public RedApple getAppleFromHand(int index) {
       return index >= 0 && index < hand.size() ? hand.get(index) : null;
    }
    
    /**
     * Uses the word on the apple to retreive card from player's hand.
     * Apple is not removed from hand.  Null is returned if
     * no such apple exists in the hand with that word.
     */
    public RedApple getAppleFromHand(String word) {
        if (word != null) {
            for (RedApple apple : hand) {
                if (word.equals(apple.getWord())) {
                    return apple;
                }
            } 
        }
        return null;
    }
    
    /**
     * Removes all cards from player's hands
     */
    protected void clearHand() {
		hand.clear();
	}
    
    /** 
     * Plays (i.e. removes) the given apple from the players hand.
     * @return true if the apple was in the hand and removed, false otherwise
     */
    protected boolean playApple(RedApple apple) {
      return hand.remove(apple);
    }
    
    /**
     * Adds the card to this player's hand.
     */
    protected void dealApple(RedApple apple) {
    	if (apple == null) {
    		throw new IllegalArgumentException("RedApple cannot be null.");
    	}
        hand.add(apple);
    }
        
    /**
     * Returns the point cards the player has been awarded. 
     */
    public List<GreenApple> getGreenApples() {
        return Collections.unmodifiableList(greenApples);
    }
    
    /**
     * Returns the RedApple that was played to win the given
     * GreenApple.
     * 
     * @param greenApple
     * @return null if the GreenApple hasn't
     * 		been won by this player
     */
    public RedApple getWinningRedApple(GreenApple greenApple) {
    	if (greenApple == null) {
    		throw new IllegalArgumentException("GreenApple argument cannot be null.");
    	}
    	final int index = greenApples.indexOf(greenApple);
    	return index < 0 ? null : winningRedApples.get(index);
    }
    
    /**
     * Returns the number of point cards player has earned.
     */
    public int getPoints() {
       return greenApples.size(); 
    }
    
    /**
     * Adds the card to this players points pile.
     * 
     * @param point - the GreenApple won
     * @param winner - the RedApple played that was selected as the
     * 		winner by the judge.
     */
    protected void awardPoint(GreenApple point, RedApple winner) {
    	if (point == null) {
    		throw new IllegalArgumentException("GreenApple argument cannot be null.");
    	} else if (winner == null) {
    		throw new IllegalArgumentException("RedApple argument cannot be null.");
    	}
        greenApples.add(point);
        winningRedApples.add(winner);
    }
    
    /**
     * This method clears out any point cards the player
     * has been awarded.
     */
    protected void clearPoints() {
		greenApples.clear();
		winningRedApples.clear();
	}
    
    /**
	 * Returns true if the player id is invalid.
	 * 
	 * @param id
	 * @return
	 */
	public static boolean isInvalidPlayerId(String id) {
		return id == null || "".equals(id.trim());
	}
    
    /**
     * Players are equal if their ids match.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }
        Player p = (Player)obj;
        String pid = p.getId();
        return pid == null ? id == null : pid.equals(id);
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return id == null ? Player.class.hashCode() : id.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Player: " + id + (nick == null ? "" : " (" + nick + ")");
	}

}
