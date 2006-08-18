/*
 * Game.java
 *
 * Created on July 23, 2006, 12:32 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ourbabywolf.apples2apples;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

/**
 * Maintains the state of the apples2apples game: the players, the rounds, the
 * decks, etc.
 * 
 * @author joe@ourbabywolf.com
 */
public class Game {
	
	/** The logger. */
	private final Logger log = Logger.getLogger(Game.class.getName());

	/** 
	 * The states the game can be in. Graphically, the phases work as such:
	 * <pre>
	 *                /-----------------------------\
	 *                |                             |
	 *                v                             |
	 *  INIT --> ROUND_PLAY --> ROUND_JUDGE --> ROUND_OVER --> GAME_OVER
	 *                ^              ^              ^
	 *                |              |              |
	 *                v              v              v           
	 *            SUSPENDED      SUSPENDED       SUSPENDED
	 *  </pre>
	 *  The game can be terminated at any time, however, so it's possible
	 *  to move from any non-GAME_OVER state to the GAME_OVER state.
	 */
	private enum GamePhase {
		/** Gathering players. */
		INIT, 
		/** Players can play their RedApples. */
		ROUND_PLAY, 
		/** Played RedApples are awaiting judgement. */
		ROUND_JUDGE,
		/** After the judge has selected the winner. */
		ROUND_OVER,
		/** Final state, waiting for restart. */
		GAME_OVER,
		/** When the round can't continue. */
		SUSPENDED 
	};

	/**
	 * Status codes indicating the result of the method execution.
	 */
	public enum Result {
		/** The method call was successful. */
		SUCCESS, 
		/** The method call failed to execute because the game lacked 
		 * something, such as not having enough players. */
		ERROR_GAME_UNINITIALIZED,
		/** The method call failed to execute because it is not allowed 
		 * at this time. */
		ERROR_PROHIBITED,
		/** Calling the method execution had no effect. */
		NO_EFFECT, 
		/** The method call failed because its parameter(s) were invalid. */
		ERROR_INVALID_PARAMETER 
	}

	/** The current state of the game. */
	private GamePhase phase;

	/** The phase the game was in prior to it being suspended. */
	private GamePhase preSuspendedPhase;

	/** Holds game configuration settings. */
	private final GameConfiguration config;

	/** This gets notified of game events. */
	private final GameEventListener eventListener;

	/** The players in the game. */
	private final Set<Player> players;
	
	/** The winners of the game (will be empty until game is won). */
	private final Set<Player> winners;

	/** The list of ids that are banned for being players. */
	private final Set<String> bannedPlayerIds;
	
	/** The list of nicknames that are in use for this game. */
	private final Set<String> nicks;

	/** The number of rounds played. */
	private int roundsPlayed = 0;

	/** Indicates the player who is the current judge. */
	private Player judge = null;

	/** Indicates that green apple that is currently being played for. */
	private GreenApple greenApple;

	/** The Deck of red apples used in the game. */
	private Deck<RedApple> redApples;

	/** The Deck of green apples used in the game. */
	private Deck<GreenApple> greenApples;

	/** The mapping of players to the cards they've submitted for judgement. */
	private final Map<Player, RedApple> applesToJudge;

	/** The number of points needed to win. if 0 or less, unlimited. */
	private int pointsNeededToWin = 0;

	/**
	 * Creates a new Game using the given event listener with default
	 * configuration parameters. Decks must also be provided.
	 * 
	 * @param eventListener
	 * @param red
	 *            apple deck
	 * @param green
	 *            apple deck
	 */
	public Game(GameEventListener eventListener, Deck<RedApple> redApples,
			Deck<GreenApple> greenApples) {
		this(eventListener, new DefaultGameConfiguration(), redApples,
				greenApples);
	}

	/**
	 * Creates a new Game using the given event listener and configuration.
	 * Decks must also be provided.
	 * 
	 * @param eventListener
	 * @param config
	 * @param red
	 *            apple deck
	 * @param green
	 *            apple deck
	 * @throws GameConfigurationException - if the configuration 
	 * 		contains some invalid parameters            
	 */
	public Game(GameEventListener eventListener, GameConfiguration config,
			Deck<RedApple> redApples, Deck<GreenApple> greenApples) 
			throws GameConfigurationException  {
		if (eventListener == null) {
			throw new IllegalArgumentException(
					"GameEventListener cannot be null.");
		}
		this.eventListener = eventListener;

		if (config == null) {
			throw new IllegalArgumentException(
					"GameConfiguration cannot be null.");
		}
		this.config = config;
		verifyConfig();

		if (redApples == null) {
			throw new IllegalArgumentException("RedApples Deck cannot be null.");
		}
		this.redApples = redApples;

		if (greenApples == null) {
			throw new IllegalArgumentException(
					"GreenApples Deck cannot be null.");
		}
		this.greenApples = greenApples;

		this.phase = GamePhase.INIT;
		this.players = new HashSet<Player>(config.getMaxNbrOfPlayers());
		this.winners = new HashSet<Player>(config.getMaxNbrOfPlayers());
		this.bannedPlayerIds = new HashSet<String>();
		this.nicks = new HashSet<String>();
		this.applesToJudge = new HashMap<Player, RedApple>(config
				.getMaxNbrOfPlayers());
	}
	
