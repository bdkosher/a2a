/*
 * GameEventListener.java
 *
 * Created on July 24, 2006, 11:09 PM
 */
package com.ourbabywolf.apples2apples;

import java.util.Collection;
import java.util.EventListener;

/**
 * Contains methods that are fired when certain events occur in the game.
 *
 * @author joe@ourbabywolf.com
 */
public interface GameEventListener extends EventListener {
    
	/**
	 * This method is called when enough players have joined to
	 * start the game, based on whatever was specified in the 
	 * GameConfiguration.
	 * 
	 * @param players
	 */
    public void minimumNbrOfPlayersJoinedGame(Collection<Player> players);
    
    /**
     * This method is called when the number of players drops below
     * the minimum number of players required to play the game.  This method
     * is fired regardless of whether or not the game has started.
     * 
     * @param players
     */
    public void notEnoughPlayersToPlayGame(Collection<Player> players);
    
    /**
     * This method is called when the winner has been determined due to
     * the completion of the round or some other event (e.g. running out
     * of cards) provided there's a single points leader at the time
     * of the other event.
     * 
     * @param gameWinner
     */
    public void gameWon(Player gameWinner);
    
    /**
     * In the instance that players leaving or joining a game reduce the
     * points needed to win so that there are one or more players that
     * have that many points, this event will be called.
     * 
     * @param gameWinners
     */
    public void gameWonByMultiplePlayers(Collection<Player> gameWinners);
    
    /**
     * This method is called when the administrator of the game decides
     * to terminate the game gracefully.  This event allows the game's
     * players to be notified appropriately.
     */
    public void gameTerminated();
    
    /**
     * This method is called when the round has begun.  The green apple
     * that is being played for is provided, along with the judge for
     * the round.
     * 
     * @param apple
     * @param roundJudge
     */
    public void roundStarted(GreenApple apple, Player roundJudge);
    
    /**
     * This method is called when a player joins the game.
     * 
     * @param player
     */
    public void playerJoinedGame(Player player);
    
    /**
     * This method is called when a player leaves the game.
     * 
     * @param player
     */
    public void playerLeftGame(Player player);
    
    /**
     * This method is called whenever the judge changes.  If the judge
     * is set to rotate, this method will be called at the beginning
     * of every new round.
     * 
     * @param newJudge
     */
    public void judgeChanged(Player newJudge);
    
    /**
     * This method is called whenever a player becomes inactive.
     * 
     * @param player
     */
    public void playerInactivated(Player player);
    
    /**
     * This method is called whenever an inactive player becomes active
     * again.
     * 
     * @param player
     */
    public void playerActivated(Player player);
    
    /**
     * This method is called whenever a player changes his/her nickname.
     * 
     * @param player
     * @param oldNick
     */
    public void playerNickChanged(Player player, String oldNick);
    
    /**
     * This method is called whenever a new RedApple is dealt to a player.
     * 
     * @param player
     * @param dealtApple
     */
    public void redAppleDealt(Player player, RedApple dealtApple);
       
    /**
     * This method gets called when a non-judge player plays a RedApple
     * during a round.
     * 
     * @param player
     * @param applePlayed
     */
    public void applePlayed(Player player, RedApple applePlayed);
    
    /**
     * This method is called when all active players have played a
     * RedApple for a round, signaling that the judge can now pick the
     * winner.
     * 
     * @param judge
     * @param cards
     */
    public void readyToJudge(Player judge, Collection<RedApple> cards);
    
    /**
     * This method is called when the judge picks the winning RedApple and 
     * a single player played the winning apple.
     * 
     * @param judge
     * @param roundWinner
     * @param winningApple
     * @param pointWon
     */
    public void roundWon(Player judge, Player roundWinner, RedApple winningApple, GreenApple pointWon);
    
    /**
     * This method is called when the judge picks the winning RedApple and there were
     * multiple players who played the same apple.  Technically, all cards in the
     * deck should be unique, but that's not enforced by this game.
     * 
     * @param judge
     * @param roundWinners
     * @param winningApple
     * @param pointWon
     */
    public void roundWonByMultiplePlayers(Player judge, Collection<Player> roundWinners, RedApple winningApple, GreenApple pointWon);
    
    /**
     * This event is fired when there are no more cards in the 
     * GreenApple deck.  The listener implementation may do a 
     * refresh of the GreenApple deck to remedy the situation or
     * ask the Game to check for the winner(s).
     */
    public void greenAppleDeckExhausted();
    
    /**
     * This event is fired when there are not enough cards left to 
     * deal RedApples to every player.  The listener implementation may
     * do a refresh of the RedApple deck to remedy the situation or
     * use the game to check for the winner(s).
     * 
     * @param players
     */
    public void redAppleDeckExhausted();

    /** 
     * This method is called whenever the points needed changes.  This can occur
     * when a player leaves or joins the game (regardless of whether or not it's
     * started) and the points needed to win is based on the number of players. 
     * Or it can occur if an admin manually sets the points needed to win to
     * another value.
     * 
     * @param newPointsNeededToWin
     * @param oldPointsNeededToWin
     */
	public void pointsNeededToWinChanged(int newPointsNeededToWin, int oldPointsNeededToWin);

	/**
	 * This method is called when there aren't enough active players to complete
	 * a round, either the number of players drops below the minimum or the judge
	 * becomes inactive OR a player leaves the game, dropping the number of players
	 * below the minimum and the game is configured to allow players to join
	 * the game in progress.
	 * 
	 * @param player - the player responsible for suspending the game, either by
	 * 		deactivation or leaving
	 */
	public void playSuspended(Player player);

	/**
	 * This method is called when the game gets enough players, either through
	 * reactivation or joining the game, to continue the game.
	 * 
	 * @param player
	 */
	public void playResumed(Player player);

	/**
	 * This method is called at the beginning of the round when a new
	 * GreenApple is presented to the players.
	 * 
	 * @param greenApple
	 */
	public void greenApplePlayed(GreenApple greenApple);

	/**
	 * This method is called when the number of players in the game reaches
	 * its maximum number.
	 * 
	 * @param players
	 */
	public void maximumNbrOfPlayersJoinedGame(Collection<Player> players);
}
