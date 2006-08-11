/*
 * AbstractGameEventListener.java - created Jul 31, 2006 11:57:18 AM
 * $Id$
 */
package com.ourbabywolf.apples2apples;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Basic implementation of GameEventListener that logs whenever an event
 * is called.
 * 
 * @author joe@ourbabywolf.com
 *
 */
public class AbstractGameEventListener implements GameEventListener {
	
	protected final Logger log;

	public AbstractGameEventListener() {
		log = Logger.getLogger("com.ourbabywolf.apples2apples.AbstractGameEventListener");
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#applePlayed(com.ourbabywolf.apples2apples.Player, com.ourbabywolf.apples2apples.RedApple)
	 */
	public void applePlayed(Player player, RedApple applePlayed) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "applePlayed(" + player + ", " + applePlayed + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#gameTerminated()
	 */
	public void gameTerminated() {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "gameTerminated()");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#gameWon(com.ourbabywolf.apples2apples.Player)
	 */
	public void gameWon(Player gameWinner) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "gameWon(" + gameWinner + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#gameWonByMultiplePlayers(java.util.Collection)
	 */
	public void gameWonByMultiplePlayers(Collection<Player> gameWinners) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "gameWonByMultiplePlayers" 
					+ collectionToString(gameWinners) + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#greenAppleDeckExhausted()
	 */
	public void greenAppleDeckExhausted() {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "greenAppleDeckExhausted()");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#greenApplePlayed(com.ourbabywolf.apples2apples.GreenApple)
	 */
	public void greenApplePlayed(GreenApple greenApple) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE,"greenApplePlayed(" + greenApple + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#judgeChanged(com.ourbabywolf.apples2apples.Player)
	 */
	public void judgeChanged(Player newJudge) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "judgeChanged(" + newJudge + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#maximumNbrOfPlayersJoinedGame(java.util.Collection)
	 */
	public void maximumNbrOfPlayersJoinedGame(Collection<Player> players) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "maximumNbrOfPlayersJoinedGame" 
					+ collectionToString(players) + ")");
		}

	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#minimumNbrOfPlayersJoinedGame(java.util.Collection)
	 */
	public void minimumNbrOfPlayersJoinedGame(Collection<Player> players) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "minimumNbrOfPlayersJoinedGame" 
					+ collectionToString(players) + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#notEnoughPlayersToContinueGame(java.util.Collection)
	 */
	public void notEnoughPlayersToPlayGame(Collection<Player> players) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "notEnoughPlayersToPlayGame" 
					+ collectionToString(players) + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#playResumedByReactivation(com.ourbabywolf.apples2apples.Player)
	 */
	public void playResumed(Player player) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "playResumedByReactivation(" + player + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#playSuspendedByInactivation(com.ourbabywolf.apples2apples.Player)
	 */
	public void playSuspended(Player player) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "playSuspendedByInactivation(" + player + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#playerActivated(com.ourbabywolf.apples2apples.Player)
	 */
	public void playerActivated(Player player) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "playerActivated(" + player + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#playerInactivated(com.ourbabywolf.apples2apples.Player)
	 */
	public void playerInactivated(Player player) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "playerInactivated(" + player + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#playerJoinedGame(com.ourbabywolf.apples2apples.Player)
	 */
	public void playerJoinedGame(Player player) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "playerJoinedGame(" + player + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#playerLeftGame(com.ourbabywolf.apples2apples.Player)
	 */
	public void playerLeftGame(Player player) {
		if (log.isLoggable(Level.FINE)){
			log.log(Level.FINE, "playerLeftGame(" + player + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#playerNickChanged(com.ourbabywolf.apples2apples.Player, java.lang.String)
	 */
	public void playerNickChanged(Player player, String oldNick) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "playerNickChanged(" + player + ", " + oldNick + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#pointsNeededToWinChanged(int, int)
	 */
	public void pointsNeededToWinChanged(int newPointsNeededToWin,
			int oldPointsNeededToWin) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "pointsNeededToWinChanged(" + newPointsNeededToWin
					+ ", " + oldPointsNeededToWin + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#readyToJudge(com.ourbabywolf.apples2apples.Player, java.util.Collection)
	 */
	public void readyToJudge(Player judge, Collection<RedApple> cards) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "readyToJudge(" + judge + ", "
					+ collectionToString(cards) + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#redAppleDealt(com.ourbabywolf.apples2apples.Player, com.ourbabywolf.apples2apples.RedApple)
	 */
	public void redAppleDealt(Player player, RedApple dealtApple) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "redAppleDealt(" + player + ", " + dealtApple + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#redAppleDeckExhausted()
	 */
	public void redAppleDeckExhausted() {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "redAppleDeckExhausted()");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#roundStarted(com.ourbabywolf.apples2apples.GreenApple, com.ourbabywolf.apples2apples.Player)
	 */
	public void roundStarted(GreenApple apple, Player roundJudge) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "roundStarted(" + apple + ", " + roundJudge + ")");
		}
	}

	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#roundWon(com.ourbabywolf.apples2apples.Player, com.ourbabywolf.apples2apples.Player, com.ourbabywolf.apples2apples.RedApple, com.ourbabywolf.apples2apples.GreenApple)
	 */
	public void roundWon(Player judge, Player roundWinner,
			RedApple winningApple, GreenApple pointWon) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "roundWon(" + judge + ", " + roundWinner
					+ ", " + winningApple + ", " + pointWon);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.ourbabywolf.apples2apples.GameEventListener#roundWon(com.ourbabywolf.apples2apples.Player, com.ourbabywolf.apples2apples.Player, com.ourbabywolf.apples2apples.RedApple, com.ourbabywolf.apples2apples.GreenApple)
	 */
	public void roundWonByMultiplePlayers(Player judge, Collection<Player> roundWinners,
			RedApple winningApple, GreenApple pointWon) {
		if (log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "roundWon(" + judge + ", " 
					+ collectionToString(roundWinners)
					+ ", " + winningApple + ", " + pointWon);
		}
	}
	
	/**
	 * Writes out the elements of a collection nicely.
	 * 
	 * @param coll
	 * @return
	 */
	private String collectionToString(Collection coll) {
		StringBuilder builder = new StringBuilder();
		if (coll != null) {
			for (Iterator it = coll.iterator(); it.hasNext(); ) {
				builder.append(it.next());
				if (it.hasNext()) {
					builder.append(", ");
				}
			}
		} else {
			builder.append("null");
		}
		return builder.toString();
	}

}