	/**
	 * Verifies that the GameConfiguration is OK.
	 * 
	 * @throws Exception - if there is something wrong with the
	 * 		configuration.
	 */
	private void verifyConfig() throws GameConfigurationException {
		final int minNumberOfCardsPerHand = 1;
		if (config.getCardsPerHand() < minNumberOfCardsPerHand) {
			throw new GameConfigurationException("Number of cards per hand", 
					config.getCardsPerHand(), minNumberOfCardsPerHand);
		}
		final int minNumberOfPlayers = 2;
		if (config.getMinNbrOfPlayers() < minNumberOfPlayers) {
			throw new GameConfigurationException("Minimum number of players",
					config.getMinNbrOfPlayers(), minNumberOfPlayers);
		}
		final int maxNumberOfPlayers = minNumberOfPlayers;
		if (config.getMaxNbrOfPlayers() < maxNumberOfPlayers) {
			throw new GameConfigurationException("Maximum number of players",
					config.getMaxNbrOfPlayers(), maxNumberOfPlayers);
		}
	}
	
	/**
	 * Verifies that the GameConfiguration in regards to the number of points
	 * needed to win is OK.
	 * 
	 * @throws Exception - if there is something wrong with the
	 * 		configuration.
	 */
	private void verifyNbrOfPointsNeededToWinConfig() throws GameConfigurationException {
		final int minNumberOfPoints = 1;
		if (pointsNeededToWin < minNumberOfPoints &&
				pointsNeededToWin >= 0) {
			throw new GameConfigurationException(players.size(), 
					pointsNeededToWin, minNumberOfPoints);
		}
	}

	/**
	 * Returns the GameConfiguration used to construct this game.
	 * @return
	 */
	public GameConfiguration getConfiguration() {
		return this.config;
	}

	/**
	 * Returns the number of rounds played this game.
	 * 
	 * @return
	 */
	public int getRoundsPlayed() {
		return roundsPlayed;
	}

	/**
	 * Returns the currently calcuated points needed to win.
	 * 
	 * @return the pointsNeededToWin
	 */
	public int getPointsNeededToWin() {
		return pointsNeededToWin;
	}

	/**
	 * Changes the points needed to win.
	 * 
	 * @param pointsNeededToWin
	 *            the pointsNeededToWin to set
	 */
	public void setPointsNeededToWin(int pointsNeededToWin) {
		if (pointsNeededToWin != this.pointsNeededToWin) {
			eventListener.pointsNeededToWinChanged(pointsNeededToWin,
					this.pointsNeededToWin);
			this.pointsNeededToWin = pointsNeededToWin;
		}
	}
	
	/**
	 * Indicates if the game is ready to start.
	 * 
	 * @return
	 */
	public boolean isReadyToStart() {
		return phase == GamePhase.INIT && players.size() >= config.getMinNbrOfPlayers();
	}

	/**
	 * Indicates if the game is in progress.
	 * 
	 * @return
	 */
	public boolean isStarted() {
		return phase != GamePhase.GAME_OVER && phase != GamePhase.INIT;
	}

	/**
	 * Indicates if the game is over.
	 * 
	 * @return
	 */
	public boolean isOver() {
		return phase == GamePhase.GAME_OVER;
	}
	
	/**
	 * Indicates if the game is suspended.
	 * 
	 * @return
	 */
	public boolean isSuspended() {
		return phase == GamePhase.SUSPENDED;
	}

	/**
	 * Creates a new Player for the Game using the provided id. Returns a
	 * OperationResult reflecting the result of this action. If the
	 * configuration says that the points needed to win can be modified
	 * throughout the game, this method will determine the new value of the
	 * points needed to win. If the game has already been started, it also
	 * checks to see if anyone has won the game by virtue of a
	 * points-needed-to-win reduction.
	 * 
	 * @param id
	 * @return
	 */
	public Result join(String id) {
		return join(id, null);
	}

