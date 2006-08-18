/*
 * GameConfiguration.java
 *
 * Created on July 25, 2006, 12:05 AM
 */
package com.ourbabywolf.apples2apples;

/**
 * Holds the configurable aspects of the game.
 *
 * @author joe@ourbabywolf.com
 */
public interface GameConfiguration {
    
    /**
     * Returns the maximum number of players that may join the game.
     * @return
     */
    public int getMaxNbrOfPlayers();
    
    /** 
     * Returns the number of cards each player's hand contains. 
     * @return
     */
    public int getCardsPerHand();
    
    /** 
     * Returns true if players can join the game while it's in progress.
     * @return
     */
    public boolean playersCanJoinDuringGame();
    
    /**
     * Returns true if players can leave during the game.
     * @return
     */
    public boolean playersCanLeaveDuringGame();
    
    /** 
     * Returns the number of GreenApples needed to win. If 0 or less,
     * it is assume game continues until GreenApple deck is exhausted
     * or a winner is manually declared.
     *  
     * @param nbrOfPlayers - the number of players who have joined the
     * 		game, since officially the points needed to win depends on
     * 		the number of players
     */
    public int getPointsNeededToWin(int nbrOfPlayers);
    
    /**
     * If this returns true, the number of points needed to win will
     * be determined at the start of the game and remain that value
     * regardless of who joins are leaves the game during its progress.
     * If false, the number of points needed to win will vary based
     * on the number of players.
     * 
     * @return
     */
    public boolean fixPointsNeededToWinAtStart();

    /**
     * If this returns true, the Game rotates the judge.  Otherwise, the
     * judge is fixed. 
     * @return
     */
	public boolean isJudgeRotated();

	/**
	 * Returns the minimum number of players needed to start the game.
	 * @return
	 */
	public int getMinNbrOfPlayers();
	
	/**
	 * If this returns true, the game automatically starts a new round
	 * when 1) the previous round has ended, 2) the game has been restarted,
	 * and 3) when the minimum number of players required to begin a game
	 * have joined the game.  Otherwise, the next round has to be manually started.
	 * @return
	 */
    public boolean autoStartRound();
}