	/**
	 * Creates a new Player for the Game using the provided id and nick. Returns
	 * an OperationResult reflecting the result of this action. If the
	 * configuration says that the points needed to win can be modified
	 * throughout the game, this method will determine the new value of the
	 * points needed to win. If the game has already been started, it also
	 * checks to see if anyone has won the game by virtue of a
	 * points-needed-to-win reduction.
	 * 
	 * @param id
	 * @param player's
	 *            nick name
	 * @return
	 */
	public Result join(String id, String nick) {
		return join(id, nick, false);
	}
	
	/**
	 * Creates a new Player for the Game using the provided id and nick. Returns
	 * an OperationResult reflecting the result of this action. If the
	 * configuration says that the points needed to win can be modified
	 * throughout the game, this method will determine the new value of the
	 * points needed to win. If the game has already been started, it also
	 * checks to see if anyone has won the game by virtue of a
	 * points-needed-to-win reduction. 
	 * @param id
	 * @param nick
	 * @param canCheat
	 * @return
	 */
	public Result join(String id, String nick, boolean canCheat) {
		if (Player.isInvalidPlayerId(id)) {
			return Result.ERROR_INVALID_PARAMETER;
		} else if (isOver() || (isStarted() && !config.playersCanJoinDuringGame())) {
			return Result.ERROR_PROHIBITED;
		} else if (bannedPlayerIds != null && bannedPlayerIds.contains(id)) {
			return Result.ERROR_PROHIBITED;
		} else if (players.size() >= config.getMaxNbrOfPlayers()) {
			return getPlayer(id) != null ? Result.NO_EFFECT :
					Result.ERROR_PROHIBITED;
		} else {
			Player player = canCheat ? new Cheater(id, nick) : new Player(id, nick);
			if (nicks.contains(player.getNick())) {
				if (players.contains(player)) {
					return Result.NO_EFFECT;
				} else {
					return Result.ERROR_INVALID_PARAMETER;
				}
			}
			players.add(player);
			nicks.add(player.getNick());
			eventListener.playerJoinedGame(player);
			if (players.size() == config.getMinNbrOfPlayers()) {
				eventListener.minimumNbrOfPlayersJoinedGame(players);
			} else if (players.size() == config.getMaxNbrOfPlayers()) {
				eventListener.maximumNbrOfPlayersJoinedGame(players);
			}
			/* See if the additional player alters the number of points needed to win. */
			if (!config.fixPointsNeededToWinAtStart()) {
				int newPointsNeededToWin = config
						.getPointsNeededToWin(players.size());
				if (pointsNeededToWin != newPointsNeededToWin) {
					eventListener.pointsNeededToWinChanged(
							newPointsNeededToWin, pointsNeededToWin);
					pointsNeededToWin = newPointsNeededToWin;
					/* If for some reason the points drop, check if there's a winner. */
					if (isStarted()) {
						verifyNbrOfPointsNeededToWinConfig();
						checkForGameWinner(false);
					}
				}
			}
			/* Check if the joining player can resume suspended play. */
			if (isSuspended() && 
					getActivePlayers().size() >= config.getMinNbrOfPlayers()) {
				resumePlay(player);
			}
			/* If they're joining during play, prevent them from playing until next round.
			 * The replenishHands method will enable them to play again. */
			if (isTimeToPlayRedApples()) {
				log.fine(player + " cannot play until next round.");
				player.setAbleToPlay(false);
			}
			/* If autostart is set up and we have the min number of players needed,
			 * start the game. */
			if (config.autoStartRound() 
					&& !isStarted() 
					&& players.size() == config.getMinNbrOfPlayers()) {
				startRound();
			}
			return Result.SUCCESS;
		}
	}

	/**
	 * Removes the player from the game. If the configuration says that the
	 * points needed to win can be modified throughout the game, this method
	 * will determine the new value of the points needed to win. If the game has
	 * already been started, it also checks to see if anyone has won the game by
	 * virtue of a points-needed-to-win reduction.  If there is no winner, it
	 * then checks if judgement can now be since this player left.
	 * 
	 * @param player
	 * @return
	 */
	public Result leave(Player player) {
		if (player == null || !players.contains(player)) {
			return Result.ERROR_INVALID_PARAMETER;
		} else if (player == null) {
			return Result.NO_EFFECT;
		} else if ((isStarted() && !config.playersCanLeaveDuringGame())
				|| player.equals(judge)) {
			/* Cannot let judge leave game (for now). */
			return Result.ERROR_PROHIBITED;
		} else {
			players.remove(player);
			nicks.remove(player.getNick());
			RedApple playedApple = applesToJudge.get(player);
			if (playedApple != null) {
				applesToJudge.remove(playedApple);
			}
			eventListener.playerLeftGame(player);
			/* Check if the leaving player alters the number of points needed to win. */
			if (!config.fixPointsNeededToWinAtStart()) {
				int newPointsNeededToWin = config
						.getPointsNeededToWin(players.size());
				if (pointsNeededToWin != newPointsNeededToWin) {
					eventListener.pointsNeededToWinChanged(
							newPointsNeededToWin, pointsNeededToWin);
					pointsNeededToWin = newPointsNeededToWin;
					/* if the points changed during game play... */
					if (isStarted()) {
						/* see if anyone has enough points to be declared the winner. */
						checkForGameWinner(false);
						/* check if all active players have played their RedApples. */
						checkForJudgementPhase();
					}
				}
			}
			/* See if the leaving player forces the game to end. */
			if (players.size() < config.getMinNbrOfPlayers()) {
				eventListener.notEnoughPlayersToPlayGame(players);
				/* if the game is started and no one new can join, declare the winner. */
				if (isStarted() && !config.playersCanJoinDuringGame()) {
					checkForGameWinner(true);
				}
			/* See if the leaving player forces the game to become suspended or end. */	
			} else if (getActivePlayers().size() < config.getMinNbrOfPlayers()) {
				suspendPlay(player);
			}
			return Result.SUCCESS;
		}
	}

	/**
	 * Inactivates a player from the game. Inactivation is different than
	 * leaving in that it's temporary and/or accidental. E.g. the player chooses
	 * to sit out for a couple of rounds or some network problem kicks the
	 * player offline.  This method checks if judgement can occur when the
	 * player becomes inactive.
	 * 
	 * Why "inactivate" instead of "deactivate"? It's because inactive carries
	 * a more passive connotation.  Inactivation is intended to occur because
	 * something unintentional happens, such as a player loses their internet
	 * connection and becomes inactive as a result, or if a player doesn't
	 * respond after X minutes, and is therefore reckoned inactive.  However,
	 * should the software using this game permit this to be used more actively,
	 * such as a "I want to temporarily leave the game but come back later", then
	 * this method certainly can be used as such. 
	 * 
	 * @param player
	 */
	public Result inactivatePlayer(Player player) {
		if (player == null || !players.contains(player)) {
			return Result.ERROR_INVALID_PARAMETER;
		} else if (!player.isActive()) {
			return Result.NO_EFFECT;
		} else {
			player.setActive(false);
			eventListener.playerInactivated(player);
			/* suspend game if necessary.  The suspend game method will ensure the game is started and not presently suspended. */
			if (player == judge
					|| getActivePlayers().size() < config
							.getMinNbrOfPlayers()) {
				suspendPlay(player);
			}
			/* see if all active players have submitted their RedApples. */
			checkForJudgementPhase();
			return Result.SUCCESS;
		}
	}
	
	/**
	 * Activates a player in the game. Activation is different than joining the
	 * game. Inactive players are still in the game, they're just temporarily
	 * unable to play. Activating a player means that they are able to start
	 * playing again.
	 * 
	 * @param player
	 */
	public Result activatePlayer(Player player) {
		if (player == null || !players.contains(player)) {
			return Result.ERROR_INVALID_PARAMETER;
		} else if (player.isActive()) {
			return Result.NO_EFFECT;
		} else {
			player.setActive(true);
			eventListener.playerActivated(player);
			/* see if activation results in the game resuming. the method will check to see if the game is presently suspended. */
			if (judge != null && judge.isActive()
					&& getActivePlayers().size() >= config
							.getMinNbrOfPlayers()) {
				resumePlay(player);
			}		
			return Result.SUCCESS;
		}
	}
	
	/**
	 * Temporarily suspends game play. When game is resumed, it returns
	 * to the phase it was in prior to the suspension.  
	 * 
	 * @param responsiblePlayer
	 * @return
	 */
	public Result suspendPlay(Player responsiblePlayer) {
		if (!isStarted()) {
			return Result.ERROR_PROHIBITED;
		} else if (phase == GamePhase.SUSPENDED) {
			return Result.NO_EFFECT;
		}
		eventListener.playSuspended(responsiblePlayer);
		preSuspendedPhase = phase;
		phase = GamePhase.SUSPENDED;
		return Result.SUCCESS;
	}
	
	/**
	 * Resumes the game if it's suspended, trigger the playResumed
	 * event.
	 * 
	 * @param responsiblePlayer
	 * @return
	 */
	public Result resumePlay(Player responsiblePlayer) {
		if (phase != GamePhase.SUSPENDED) {
			return Result.NO_EFFECT;
		}
		eventListener.playResumed(responsiblePlayer);
		phase = preSuspendedPhase;
		return Result.SUCCESS;
	}

	
	/**
	 * Prevents a player with the given id from joining the game.  If
	 * the player has already joined the game, this method will kick
	 * the player from the game.
	 * 
	 * @param id
	 * @return
	 */
	public Result banPlayer(String id) {
		leave(getPlayer(id)); // will return invalid param is player doesn't exist
		return bannedPlayerIds.add(id) ? Result.SUCCESS : Result.NO_EFFECT;
	}
	
	/**
	 * Unbans a player by id, effectively allowing them to join the game.
	 * @param id
	 * @return
	 */
	public Result unbanPlayer(String id) {
		return bannedPlayerIds.remove(id) ? Result.SUCCESS : Result.NO_EFFECT;
	}
	
	/**
	 * Changes a players nickname.  The new nickname cannot be another
	 * player's nick.
	 * 
	 * @param id
	 * @param newNick
	 * @return
	 */
	public Result changePlayerNick(Player player, String newNick) {
		if (player == null || !players.contains(player)) {
			return Result.ERROR_INVALID_PARAMETER;
		} else if (player.getNick().equals(newNick)) {
			return Result.NO_EFFECT;
		} else if (nicks.contains(newNick)) {
			return Result.ERROR_PROHIBITED;
		} 
		String oldNick = player.getNick();
		nicks.remove(oldNick);
		nicks.add(newNick);
		player.setNick(newNick);
		eventListener.playerNickChanged(player, oldNick);
		return Result.SUCCESS;
	}
	
	/**
	 * Returns the set of ids that have been banned.
	 * @return
	 */
	public Set<String> getBannedPlayerIds() {
		return bannedPlayerIds;
	}

	/**
	 * Returns the set of players.  The returned set is not modifiable.
	 * 
	 * @return
	 */
	public Set<Player> getPlayers() {
		return Collections.unmodifiableSet(players);
	}
	
	/**
	 * Returns the set of players that haven't played red apples yet.
	 * The returned set is not modifiable.  The result set is based off of
	 * all players, regardless if they're inactive or active.
	 * 
	 * @return
	 */
	public Set<Player> getPlayersWhoHaveNotPlayedRedApples() {
		return getPlayersWhoHaveNotPlayedRedApples(players);
	}
	
	/**
	 * Returns the subset of the input set of players for the players who
	 * have not submitted red apples yet.  The returned subset will not contain
	 * the judge or any players have have recently joined the game and have
	 * not been dealt cards to play.
	 *  
	 * @param playerSet
	 * @return
	 */
	public Set<Player> getPlayersWhoHaveNotPlayedRedApples(Set<Player> playerSet) {
		Set<Player> unplayed = new HashSet<Player>(playerSet.size());
		unplayed.addAll(playerSet);
		Set<Player> played = applesToJudge.keySet();
		for (Player p : playerSet) {
			/* Ignore judges and players who just joined the game and
			 * therefore do not have cards yet to play */
			if (played.contains(p)
					|| p.equals(judge) 
					|| !p.isAbleToPlay()) {
				unplayed.remove(p);
			}
		}
		return Collections.unmodifiableSet(unplayed);
	}

	/**
	 * Returns the set of players sorted from highest to lowest scoring.
	 * 
	 * @return
	 */
	public Set<Player> getPlayersSortedByPoints() {
		Set<Player> sorted = new TreeSet<Player>(new Comparator<Player>() {
			/**
			 * Sorts by their score so that the highest scorers are first in the
			 * set.
			 */
			public int compare(Player p1, Player p2) {
				int difference = p2.getPoints() - p1.getPoints();
				return difference == 0 ? p1.hashCode() - p2.hashCode() : difference;
			}
		});
		sorted.addAll(players);
		return Collections.unmodifiableSet(sorted);
	}
	
	/**
	 * Returns the winners of the game.  If the game hasn't been won yet,
	 * this method returns an empty set.
	 * 
	 * @return
	 */
	public Set<Player> getWinners() {
		return Collections.unmodifiableSet(winners);
	}

	/**
	 * Returns the player with the given id or null if no such
	 * player exists.
	 * 
	 * @param id
	 * @return
	 */
	public Player getPlayer(String id) {
		if (players != null && id != null) {
			for (Player p : players) {
				if (id.equals(p.getId())) {
					return p;
				}
			}
		}
		return null;
	}

	/**
	 * Returns the players that are currently active.
	 * The returned set is not modifiable.
	 * 
	 * @return
	 */
	public Set<Player> getActivePlayers() {
		Set<Player> active = new HashSet<Player>(players.size());
		for (Player p : players) {
			if (p.isActive()) {
				active.add(p);
			}
		}
		return Collections.unmodifiableSet(active);
	}

	/**
	 * Examines the points held by each player and determines if there are
	 * players with points equalling/exceededing the points needed to win. If
	 * so, it will fire the appropriate method in the GameEventListener. If
	 * points needed to win is less than one, this method does nothing unles
	 * forceDeclaration is set to true.
	 * 
	 * @param forceDeclaration -
	 *            forces that the appropriate GameEventListener method be
	 *            called, declaring the winner(s) to be the player(s) with the
	 *            most points.
	 */
	public void checkForGameWinner(boolean forceDeclaration) {
		if (isStarted()) {
			/* Get the player(s) with the most points */
			Set<Player> sorted = getPlayersSortedByPoints();
			Set<Player> potentialWinners = new HashSet<Player>(players.size());
			Player potentialWinner = null;
			for (Player p : sorted) {
				/* A null potential winner is the first in the list, and therefore has the highest points. */
				if (potentialWinner == null || potentialWinner.getPoints() == p.getPoints()) {
					potentialWinner = p;
					//log.info("Player " + p + " has " + p.getPoints() + " points.");
					potentialWinners.add(p);
				} else {
					/* The moment we have a player with points less than that of the potential
					 * winner, we have no need to continue iterating through the list. */
					break;
				}
			}
			if (potentialWinner.getPoints() >= pointsNeededToWin || forceDeclaration) {
				if (potentialWinners.size() == 1) {
					eventListener.gameWon(potentialWinner);
					winners.add(potentialWinner);
				} else {
					eventListener.gameWonByMultiplePlayers(potentialWinners);
					winners.addAll(potentialWinners);
				}
				phase = GamePhase.GAME_OVER;
			}
		}
	}

	/**
	 * @return the judge
	 */
	public Player getJudge() {
		return judge;
	}

	/**
	 * Permanently (at least, until this method is called again) sets the judge
	 * to the indicated player.  This causes the judge changed event to be fired.
	 * 
	 * @param judge
	 *            the judge to set
	 * @return SUCCESS if the judge was changed, NO_EFFECT if the judge wasn't
	 *         changed ERROR_INVALID_PARAMETER if the player is null or not in
	 *         the game ERROR_PROHIBITED if the game is not in the
	 *         init phase or round_over phase.
	 */
	public Result setJudge(Player judge) {
		if (judge == null || !players.contains(judge)) {
			return Result.ERROR_INVALID_PARAMETER;
		} else if (phase != GamePhase.INIT && phase != GamePhase.ROUND_OVER) {
			return Result.ERROR_PROHIBITED;
		}
		Player oldJudge = this.judge;
		if (oldJudge != judge) {
			this.judge = judge;
			eventListener.judgeChanged(judge);
			return Result.SUCCESS;
		}
		return Result.NO_EFFECT;
	}

	/**
	 * Sets the judge to be the next judge in the rotation. If the judge is
	 * configured to be fixed, this method returns an error.  If succesful, the
	 * judge changed event is fired.
	 * 
	 * @return SUCCESS if the judge changed
	 * 			NO_EFFECT if the judge did not change
	 * 			ERROR_PROHIBITED if we're not in a ROUND_OVER phase or INIT phase
	 * 			ERROR_PROHIBITED if the game is configured not to rotate judges
	 */
	public Result setJudge() {
		if (phase != GamePhase.INIT && phase != GamePhase.ROUND_OVER
				|| !config.isJudgeRotated()) {
			return Result.ERROR_PROHIBITED;
		}
		Player oldJudge = judge; 
		for (Iterator<Player> it = players.iterator(); it.hasNext();) {
			/* find the current judge in the rotation. */
			if (judge == null || it.next().equals(judge)) {
				judge = it.hasNext() ? it.next() : players.iterator().next();
				/* to check if the judge actually changed...don't know why it wouldn't */
				if (judge != oldJudge) {
					eventListener.judgeChanged(judge);
					return Result.SUCCESS;
				}
			}
		}
		return Result.NO_EFFECT;
	}
	
	/**
	 * When the game has ended, resets things so that a new game could start.
	 * If rounds are configured to start automatically, the first round will
	 * automatically start.
	 * 
	 * @return
	 */
	public Result restart() {
		if (!isOver()) {
			return Result.ERROR_PROHIBITED;
		} else if (redApples.isExhausted() || greenApples.isExhausted()) {
			return Result.ERROR_GAME_UNINITIALIZED;
		}
		phase = GamePhase.INIT;
		roundsPlayed = 0;
		greenApple = null;
		if (config.isJudgeRotated()) {
			judge = null;
		}
		winners.clear();
		for (Player p : players) {
			p.clearHand();
			p.clearPoints();
			p.resetRoundsPlayed();
		}
		if (config.autoStartRound()) {
			startRound();
		}
		return Result.SUCCESS;
	}

	/**
	 * Signals a new round to begin. And prepares for the round by 1)
	 * replenishing players hands 2) setting the judge 3) drawing the new
	 * GreenApple to play for If this is the first round and the game is
	 * configured to fix the points needed to win at the start of the game, 4)
	 * the points needed to win are determined
	 */
	public Result startRound() {
		if (phase == GamePhase.ROUND_PLAY) {
			return Result.NO_EFFECT;
		} else if (phase != GamePhase.INIT
				&& phase != GamePhase.ROUND_OVER) {
			return Result.ERROR_PROHIBITED;
		} else if (players.size() < config.getMinNbrOfPlayers()) {
			return Result.ERROR_GAME_UNINITIALIZED;
		} else if (getActivePlayers().size() < config.getMinNbrOfPlayers()) {
			return Result.ERROR_PROHIBITED;
		}
		replenishHands();
		if (!isStarted() && config.fixPointsNeededToWinAtStart()) {
			pointsNeededToWin = config.getPointsNeededToWin(players.size());
			verifyNbrOfPointsNeededToWinConfig();
		}
		
		/* Don't want to set the judge to an inactive player? */
		int maxAttempts = players.size() + 1; // the +1 allows the judges to be rotated back to the first new judge, should everyone be inactive
		do {
			setJudge();
			--maxAttempts;
		} while (judge != null && !judge.isActive() 
				&& maxAttempts > 0 /*&& config.rotatePastInactiveJudges()*/);
			
		if (judge == null) {
			return Result.ERROR_GAME_UNINITIALIZED;
		} else if (!judge.isActive()) {
			suspendPlay(null);
		}
		drawGreenApple();
		applesToJudge.clear();
		phase = GamePhase.ROUND_PLAY;
		return Result.SUCCESS;
	}

	/**
	 * Draws the next GreenApple to be played for.
	 */
	public Result drawGreenApple() {
		if (phase != GamePhase.ROUND_OVER && phase != GamePhase.INIT) {
			return Result.ERROR_PROHIBITED;
		} else if (greenApples.isExhausted()) {
			return Result.NO_EFFECT;
		}
		greenApple = greenApples.draw();
		eventListener.greenApplePlayed(greenApple);
		if (greenApples.isExhausted()) {
			eventListener.greenAppleDeckExhausted();
		}
		return Result.SUCCESS;
	}

	/**
	 * Deals out enough RedApples to each player to fill their hands to
	 * capacity. The appropriate events are fired if there are not enough cards
	 * to do so.
	 */
	public Result replenishHands() {
		if (redApples.isExhausted()) {
			return Result.ERROR_GAME_UNINITIALIZED;
		}
		for (Player p : players) {
			while (p.getHand().size() < config.getCardsPerHand()) {
				RedApple redApple = redApples.draw();
				p.dealApple(redApple);
				p.setAbleToPlay(true);
				eventListener.redAppleDealt(p, redApple);
				if (redApples.isExhausted()) {
					eventListener.redAppleDeckExhausted();
					return Result.ERROR_GAME_UNINITIALIZED;
				}
			}
		}
		return Result.SUCCESS;
	}

	/**
	 * This method simulates the act of a player submitting a red apple
	 * to the judge.  This can only be done during the ROUND_PLAY phase
	 * of the game.  Also, this method checks to ensure that the player
	 * actually possess the apple and that the player is a player in the
	 * game.  Upon successful play, the <code>applePlayed</code> event
	 * is fired.  If this is the last of the active players to play a
	 * card, the <code>judgementReady</code> event is also fired.
	 * 
	 * @param player
	 * @param apple
	 * @return
	 */
	public Result play(Player player, RedApple apple) {
		if (player == null || !players.contains(player) || apple == null) {
			return Result.ERROR_INVALID_PARAMETER;
		} else if (phase != GamePhase.ROUND_PLAY 
				|| applesToJudge.keySet().contains(player) 
				|| player.equals(judge) 
				|| !player.isAbleToPlay()) {
			return Result.ERROR_PROHIBITED;
		} 
		if (!player.isActive()) {
			activatePlayer(player);
		}	
		if (player.playApple(apple)) {
			applesToJudge.put(player, apple);
			eventListener.applePlayed(player, apple);
			checkForJudgementPhase();
			return Result.SUCCESS;
		} else {
			return Result.ERROR_INVALID_PARAMETER;
		}
	}
	
	/**
	 * Called when the judge has decided.  If the game is configured to
	 * auto-start the rounds, the next round will be started immediately.
	 * The <code>roundWon</code> event is fired if the parameters and
	 * phase are appropriate.
	 * 
	 * @param selected
	 * @return
	 */
	public Result judge(RedApple selected) {
		if (selected == null || !applesToJudge.containsValue(selected)) {
			return Result.ERROR_INVALID_PARAMETER;
		} else if (judge == null) {
			return Result.ERROR_GAME_UNINITIALIZED;
		} else if (!isTimeToJudge()) {
			return Result.ERROR_PROHIBITED;
		}
		/* Activate the judge if necessary */
		if (!judge.isActive()) {
			activatePlayer(judge);
		}
		/* End the round. */
		phase = GamePhase.ROUND_OVER;
		++roundsPlayed;
		/* Check for winners...there's a possibility mulitple players played the same apple. */
		Set<Player> winners = new HashSet<Player>(players.size());
		for (Player p : applesToJudge.keySet()) {
			if (selected.equals(applesToJudge.get(p))) {
				winners.add(p);
				log.fine("Awarding point to " + p);
				p.awardPoint(greenApple, selected);
				p.incrementRoundsPlayed();
			}
		}
		if (winners.isEmpty()) { /* not sure why this would happen. */
			return Result.ERROR_INVALID_PARAMETER;
		} else {
			if (winners.size() == 1) {
				eventListener.roundWon(judge, winners.iterator().next(), selected, greenApple);
			} else {
				eventListener.roundWonByMultiplePlayers(judge, winners, selected, greenApple);
			}
			/* if points needed to win < 0, assume that winner has to be declared manually. */
			if (pointsNeededToWin >= 0) {
				checkForGameWinner(false);
			}
		}
		/* If the game hasn't ended and auto start is enabled, start next round. */
		if (!isOver() && config.autoStartRound()) {
			startRound();
		}
		return Result.SUCCESS;
	}

	/**
	 * Returns the collection of RedApples that have been submitted thus
	 * far for judgement.
	 * 
	 * @return
	 */
	public Collection<RedApple> getApplesToJudge() {
		return applesToJudge == null ? null : applesToJudge.values();
	}
	
	/**
	 * Determines if judgement can be made now.
	 * 
	 * @return
	 */
	public boolean isTimeToJudge() {
		return phase == GamePhase.ROUND_JUDGE;
	}
	
	/**
	 * Indicates if players can play their RedApples at this time.
	 * 
	 * @return
	 */
	public boolean isTimeToPlayRedApples() {
		return phase == GamePhase.ROUND_PLAY;
	}
	
	/**
	 * Indicates if the round is over.  If rounds are configured to
	 * start automatically, then don't expect this method to return
	 * true since the game leaves the round over phase as quickly as
	 * it enters it.
	 * 
	 * @return
	 */
	public boolean isRoundOver() {
		return phase == GamePhase.ROUND_OVER;
	}
	
	/**
	 * Allows you to abort the current round for whatever reason. Any
	 * played red apples are returned to the players hands.  New
	 * round is started only if autoStartRound is set to true
	 * by the GameConfiguration.
	 * 
	 * @return NO_EFFECT if the game isn't started, SUCCESS otherwise
	 */
	public Result abortRound() {
		if (!isStarted()) {
			return Result.NO_EFFECT;
		}
		/* Return cards to players hands. */
		for (Player p : applesToJudge.keySet()) {
			RedApple apple = applesToJudge.get(p);
			p.dealApple(applesToJudge.get(p));
			eventListener.redAppleDealt(p, apple);
		}
		if (config.autoStartRound()) {
			startRound();
		}
		return Result.SUCCESS;
	}
	
	/**
	 * Checks if judgement can occur, and if so, fires the
	 * <code>readyToJudge</code> event.
	 *
	 */
	public void checkForJudgementPhase() {
		if (phase == GamePhase.ROUND_PLAY
				/* only active players need to have played in order to prompt judgement. */
				&& getPlayersWhoHaveNotPlayedRedApples(getActivePlayers()).isEmpty()) {
			eventListener.readyToJudge(judge, applesToJudge.values());
			phase = GamePhase.ROUND_JUDGE;
		}
	}
	
	/**
	 * Immediately ends the game.  No winner declared, but the gameTerminiated
	 * event is fired.
	 * 
	 * @return NO_EFFECT if the games is already over, ERROR_PROHIBITED if
	 * 		the game hasn't started yet, SUCCESS otherwise
	 */
	public Result terminate() {
		if (phase == GamePhase.INIT) {
			return Result.ERROR_PROHIBITED;
		} else if (isOver()) {
			return Result.NO_EFFECT;
		}
		phase = GamePhase.GAME_OVER;
		eventListener.gameTerminated();
		return Result.SUCCESS;
	}

}
