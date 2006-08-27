/*
 * GameTest.java - created Jul 29, 2006 9:03:55 AM
 * $Id$
 */
package com.ourbabywolf.apples2apples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

/**
 * @author joe@ourbabywolf.com
 *
 */
public class GameTest {
	
	private static final Logger log = Logger.getLogger(GameTest.class.getName());
	
	private Deck<RedApple> redApples;
	private Deck<GreenApple> greenApples;
	
	private static class GameTestDeck<AppleType extends Apple> implements Deck<AppleType> {

		private final AppleType apple;
		public GameTestDeck(AppleType apple) {
			this.apple = apple;
		}
		public AppleType draw() {
			return apple;
		}
		public String getDescription() {
			return "Test deck for GameTest.java";
		}
		public boolean isExhausted() {
			return false;
		}
		public void shuffle() {
		}
		public void combine(Deck<AppleType> deck) {
		}
	}
	
	private static class CountingEventListener extends BaseGameEventListener {
		public int applePlayedEventCounter = 0;
		@Override
		public void applePlayed(Player player, RedApple applePlayed) {
			super.applePlayed(player, applePlayed);
			++applePlayedEventCounter;
		}
		public int gameTerminatedEventCounter = 0;
		@Override
		public void gameTerminated() {
			super.gameTerminated();
			++gameTerminatedEventCounter;
		}
		public int gameWonEventCounter = 0;
		@Override
		public void gameWon(Player gameWinner) {
			super.gameWon(gameWinner);
			++gameWonEventCounter;
		}
		public int gameWonByMultiplePlayersEventCounter = 0;
		@Override
		public void gameWonByMultiplePlayers(Collection<Player> gameWinners) {
			super.gameWonByMultiplePlayers(gameWinners);
			++gameWonByMultiplePlayersEventCounter;
		}
		public int greenAppleDeckExhaustedEventCounter = 0;
		@Override
		public void greenAppleDeckExhausted() {
			super.greenAppleDeckExhausted();
			++greenAppleDeckExhaustedEventCounter;
		}
		public int greenApplePlayedEventCounter = 0;
		@Override
		public void greenApplePlayed(GreenApple greenApple) {
			super.greenApplePlayed(greenApple);
			++greenApplePlayedEventCounter;
		}
		public int judgeChangedEventCounter = 0;
		@Override
		public void judgeChanged(Player newJudge) {
			super.judgeChanged(newJudge);
			++judgeChangedEventCounter;
		}
		public int minimumNbrOfPlayersJoinedGameEventCounter = 0;
		@Override
		public void minimumNbrOfPlayersJoinedGame(Collection<Player> players) {
			super.minimumNbrOfPlayersJoinedGame(players);
			++minimumNbrOfPlayersJoinedGameEventCounter;
		}
		public int maximumNbrOfPlayersJoinedGameEventCounter = 0;
		@Override
		public void maximumNbrOfPlayersJoinedGame(Collection<Player> players) {
			super.maximumNbrOfPlayersJoinedGame(players);
			++maximumNbrOfPlayersJoinedGameEventCounter;
		}
		public int notEnoughPlayersToPlayGameEventCounter = 0;
		@Override
		public void notEnoughPlayersToPlayGame(Collection<Player> players) {
			super.notEnoughPlayersToPlayGame(players);
			++notEnoughPlayersToPlayGameEventCounter;
		}
		public int playResumedEventCounter = 0;
		@Override
		public void playResumed(Player player) {
			super.playResumed(player);
			++playResumedEventCounter;
		}
		public int playSuspendedEventCounter = 0;
		@Override
		public void playSuspended(Player player) {
			super.playSuspended(player);
			++playSuspendedEventCounter;
		}
		public int playerActivatedEventCounter = 0;
		@Override
		public void playerActivated(Player player) {
			super.playerActivated(player);
			++playerActivatedEventCounter;
		}
		public int playerInactivatedEventCounter = 0;
		@Override
		public void playerInactivated(Player player) {
			super.playerInactivated(player);
			++playerInactivatedEventCounter;
		}
		public int playerJoinedGameEventCounter = 0;
		@Override
		public void playerJoinedGame(Player player) {
			super.playerJoinedGame(player);
			++playerJoinedGameEventCounter;
		}
		public int playerLeftGameEventCounter = 0;
		@Override
		public void playerLeftGame(Player player) {
			super.playerLeftGame(player);
			++playerLeftGameEventCounter;
		}
		public int playerNickChangedEventCounter = 0;
		@Override
		public void playerNickChanged(Player player, String oldNick) {
			super.playerNickChanged(player, oldNick);
			++playerNickChangedEventCounter;
		}
		public int pointsNeededToWinChangedEventCounter = 0;
		@Override
		public void pointsNeededToWinChanged(int newPointsNeededToWin, int oldPointsNeededToWin) {
			super.pointsNeededToWinChanged(newPointsNeededToWin, oldPointsNeededToWin);
			++pointsNeededToWinChangedEventCounter;
		}
		public int readyToJudgeEventCounter = 0;
		@Override
		public void readyToJudge(Player judge, Collection<RedApple> cards) {
			super.readyToJudge(judge, cards);
			++readyToJudgeEventCounter;
		}
		public int redAppleDealtEventCounter = 0;
		@Override
		public void redAppleDealt(Player player, RedApple dealtApple) {
			super.redAppleDealt(player, dealtApple);
			++redAppleDealtEventCounter;
		}
		public int redAppleDeckExhaustedEventCounter = 0;
		@Override
		public void redAppleDeckExhausted() {
			super.redAppleDeckExhausted();
			++redAppleDeckExhaustedEventCounter;
		}
		public int roundStartedEventCounter = 0;
		@Override
		public void roundStarted(GreenApple apple, Player roundJudge) {
			super.roundStarted(apple, roundJudge);
			++roundStartedEventCounter;
		}
		public int roundWonEventCounter = 0;
		@Override
		public void roundWon(Player judge, Player roundWinner, RedApple winningApple, GreenApple pointWon) {
			super.roundWon(judge, roundWinner, winningApple, pointWon);
			++roundWonEventCounter;
		}
		public int roundWonByMultiplePlayersEventCounter = 0;
		@Override
		public void roundWonByMultiplePlayers(Player judge, Collection<Player> roundWinners, RedApple winningApple, GreenApple pointWon) {
			super.roundWonByMultiplePlayers(judge, roundWinners, winningApple, pointWon);
			++roundWonByMultiplePlayersEventCounter;
		}	
	}
	
	private static class EZGameConfiguration implements GameConfiguration {
		private boolean autoStart = false;
		private boolean fixPoints = true;
		private int cardsPerHand = 7;
		private int maxPlayers = 5;
		private int minPlayers = 3;
		private int pointsNeededToWin = 1;
		private boolean rotateJudge = true;
		private boolean leaveDuringGame = false;
		private boolean joinDuringGame = true;
		public EZGameConfiguration() { }
		public EZGameConfiguration(final boolean autoStart, 
				final boolean fixPoints, final int cardsPerHand, 
				final int maxPlayers, final int minPlayers,
				final int pointsNeededToWin, final boolean rotateJudge, 
				final boolean leaveDuringGame, final boolean joinDuringGame) {
			this.autoStart = autoStart;
			this.fixPoints = fixPoints;
			this.cardsPerHand = cardsPerHand;
			this.maxPlayers = maxPlayers;
			this.minPlayers = minPlayers;
			this.pointsNeededToWin = pointsNeededToWin;
			this.rotateJudge = rotateJudge;
			this.leaveDuringGame = leaveDuringGame;
			this.joinDuringGame = joinDuringGame;
		}
		public boolean autoStartRound() { return autoStart; }
		public boolean fixPointsNeededToWinAtStart() { return fixPoints; }
		public int getCardsPerHand() { return cardsPerHand; }
		public int getMaxNbrOfPlayers() { return maxPlayers; }
		public int getMinNbrOfPlayers() { return minPlayers; }
		public int getPointsNeededToWin(int nbrOfPlayers) {	return pointsNeededToWin; }
		public boolean isJudgeRotated() { return rotateJudge;	}
		public boolean playersCanJoinDuringGame() { return leaveDuringGame; }
		public boolean playersCanLeaveDuringGame() { return joinDuringGame; }
	}
	
	@Before
	public void setUp() {
		redApples = new GameTestDeck<RedApple>(new RedApple("testnoun"));
		greenApples = new GameTestDeck<GreenApple>(new GreenApple("testnoun"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullListener() {
		new Game(null, redApples, greenApples);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullRedAppleDeck() {
		new Game(new CountingEventListener(), null, greenApples);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullGreenAppleDeck() {
		new Game(new CountingEventListener(), redApples, null);
	}
	
	@Test
	public void testNewGame() {
		CountingEventListener cel = new CountingEventListener();
		GameConfiguration config = new EZGameConfiguration(false, false, 1, 5, 3, 1, false, true, false); 
		Game game = new Game(cel, config, redApples, greenApples);
		
		assertFalse("Game should not be started.", game.isStarted());
		assertFalse("Game should not be over.", game.isOver());
		assertFalse("Game should not be ready to start if there are no players.", game.isReadyToStart());
		assertTrue("Game should have no players.", game.getPlayers().isEmpty());
		assertTrue("Game should have no winners.", game.getWinners().isEmpty());
		assertEquals("applePlayerEventCounter", 0, cel.applePlayedEventCounter);
		assertEquals("gameTerminatedEventCounter", 0, cel.gameTerminatedEventCounter);
		assertEquals("gameWonByMultiplePlayersEventCounter", 0, cel.gameWonByMultiplePlayersEventCounter);
		assertEquals("gameWonEventCounter", 0, cel.gameWonEventCounter);
		assertEquals("greenAppleDeckExhaustedEventCounter", 0, cel.greenAppleDeckExhaustedEventCounter);
		assertEquals("greenApplePlayedEventCounter", 0, cel.greenApplePlayedEventCounter);
		assertEquals("judgeChangedEventCounter", 0, cel.judgeChangedEventCounter);
		assertEquals("maximumNbrOfPlayersJoinedGameEventCounter", 0, cel.maximumNbrOfPlayersJoinedGameEventCounter);
		assertEquals("minimumNbrOfPlayersJoinedGameEventCounter", 0, cel.minimumNbrOfPlayersJoinedGameEventCounter);
		assertEquals("notEnoughPlayersToPlayGameEventCounter", 0, cel.notEnoughPlayersToPlayGameEventCounter);
		assertEquals("playerActivatedEventCounter", 0, cel.playerActivatedEventCounter);
		assertEquals("playerInactivatedEventCounter", 0, cel.playerInactivatedEventCounter);
		assertEquals("playerJoinedGameEventCounter", 0, cel.playerJoinedGameEventCounter);
		assertEquals("playerLeftGameEventCounter", 0, cel.playerLeftGameEventCounter);
		assertEquals("playerNickChangedEventCounter", 0, cel.playerNickChangedEventCounter);
		assertEquals("playResumedEventCounter", 0, cel.playResumedEventCounter);
		assertEquals("playSuspendedEventCounter", 0, cel.playSuspendedEventCounter);
		assertEquals("pointsNeededToWinChangedEventCounter", 0, cel.pointsNeededToWinChangedEventCounter);
		assertEquals("readyToJudgeEventCounter", 0, cel.readyToJudgeEventCounter);
		assertEquals("redAppleDealtEventCounter", 0, cel.redAppleDealtEventCounter);
		assertEquals("redAppleDeckExhaustedEventCounter", 0, cel.redAppleDeckExhaustedEventCounter);
		assertEquals("roundStartedEventCounter", 0, cel.roundStartedEventCounter);
		assertEquals("roundWonByMultiplePlayersEventCounter", 0, cel.roundWonByMultiplePlayersEventCounter);
		assertEquals("roundWonEventCounter", 0, cel.roundWonEventCounter);
	}
	
	@Test
	public void testGetPlayer() {
		Game game = new Game(new CountingEventListener(), new EZGameConfiguration(
				false, false, 1, 2, 2, 2, false, false, false
				), redApples, greenApples);
		
		Player bob = game.getPlayer("bob");
		assertEquals("Should be zero players.", 0, game.getPlayers().size());
		assertNull("Bob should not exist as a player.", bob);
		
		game.join("joe");
		Player joe = game.getPlayer("joe");
		assertEquals("Should be one players.", 1, game.getPlayers().size());
		assertNotNull("Joe should not be null.", joe);
		assertEquals("Joe's id should be 'joe'", "joe", joe.getId());
		assertEquals("Joe has no nick, so his nicks the id.", joe.getId(), joe.getNick());
		
		game.join("katie", "tokki");
		assertEquals("Should be two players.", 2, game.getPlayers().size());
		Player katie = game.getPlayer("katie");
		assertNotNull("Katie should not be null.", katie);
		assertEquals("Katie's id should be 'joe'", "katie", katie.getId());
		assertEquals("Katie's nick is 'tokki'.", "tokki", katie.getNick());
		
		game.join("katie", "katiesnewnickname");
		assertEquals("Should still be two players.", 2, game.getPlayers().size());
		assertEquals("Katie cannot change nick by rejoining game.", "tokki", katie.getNick());
		
		Player tokki = game.getPlayer("tokki");
		assertNull("Tokki is a nick, not a player.", tokki);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testPlayersUnmodifiability() {
		Game game = new Game(new CountingEventListener(), 
				new EZGameConfiguration(false, false, 1, 12, 2, 2, true, false, false), 
				redApples, greenApples);
		game.join("joe");
		game.join("katie");
		game.getPlayers().add(new Player("caleb"));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testActivePlayersUnmodifiability() {
		Game game = new Game(new CountingEventListener(), 
				new EZGameConfiguration(false, false, 1, 12, 2, 2, true, false, false), 
				redApples, greenApples);
		game.join("joe");
		game.join("katie");
		game.getActivePlayers().add(new Player("caleb"));
	}
	
	@Test
	public void testActivePlayers() {
		CountingEventListener cel = new CountingEventListener();
		GameConfiguration config = new EZGameConfiguration(false, false, 3, 12, 3, 2, true, false, false);
		Game game = new Game(cel, config, redApples, greenApples);
		game.join("joe");
		Player joe = game.getPlayer("joe");
		game.join("katie");
		Player katie = game.getPlayer("katie");
		game.join("caleb");
		Player caleb = game.getPlayer("caleb");
		game.join("joshua");
		Player joshua = game.getPlayer("joshua");
		
		Set<Player> active = game.getActivePlayers();
		assertNotNull("There should be active players.", active);
		assertEquals("There should be 4 active players.", 4, active.size());
		assertEquals("Can't inactivate null player.", Game.Result.ERROR_INVALID_PARAMETER, game.inactivatePlayer(null));
		assertEquals("Can't inactivate non-existent player.", Game.Result.ERROR_INVALID_PARAMETER, game.inactivatePlayer(new Player("bob")));
		assertEquals("Can't activate null player.", Game.Result.ERROR_INVALID_PARAMETER, game.activatePlayer(null));
		assertEquals("Can't activate non-existent player.", Game.Result.ERROR_INVALID_PARAMETER, game.activatePlayer(new Player("bob")));
		assertEquals("Should be able to deactivate joe.", Game.Result.SUCCESS, game.inactivatePlayer(joe));
		assertEquals("Inactivated player event listener should have been fired by Joe.", 1, cel.playerInactivatedEventCounter);
		assertEquals("Inactivating joe again should have no effect.", Game.Result.NO_EFFECT, game.inactivatePlayer(joe));
		assertEquals("Inactivated player event listener should not have been fired again by Joe.", 1, cel.playerInactivatedEventCounter);
		active = game.getActivePlayers();
		assertEquals("There should be 3 active players.", 3, active.size());
		assertFalse("Active players should not include Joe.", active.contains(joe));
		assertEquals("Katie should be able to inactivate.", Game.Result.SUCCESS, game.inactivatePlayer(katie));
		assertEquals("Inactivated player event listener should have been fired by Katie.", 2, cel.playerInactivatedEventCounter);
		active = game.getActivePlayers();
		assertEquals("There should be 2 active players.", 2, active.size());
		assertFalse("Active players should not include Katie.", active.contains(katie));
		
		assertEquals("Can't start game w/o enough active players.", Game.Result.ERROR_PROHIBITED, game.startRound());

		assertEquals("Joe should be able to activate.", Game.Result.SUCCESS, game.activatePlayer(joe));
		assertEquals("Activated player event listener should have been fired by Joe.", 1, cel.playerActivatedEventCounter);
				
		active = game.getActivePlayers();
		assertEquals("There should be 3 active players again.", 3, active.size());
		assertTrue("Active players should include Joe.", active.contains(joe));
		
		assertEquals("Can start game when there are enough active players.", Game.Result.SUCCESS, game.startRound());
		/* For this round, we have Katie activating during play, allowing her to play cards. */
		game.play(caleb, caleb.getAppleFromHand(0));
		game.play(joshua, caleb.getAppleFromHand(0));
		assertEquals("Katie can join game during play.", Game.Result.SUCCESS, game.activatePlayer(katie));
		game.play(katie, caleb.getAppleFromHand(0));
		game.play(joe, caleb.getAppleFromHand(0));
	}
	
	@Test
	public void testGetPlayersSortedByPoints() {
		CountingEventListener cel = new CountingEventListener();
		GameConfiguration config = new EZGameConfiguration(false, false, 1, 15, 3, 1, false, true, false);
		Game game = new Game(cel, config, redApples, greenApples);
		assertTrue("No players.", game.getPlayersSortedByPoints().isEmpty());
		
		game.join("joe");
		Player joe = game.getPlayer("joe");
		assertEquals("Joe has no points.", 0, joe.getPoints());
		assertTrue("The only player is Joe.", game.getPlayersSortedByPoints().containsAll(game.getPlayers()));
		
		game.join("katie");
		Player katie = game.getPlayer("katie");
		assertEquals("Katie has no points.", 0, katie.getPoints());
		assertTrue("The players are Katie and Joe.", game.getPlayersSortedByPoints().containsAll(game.getPlayers()));
		
		katie.awardPoint(new GreenApple("pointy"), new RedApple("point"));
		Iterator it = game.getPlayersSortedByPoints().iterator();
		assertEquals("Katie should be the first in the list.", katie, it.next());
		assertEquals("Joe should be the next in the list.", joe, it.next());
		
		joe.awardPoint(new GreenApple("pointy"), new RedApple("point"));
		joe.awardPoint(new GreenApple("pointy2"), new RedApple("point2"));
		it = game.getPlayersSortedByPoints().iterator();
		assertEquals("Joe should be the first in the list.", joe, it.next());
		assertEquals("Katie should be the next in the list.", katie, it.next());
		
		katie.awardPoint(new GreenApple("pointy"), new RedApple("point"));
		katie.awardPoint(new GreenApple("pointy2"), new RedApple("point2"));
		it = game.getPlayersSortedByPoints().iterator();
		assertEquals("Katie should be the first in the list again.", katie, it.next());
		assertEquals("Joe should be the next in the list again.", joe, it.next());
	}
	
	@Test
	public void testIndirectReactivation() {
		// TODO - if you play, you become active.  If you judge and you're the judge, you become active
	}
	
	@Test
	public void testUnqiueNicks() {
		CountingEventListener cel = new CountingEventListener();
		GameConfiguration config = new EZGameConfiguration(false, false, 1, 15, 3, 1, false, true, false);
		Game game = new Game(cel, config, redApples, greenApples);
		
		game.join("joe");
		Player joe = game.getPlayer("joe");
		assertEquals("Joe by default has a nick of 'joe'", "joe", joe.getNick());
		game.join("katie", "tokki");
		Player katie = game.getPlayer("katie");
		assertEquals("Katie has a nick of 'tokki'", "tokki", katie.getNick());
		game.join("joshua");
		Player joshua = game.getPlayer("joshua");
		assertEquals("Joshua by default has a nick of 'joshua'", "joshua", joshua.getNick());
		game.join("caleb", "captainsupercaleb");
		Player caleb = game.getPlayer("caleb");
		assertEquals("Caleb has a nick of 'captainsupercaleb'", "captainsupercaleb", caleb.getNick());
		
		assertEquals("tokki can't join game since it's Katie's nick.", Game.Result.ERROR_INVALID_PARAMETER, game.join("tokki"));
		
		assertEquals("Fred can't join game w/ nick 'tokki'", Game.Result.ERROR_INVALID_PARAMETER, game.join("fred", "tokki"));
		assertEquals("Fred can't join game w/ nick 'joe'", Game.Result.ERROR_INVALID_PARAMETER, game.join("fred", "joe"));
		assertEquals("Fred can join game w/ nick 'TOKKI'", Game.Result.SUCCESS, game.join("fred", "TOKKI"));
		Player fred = game.getPlayer("fred");
		
		assertEquals("Joe can't change nick to 'joshua'", Game.Result.ERROR_PROHIBITED, game.changePlayerNick(joe, "joshua"));
		assertEquals("Nick change event shouldn't have been fired.", 0, cel.playerNickChangedEventCounter);
		assertEquals("Joe can't change nick to 'captainsupercaleb'", Game.Result.ERROR_PROHIBITED, game.changePlayerNick(joe, "captainsupercaleb"));
		assertEquals("Nick change event again shouldn't have been fired.", 0, cel.playerNickChangedEventCounter);

		assertEquals("Null player can't change nick.", Game.Result.ERROR_INVALID_PARAMETER, game.changePlayerNick(null, "mr. bob"));
		assertEquals("Nick change event shouldn't have been fired for null.", 0, cel.playerNickChangedEventCounter);
		Player bob = new Player("bob");
		assertEquals("Nonexistent player can't change nick.", Game.Result.ERROR_INVALID_PARAMETER, game.changePlayerNick(bob, "mr. bob"));
		assertEquals("Nick change event shouldn't have been fired for non-existent player.", 0, cel.playerNickChangedEventCounter);
		assertEquals("Nonexistent player can't change nick to existing nick.", Game.Result.ERROR_INVALID_PARAMETER, game.changePlayerNick(bob, "tokki"));
		
		assertEquals("Changing nick to current nick has no effect.", Game.Result.NO_EFFECT, game.changePlayerNick(joe, "joe"));
		assertEquals("Nick change event shouldn't have been fired for no effect.", 0, cel.playerNickChangedEventCounter);
		assertEquals("Joshua can change to new nick.", Game.Result.SUCCESS, game.changePlayerNick(joshua, "superjoshua"));
		assertEquals("Nick change event should have been fired.", 1, cel.playerNickChangedEventCounter);
		assertEquals("Joshua's new nick should be 'superjoshua'", "superjoshua", joshua.getNick());
		
		game.leave(joe);
		assertEquals("Now that Joe left, Katie can change nick to 'joe'", Game.Result.SUCCESS, game.changePlayerNick(katie, "joe"));
		assertEquals("Nick change event should have been fired again.", 2, cel.playerNickChangedEventCounter);
		assertEquals("Katie's new nick should be 'joe'", "joe", katie.getNick());
		assertEquals("Now that Katie changed nick, Fred can change nick to 'tokki'", Game.Result.SUCCESS, game.changePlayerNick(fred, "tokki"));
		assertEquals("Nick change event should have been fired yet again.", 3, cel.playerNickChangedEventCounter);
		assertEquals("Fred's new nick should be 'tokki'", "tokki", fred.getNick());
		
		assertEquals("Can't leave game as invalid player with and id equal to valid player's nick.", Game.Result.ERROR_INVALID_PARAMETER, game.leave(new Player("captainsupercaleb")));
		game.leave(caleb);
		assertEquals("Now that Caleb left, Joshua can change nick to 'captainsupercaleb", Game.Result.SUCCESS, game.changePlayerNick(joshua, "captainsupercaleb"));
		assertEquals("Nick change event should have been fired once more.", 4, cel.playerNickChangedEventCounter);
		assertEquals("Joshua's new nick should be 'captainsupercaleb'", "captainsupercaleb", joshua.getNick());
		
	}
	
	@Test
	public void testNullNick() {
		CountingEventListener cel = new CountingEventListener();
		GameConfiguration config = new EZGameConfiguration(false, false, 1, 5, 3, 1, false, true, false);
		Game game = new Game(cel, config, redApples, greenApples);

		game.join("katie");
		assertEquals("Katie's nick should be her id", game.getPlayer("katie").getId(), game.getPlayer("katie").getNick());
		game.join("joe", null);
		assertEquals("Joe's nick should be his id", game.getPlayer("joe").getId(), game.getPlayer("joe").getNick());
	}
	
	@Test
	public void testCheater() {
		CountingEventListener cel = new CountingEventListener();
		GameConfiguration config = new EZGameConfiguration(false, false, 1, 5, 3, 1, false, true, false);
		Game game = new Game(cel, config, redApples, greenApples);

		game.join("katie");
		assertFalse("Joe is not a cheater.", game.getPlayer("joe") instanceof Cheater);
		game.join("joe", "Joe Wolf");
		assertFalse("Joe is not a cheater.", game.getPlayer("joe") instanceof Cheater);
		game.join("w", "George W. Bush", false);
		assertFalse("W. is not a cheater.", game.getPlayer("joe") instanceof Cheater);
		
		game.join("ted", "Ted Kennedy", true);
		assertTrue("Ted Kennedy is a cheater.", game.getPlayer("ted") instanceof Cheater);
	}
	
	@Test
	public void testPlayingApples() {
		CountingEventListener cel = new CountingEventListener();
		GameConfiguration config = new EZGameConfiguration(false, false, 7, 7, 3, 2, false, true, true);
		Game game = new Game(cel, config, redApples, greenApples);
		
		game.join("joe");
		Player joe = game.getPlayer("joe");
		game.join("katie");
		Player katie = game.getPlayer("katie");
		game.join("joshua");
		Player joshua = game.getPlayer("joshua");
		game.join("caleb");
		Player caleb = game.getPlayer("caleb");
		
		game.setJudge(katie);
		
		game.startRound();
		assertTrue("It should be time to play red apples.", game.isTimeToPlayRedApples());
		assertTrue("No one has played apples yet.", game.getPlayersWhoHaveNotPlayedRedApples().size() == 3);
		assertEquals("No apple played events should have been triggered.", 0, cel.applePlayedEventCounter);
		RedApple apple = joe.getAppleFromHand(0); /* same 1 apple for every player. */
		
		assertEquals("Joe should be able to play RedApple.", Game.Result.SUCCESS, game.play(joe, apple));
		assertEquals("Joe should have triggered apple played event.", 1, cel.applePlayedEventCounter);
		assertTrue("Only Joe has played an apple.", game.getPlayersWhoHaveNotPlayedRedApples().size() == 2);
		
		assertEquals("Judge shouldn't be able to play RedApple.", Game.Result.ERROR_PROHIBITED, game.play(katie, apple));
		assertEquals("Judge should not have triggered apple played event.", 1, cel.applePlayedEventCounter);
		assertTrue("Still only Joe has played an apple.", game.getPlayersWhoHaveNotPlayedRedApples().size() == 2);
		
		assertEquals("Caleb should be able to play RedApple.", Game.Result.SUCCESS, game.play(caleb, apple));
		assertEquals("Caleb should have triggered apple played event.", 2, cel.applePlayedEventCounter);
		assertTrue("Only Joe & Caleb have played apples.", game.getPlayersWhoHaveNotPlayedRedApples().size() == 1);
		
		assertEquals("Fred should be able to join game in progress.", Game.Result.SUCCESS, game.join("fred"));
		Player fred = game.getPlayer("fred");
		assertEquals("Fred just joined game in progress, can't play until next round.", Game.Result.ERROR_PROHIBITED, game.play(fred, apple));
		assertEquals("Fred should not have triggered apple played event.", 2, cel.applePlayedEventCounter);
		assertTrue("Still only Joe & Caleb have played apples.", game.getPlayersWhoHaveNotPlayedRedApples().size() == 1);
		
		assertEquals("Joshua should be able to play RedApple.", Game.Result.SUCCESS, game.play(joshua, apple));
		assertEquals("Joshua should have triggered apple played event.", 3, cel.applePlayedEventCounter);
		assertTrue("Everyone has played apples.", game.getPlayersWhoHaveNotPlayedRedApples().isEmpty());
		assertTrue("No one is left who hasn't played apples.", game.getPlayersWhoHaveNotPlayedRedApples().size() == 0);
		assertTrue("It should be time to judge.", game.isTimeToJudge());
		assertEquals("Ready-to-judge event should be fired.", 1, cel.readyToJudgeEventCounter);
		
		assertEquals("Can't play cards during judgement phase.", Game.Result.ERROR_PROHIBITED, game.play(joe, apple));
		assertEquals("Patrick should be able to join during game.", Game.Result.SUCCESS, game.join("patrick"));
		Player patrick = game.getPlayer("patrick");
		assertTrue("Patrick hasn't played apples.", game.getPlayersWhoHaveNotPlayedRedApples().contains(patrick));
		assertTrue("Even though Patrick hasn't played yet, it should be time to judge.", game.isTimeToJudge());
		assertTrue("Patrick has no cards yet.", patrick.getHand().isEmpty());
		assertEquals("Patrick joined during judgement phase, and shouldn't be able to play apple.", Game.Result.ERROR_PROHIBITED, game.play(patrick, apple));
		
		/* end this round */
		assertEquals("Judgement should succeed.", Game.Result.SUCCESS, game.judge(apple));
		assertEquals("Round won by multiple players event should have been fired.", 1, cel.roundWonByMultiplePlayersEventCounter);
		assertEquals("Joe should have been awarded green apple.", 1, joe.getGreenApples().size());
		assertEquals("Caleb should have been awarded green apple.", 1, caleb.getGreenApples().size());
		assertEquals("Joshua should have been awarded green apple.", 1, joshua.getGreenApples().size());
		assertEquals("Fred should not have been awarded green apple.", 0, fred.getGreenApples().size());
		assertEquals("Patrick should not have been awarded green apple.", 0, patrick.getGreenApples().size());
		assertEquals("Katie should not have been awarded green apple.", 0, katie.getGreenApples().size());
		
		/* and start a new one */
		game.startRound();
		assertTrue("No one has played apples for round 2.", game.getPlayersWhoHaveNotPlayedRedApples().size() == game.getPlayers().size() - 1 /* minus 1 for the judge */);
		assertTrue("Fred should be able to play now.", fred.isAbleToPlay());
		assertFalse("Fred should have been dealt cards.", fred.getHand().isEmpty());
		assertTrue("Patrck should be able to play now.", patrick.isAbleToPlay());
		assertFalse("Patrick should have been dealt cards.", patrick.getHand().isEmpty());
		
		/* Deactivate fred before play. */
		assertEquals("Fred can be deactivated.", Game.Result.SUCCESS, game.inactivatePlayer(fred));
		assertEquals("Joshua should be able to play RedApple again.", Game.Result.SUCCESS, game.play(joshua, apple));
		assertEquals("Joshua should have triggered apple played event again.", 4, cel.applePlayedEventCounter);
		assertEquals("Joshua can't play twice.", Game.Result.ERROR_PROHIBITED, game.play(joshua, apple));
		assertEquals("Joshua should not have triggered apple played event again.", 4, cel.applePlayedEventCounter);
		assertEquals("Caleb should be able to play RedApple again.", Game.Result.SUCCESS, game.play(caleb, apple));
		assertEquals("Caleb should have triggered apple played event again.", 5, cel.applePlayedEventCounter);
		assertEquals("Patrick should be able to play RedApple again.", Game.Result.SUCCESS, game.play(patrick, apple));
		assertEquals("Patrick should have triggered apple played event again.", 6, cel.applePlayedEventCounter);
		assertEquals("Patrick can be deactivated.", Game.Result.SUCCESS, game.inactivatePlayer(patrick));
		assertEquals("Joe should be able to play RedApple again.", Game.Result.SUCCESS, game.play(joe, apple));
		assertEquals("Joe should have triggered apple played event again.", 7, cel.applePlayedEventCounter);
		assertTrue("Everyone has played apples but Fred.", game.getPlayersWhoHaveNotPlayedRedApples().contains(fred));
		assertTrue("Only Fred has not played red apples.", game.getPlayersWhoHaveNotPlayedRedApples().size() == 1);
		
		assertTrue("There should be no winners.", game.getWinners().isEmpty());
		
		/* Two points needed to win game. */
		assertTrue("Despite Fred not playing, we can judge now.", game.isTimeToJudge());
		assertEquals("Judgement should succeed.", Game.Result.SUCCESS, game.judge(apple));
		assertEquals("Round won by multiple players event should have been fired.", 2, cel.roundWonByMultiplePlayersEventCounter);
		assertEquals("Game won by multiple players event should have been fired.", 1, cel.gameWonByMultiplePlayersEventCounter);
		assertEquals("Joe should have been awarded green apple.", 2, joe.getGreenApples().size());
		assertEquals("Caleb should have been awarded green apple.", 2, caleb.getGreenApples().size());
		assertEquals("Joshua should have been awarded green apple.", 2, joshua.getGreenApples().size());
		assertEquals("Patrick should have been awarded green apple.", 1, patrick.getGreenApples().size());
		assertEquals("Fred should not have been awarded green apple.", 0, fred.getGreenApples().size());
		assertEquals("Katie should not have been awarded green apple.", 0, katie.getGreenApples().size());
		
		assertTrue("Game should be over now.", game.isOver());
		assertTrue("Joe's a winner.", game.getWinners().contains(joe));
		assertTrue("Caleb's a winner.", game.getWinners().contains(caleb));
		assertTrue("Joshua's a winner.", game.getWinners().contains(joshua));
		
		assertEquals("Can't play apples when game is over and you won.", Game.Result.ERROR_PROHIBITED, game.play(joe, apple));
		assertEquals("Can't play apples when game is over and you didn't win.", Game.Result.ERROR_PROHIBITED, game.play(fred, apple));
		
	}
	
	@Test
	public void testTerminateGame() {
		CountingEventListener cel = new CountingEventListener();
		GameConfiguration config = new EZGameConfiguration(false, false, 1, 2, 2, 2, true, false, false);
		Game game = new Game(cel, config, redApples, greenApples);
		
		assertEquals("Can't terminate a newgame.", Game.Result.ERROR_PROHIBITED, game.terminate());
		assertEquals("Terminate event shouldn't have been fired.", 0, cel.gameTerminatedEventCounter);
		
		game.join("joe");
		assertEquals("Can't terminate an game unable to start.", Game.Result.ERROR_PROHIBITED, game.terminate());
		game.join("katie");
		assertTrue("Game can start now.", game.isReadyToStart());
		assertEquals("Can't terminate an unstarted game.", Game.Result.ERROR_PROHIBITED, game.terminate());
				
		assertEquals("Game should be able to start.", Game.Result.SUCCESS, game.startRound());
		assertEquals("Can terminate an started game.", Game.Result.SUCCESS, game.terminate());
		assertEquals("Terminate event should have been fired.", 1, cel.gameTerminatedEventCounter);
		
		assertEquals("Terminating an over game has no effect.", Game.Result.NO_EFFECT, game.terminate());
		assertEquals("Terminate event shouldn't have been fired.", 1, cel.gameTerminatedEventCounter);
	}
	
	@Test
	public void testSetJudgeRotated() {
		CountingEventListener cel = new CountingEventListener();
		GameConfiguration config = new EZGameConfiguration(false, false, 1, 4, 3, 2, true, false, false);
		Game game = new Game(cel, config, redApples, greenApples);
		
		game.join("joe");
		Player joe = game.getPlayer("joe");
		game.join("katie");
		Player katie = game.getPlayer("katie");
		game.join("joshua");
		Player joshua = game.getPlayer("joshua");
		game.join("caleb");
		Player caleb = game.getPlayer("caleb");
		
		Set<Player> pastJudges = new HashSet<Player>(4);
		assertNull("Judge should not be set yet.", game.getJudge());
		int judgeChangedExpected = 0;
		/* lets go through a rotation some arbitrary number of times...why not? */
		for (int i = 0; i < 10; ++i) {
			assertEquals(i + " Should be allowed to set judge before game starts.", Game.Result.SUCCESS, game.setJudge());
			assertTrue(i + " 1st Judge should be unique.", pastJudges.add(game.getJudge()));
			assertEquals(i + " Judge chaged event should have been fired.", ++judgeChangedExpected, cel.judgeChangedEventCounter);
			log.finer(i + " Judge is " + game.getJudge());
			assertEquals(" Should be allowed to set judge again before game starts.", Game.Result.SUCCESS, game.setJudge());
			assertTrue(i + " 2nd Judge should be unique.", pastJudges.add(game.getJudge()));
			assertEquals(i + " Judge chaged event should have been fired.", ++judgeChangedExpected, cel.judgeChangedEventCounter);
			log.finer(i + " Judge is " + game.getJudge());
			assertEquals(i + " Should be allowed to set judge yet again before game starts.", Game.Result.SUCCESS, game.setJudge());
			assertTrue(i + "3rd Judge should be unique.", pastJudges.add(game.getJudge()));
			assertEquals(i + " Judge chaged event should have been fired.", ++judgeChangedExpected, cel.judgeChangedEventCounter);
			log.finer(i + " Judge is " + game.getJudge());
			assertEquals(i + " Should be allowed to set judge a 4th time before game starts.", Game.Result.SUCCESS, game.setJudge());
			assertTrue(i + " 4th Judge should be unique.", pastJudges.add(game.getJudge()));
			assertEquals(i + " Judge chaged event should have been fired.", ++judgeChangedExpected, cel.judgeChangedEventCounter);
			log.finer(i + " Judge is " + game.getJudge());
			assertEquals(i + " Should be allowed to set judge a 5th time before game starts.", Game.Result.SUCCESS, game.setJudge());
			assertFalse(i + " 5th Judge should be not unique.", pastJudges.add(game.getJudge()));
			assertEquals(i + " Judge chaged event should have been fired.", ++judgeChangedExpected, cel.judgeChangedEventCounter);
			log.finer(i + " Judge is " + game.getJudge());
			pastJudges.remove(game.getJudge());
			assertEquals(i + " Should be allowed to set judge a 6th time before game starts.", Game.Result.SUCCESS, game.setJudge());
			assertFalse(i + " 6th Judge should be not unique.", pastJudges.add(game.getJudge()));
			assertEquals(i + " Judge chaged event should have been fired.", ++judgeChangedExpected, cel.judgeChangedEventCounter);
			log.finer(i + " Judge is " + game.getJudge());
			pastJudges.remove(game.getJudge());
			assertEquals(i + " Should be allowed to set judge a 7th time before game starts.", Game.Result.SUCCESS, game.setJudge());
			assertFalse(i + " 7th Judge should be not unique.", pastJudges.add(game.getJudge()));
			assertEquals(i + " Judge chaged event should have been fired.", ++judgeChangedExpected, cel.judgeChangedEventCounter);
			log.finer(i + " Judge is " + game.getJudge());
			pastJudges.remove(game.getJudge());
			assertEquals(i + " Should be allowed to set judge a 8th time before game starts.", Game.Result.SUCCESS, game.setJudge());
			assertFalse(i + " 8th Judge should be not unique.", pastJudges.add(game.getJudge()));
			assertEquals(i + " Judge chaged event should have been fired.", ++judgeChangedExpected, cel.judgeChangedEventCounter);
			log.finer(i + " Judge is " + game.getJudge());
			pastJudges.remove(game.getJudge());
			assertTrue(i + " Judges rotated successfully.", pastJudges.isEmpty());
		}
		
		/* see if we can set the judge during the game. */
		game.startRound();
		assertEquals("Judge chaged event should have been fired when round started.", ++judgeChangedExpected, cel.judgeChangedEventCounter);
		
		assertEquals("Can't change judge during play round.", Game.Result.ERROR_PROHIBITED, game.setJudge());
		assertEquals("Judge chaged event should not have been fired during round.", judgeChangedExpected, cel.judgeChangedEventCounter);
		
		RedApple winner = katie.getAppleFromHand(0); /* all cards are the same anyways, so they all have to play them */
		/* one of these players is the judge, I don't know which, so I'll make them all play, and the game will ignore the card played by the judge. */
		log.finer("Joe played apple: " + game.play(joe, winner));
		log.finer("Katie played apple: " + game.play(katie, winner));	
		log.finer("Caleb played apple: " + game.play(caleb, winner));
		log.finer("Joshua played apple: " + game.play(joshua, winner));
		assertTrue("We should be ready to judge now.", game.isTimeToJudge());
		assertEquals("Can't change judge during judgement round.", Game.Result.ERROR_PROHIBITED, game.setJudge());
		assertEquals("Judge chaged event should not have been fired during judgement.", judgeChangedExpected, cel.judgeChangedEventCounter);
		assertEquals("Should be able to judge now.", Game.Result.SUCCESS, game.judge(winner));

		assertEquals("Can change judge during round over phase.", Game.Result.SUCCESS, game.setJudge());
		assertEquals("Judge chaged event should have been after round ended.", ++judgeChangedExpected, cel.judgeChangedEventCounter);
		assertEquals("Can specifically change judge during round over phase.", Game.Result.SUCCESS, game.setJudge(joe));
		assertEquals("Judge chaged event should have been fired after judge was specifically changed.", ++judgeChangedExpected, cel.judgeChangedEventCounter);
		
		assertEquals("We can terminate game now.", Game.Result.SUCCESS, game.terminate());
		assertTrue("Terminated game should be over.", game.isOver());
		assertEquals("Can't change judge when game is over.", Game.Result.ERROR_PROHIBITED, game.setJudge());
		assertEquals("Judge chaged event should not have been fired after game was terminated.", judgeChangedExpected, cel.judgeChangedEventCounter);
	}
	
	@Test
	public void testSetJudgeFixed() {
		CountingEventListener cel = new CountingEventListener();
		GameConfiguration config = new EZGameConfiguration(false, false, 1, 4, 2, 2, false, false, false);
		Game game = new Game(cel, config, redApples, greenApples);
		
		assertEquals("Can't set judge to nonexistent player.", Game.Result.ERROR_INVALID_PARAMETER, game.setJudge(new Player("bob")));
		game.join("joe");
		Player joe = game.getPlayer("joe");
		assertEquals("You can set judge during initialization.", Game.Result.SUCCESS, game.setJudge(joe));
		assertEquals("Setting judge to current judge has no effect.", Game.Result.NO_EFFECT, game.setJudge(joe));
		
		game.join("katie");
		Player katie = game.getPlayer("katie");
		game.startRound();
		assertTrue("Should be time to play red apples.", game.isTimeToPlayRedApples());
		assertEquals("Can't change judge during round play.", Game.Result.ERROR_PROHIBITED, game.setJudge(katie));
		assertEquals("Setting judge to current judge is prohibited during game play.", Game.Result.ERROR_PROHIBITED, game.setJudge(joe));
		
		game.play(katie, katie.getAppleFromHand(0));
		assertTrue("Should be time to judge.", game.isTimeToJudge());
		assertEquals("Can't change judge during round play.", Game.Result.ERROR_PROHIBITED, game.setJudge(katie));
		assertEquals("Setting judge to current judge has is prohibited during judgement.", Game.Result.ERROR_PROHIBITED, game.setJudge(joe));
		game.judge(game.getApplesToJudge().iterator().next());
		assertTrue("Round should be over", game.isRoundOver());
		assertEquals("Can change judge during round over phase.", Game.Result.SUCCESS, game.setJudge(katie));
		assertEquals("Setting judge to current judge has no effect round over phase.", Game.Result.NO_EFFECT, game.setJudge(katie));
		
		game.terminate();
		assertTrue("Game should be over", game.isOver());
		assertEquals("Can't change judge after the game is over.", Game.Result.ERROR_PROHIBITED, game.setJudge(joe));
		assertEquals("Setting judge to current judge is prohibited after game is over.", Game.Result.ERROR_PROHIBITED, game.setJudge(katie));
	}
	
	@Test
	public void testAutoStart() {
		// TODO
	}
	
	@Test
	public void testDeclareWinner() {
		// TODO
	}
	
	@Test
	public void testPointsFixedAtStart() {
		GameConfiguration config = new DefaultGameConfiguration() {
			@Override
			public int getPointsNeededToWin(int nbrOfPlayers) {
				return nbrOfPlayers;
			}
			@Override
			public boolean fixPointsNeededToWinAtStart() {
				return true;
			}
			@Override
			public int getMinNbrOfPlayers() {
				return 2;
			}
			@Override
			public boolean playersCanJoinDuringGame() {
				return true;
			}
			@Override
			public boolean autoStartRound() {
				return false;
			}
		};
		CountingEventListener cel = new CountingEventListener();
		Game game = new Game(cel, config, redApples, greenApples);
		game.join("joe");
		assertEquals("Points changed event should not be called.", 0, cel.pointsNeededToWinChangedEventCounter);
		Player joe = game.getPlayer("joe");
		game.join("katie");
		assertEquals("Points changed event should not be called.", 0, cel.pointsNeededToWinChangedEventCounter);
		Player katie = game.getPlayer("katie");
		game.startRound();
		assertEquals("Points changed event should be called at start.", 1, cel.pointsNeededToWinChangedEventCounter);
		int nbrOfPlayersAtStart = game.getPlayers().size();
		
		assertEquals("At start, should be " + nbrOfPlayersAtStart, nbrOfPlayersAtStart, game.getPointsNeededToWin());
		game.join("joshua");
		assertEquals("Points changed event should not be called.", 1, cel.pointsNeededToWinChangedEventCounter);
		assertEquals("After Joshua joins, should be " + nbrOfPlayersAtStart, nbrOfPlayersAtStart, game.getPointsNeededToWin());
		game.join("caleb");
		assertEquals("Points changed event should not be called.", 1, cel.pointsNeededToWinChangedEventCounter);
		assertEquals("After Caleb joins, should be " + nbrOfPlayersAtStart, nbrOfPlayersAtStart, game.getPointsNeededToWin());
		game.leave(game.getJudge() == katie ? joe : katie);
		assertEquals("Points changed event should not be called.", 1, cel.pointsNeededToWinChangedEventCounter);
		assertEquals("After player leaves, should be " + nbrOfPlayersAtStart, nbrOfPlayersAtStart, game.getPointsNeededToWin());
	}
	
	@Test
	public void testPointsNeededToWinChanging() {
		GameConfiguration config = new DefaultGameConfiguration() {
			@Override
			public int getPointsNeededToWin(int nbrOfPlayers) {
				return nbrOfPlayers;
			}
			@Override
			public boolean fixPointsNeededToWinAtStart() {
				return false;
			}
			@Override
			public int getMinNbrOfPlayers() {
				return 2;
			}
			@Override
			public boolean playersCanJoinDuringGame() {
				return true;
			}
			@Override
			public boolean autoStartRound() {
				return false;
			}
		};
		CountingEventListener cel = new CountingEventListener();
		Game game = new Game(cel, config, redApples, greenApples);
		game.join("joe");
		assertEquals("Points changed event should be called.", 1, cel.pointsNeededToWinChangedEventCounter);
		Player joe = game.getPlayer("joe");
		game.join("katie");
		assertEquals("Points changed event should be called.", 2, cel.pointsNeededToWinChangedEventCounter);
		Player katie = game.getPlayer("katie");
		game.startRound();
		
		assertEquals("At start, should be " + game.getPlayers().size(), game.getPlayers().size(), game.getPointsNeededToWin());
		game.join("joshua");
		assertEquals("After Joshua joins, should be " + game.getPlayers().size(), game.getPlayers().size(), game.getPointsNeededToWin());
		assertEquals("Points changed event should be called.", 3, cel.pointsNeededToWinChangedEventCounter);
		game.join("caleb");
		assertEquals("After Caleb joins, should be " + game.getPlayers().size(), game.getPlayers().size(), game.getPointsNeededToWin());
		assertEquals("Points changed event should be called.", 4, cel.pointsNeededToWinChangedEventCounter);
		game.leave(game.getJudge() == katie ? joe : katie);
		assertEquals("After player leaves, should be " + game.getPlayers().size(), game.getPlayers().size(), game.getPointsNeededToWin());
		assertEquals("Points changed event should be called.", 5, cel.pointsNeededToWinChangedEventCounter);
	}
	
	@Test
	public void testSuspendAndResumeGame() {
		// TODO
	}
	
	@Test
	public void testJoinAndLeaveBeforeGameStarts() {
		CountingEventListener cel = new CountingEventListener();
		GameConfiguration config = new EZGameConfiguration(false, false, 1, 4, 3, 1, true, false, false);
		Game game = new Game(cel, config, redApples, greenApples);
		
		assertEquals("Game should have no players initially.", 0, game.getPlayers().size());
		assertFalse("0-player game should not be ready to start.", game.isReadyToStart());
		assertFalse("0-player game should not automatically be started.", game.isStarted());
		
		assertEquals("Fred should be able to join new game.", Game.Result.SUCCESS, game.join("fred"));
		assertTrue("Fred should be able to play.", game.getPlayer("fred").isAbleToPlay());
		assertEquals("Fred joining game should increase number of players.", 1, game.getPlayers().size());
		assertEquals("Fred joining game should have triggered join event.", 1, cel.playerJoinedGameEventCounter);
		assertEquals("Fred joining game should not have triggered min event.", 0, cel.minimumNbrOfPlayersJoinedGameEventCounter);
		assertEquals("Fred joining game should not have triggered max event.", 0, cel.maximumNbrOfPlayersJoinedGameEventCounter);
		assertFalse("Game should not be ready to start with only 1 player, Fred.", game.isReadyToStart());
		
		assertEquals("Fred should be able to leave new game.", Game.Result.SUCCESS, game.leave(game.getPlayer("fred")));
		assertEquals("Fred leaving game should decrease the number of players.", 0, game.getPlayers().size());
		assertEquals("Fred leaving game should have triggered leave event.", 1, cel.playerLeftGameEventCounter);
		assertEquals("Fred leaving game should have triggered too-few-players event.", 1, cel.notEnoughPlayersToPlayGameEventCounter);
		assertFalse("Game should not be ready to start with 0 players.", game.isReadyToStart());
		
		assertEquals("Katie should be able to join new game.", Game.Result.SUCCESS, game.join("katie"));
		assertTrue("Katie should be able to play.", game.getPlayer("katie").isAbleToPlay());
		assertEquals("Katie joining game should increase number of players.", 1, game.getPlayers().size());
		assertEquals("Katie joining game should have triggered join event.", 2, cel.playerJoinedGameEventCounter);
		assertEquals("Katie joining game should not have triggered min event.", 0, cel.minimumNbrOfPlayersJoinedGameEventCounter);
		assertEquals("Katie joining game should not have triggered max event.", 0, cel.maximumNbrOfPlayersJoinedGameEventCounter);
		assertFalse("Game should not be ready to start with only 1 player.", game.isReadyToStart());
		
		assertEquals("Caleb should be able to join new game.", Game.Result.SUCCESS, game.join("caleb"));
		assertTrue("Caleb should be able to play.", game.getPlayer("caleb").isAbleToPlay());
		assertEquals("Caleb joining game should increase number of players.", 2, game.getPlayers().size());
		assertEquals("Caleb joining game should have triggered join event.", 3, cel.playerJoinedGameEventCounter);
		assertEquals("Caleb joining game should not have triggered min event.", 0, cel.minimumNbrOfPlayersJoinedGameEventCounter);
		assertEquals("Caleb joining game should not have triggered max event.", 0, cel.maximumNbrOfPlayersJoinedGameEventCounter);
		assertFalse("Game should not be ready to start with only 2 players.", game.isReadyToStart());
		
		assertEquals("Caleb rejoining game should have no effect.", Game.Result.NO_EFFECT, game.join("caleb"));
		assertTrue("Caleb should still be able to play.", game.getPlayer("caleb").isAbleToPlay());
		assertEquals("Caleb rejoining game should have no effect on number of players.", 2, game.getPlayers().size());
		assertEquals("Caleb rejoining game should have triggered join event.", 3, cel.playerJoinedGameEventCounter);
		assertEquals("Caleb rejoining game should not have triggered min event.", 0, cel.minimumNbrOfPlayersJoinedGameEventCounter);
		assertEquals("Caleb rejoining game should not have triggered max event.", 0, cel.maximumNbrOfPlayersJoinedGameEventCounter);
		assertFalse("Game should not be ready to start with only 2 players.", game.isReadyToStart());
		assertEquals("Can't start game without enough players.", Game.Result.ERROR_GAME_UNINITIALIZED, game.startRound());
		
		assertEquals("Joshua should be able to join new game.", Game.Result.SUCCESS, game.join("joshua"));
		assertTrue("Joshua should be able to play.", game.getPlayer("joshua").isAbleToPlay());
		assertEquals("Joshua joining game should increase number of players.", 3, game.getPlayers().size());
		assertEquals("Joshua joining game should have triggered join event.", 4, cel.playerJoinedGameEventCounter);
		assertEquals("Joshua joining game should have triggered min event.", 1, cel.minimumNbrOfPlayersJoinedGameEventCounter);
		assertEquals("Joshua joining game should not have triggered max event.", 0, cel.maximumNbrOfPlayersJoinedGameEventCounter);
		assertTrue("Game should be ready to start with 3 players.", game.isReadyToStart());
		
		assertEquals("Joshua rejoining game should have no effect.", Game.Result.NO_EFFECT, game.join("joshua"));
		assertTrue("Joshua should still be able to play.", game.getPlayer("joshua").isAbleToPlay());
		assertEquals("Joshua rejoining game should have no effect on number of players.", 3, game.getPlayers().size());
		assertEquals("Joshua rejoining game should not have triggered join event.", 4, cel.playerJoinedGameEventCounter);
		assertEquals("Joshua rejoining game should not have triggered min event.", 1, cel.minimumNbrOfPlayersJoinedGameEventCounter);
		assertEquals("Joshua rejoining game should not have triggered max event.", 0, cel.maximumNbrOfPlayersJoinedGameEventCounter);
		assertTrue("Game should be ready to start with 3 players.", game.isReadyToStart());
		
		assertEquals("Joshua should be able to leave new game.", Game.Result.SUCCESS, game.leave(game.getPlayer("joshua")));
		assertEquals("Joshua leaving game should decrease the number of players.", 2, game.getPlayers().size());
		assertEquals("Joshua leaving game should have triggered leave event.", 2, cel.playerLeftGameEventCounter);
		assertEquals("Joshua leaving game should have triggered too-few-players event.", 2, cel.notEnoughPlayersToPlayGameEventCounter);
		assertFalse("Game should not be ready to start with 2 players again.", game.isReadyToStart());
		
		assertEquals("Joshua shouldn't be able to re-leave new game.", Game.Result.ERROR_INVALID_PARAMETER, game.leave(game.getPlayer("joshua")));
		assertEquals("Joshua re-leaving game should not have triggered leave event.", 2, cel.playerLeftGameEventCounter);
		assertEquals("Joshua re-leaving game should not have triggered not-enough-players event.", 2, cel.notEnoughPlayersToPlayGameEventCounter);
		assertFalse("Game should not be ready to start with 2 players yet again.", game.isReadyToStart());
		assertEquals("Can't start game again without enough players.", Game.Result.ERROR_GAME_UNINITIALIZED, game.startRound());
		
		assertEquals("Joshua re-rejoining game should be allowed.", Game.Result.SUCCESS, game.join("joshua"));
		assertTrue("Joshua should be able to play again.", game.getPlayer("joshua").isAbleToPlay());
		assertEquals("Joshua re-rejoining game should increase the number of players.", 3, game.getPlayers().size());
		assertEquals("Joshua re-rejoining game should have triggered join event.", 5, cel.playerJoinedGameEventCounter);
		assertEquals("Joshua re-rejoining game should have triggered min event.", 2, cel.minimumNbrOfPlayersJoinedGameEventCounter);
		assertEquals("Joshua re-rejoining game should not have triggered max event.", 0, cel.maximumNbrOfPlayersJoinedGameEventCounter);
		assertTrue("Game should be ready to start again with 3 players.", game.isReadyToStart());
		
		assertEquals("Katie should be able to leave new game.", Game.Result.SUCCESS, game.leave(game.getPlayer("katie")));
		assertEquals("Katie leaving game should decrease the number of players.", 2, game.getPlayers().size());
		assertEquals("Katie leaving game should have triggered leave event.", 3, cel.playerLeftGameEventCounter);
		assertEquals("Katie leaving game should have triggered not-enough-players event.", 3, cel.notEnoughPlayersToPlayGameEventCounter);
		assertFalse("Game should not be ready to start with 2 players yet again.", game.isReadyToStart());
		
		assertEquals("Katie shouldn't be able to re-leave new game.", Game.Result.ERROR_INVALID_PARAMETER, game.leave(game.getPlayer("katie")));
		assertEquals("Katie re-leaving game should decrease the number of players.", 2, game.getPlayers().size());
		assertEquals("Katie re-leaving game should not have triggered leave event.", 3, cel.playerLeftGameEventCounter);
		assertEquals("Katie re-leaving game should not have triggered not-enough-players event.", 3, cel.notEnoughPlayersToPlayGameEventCounter);
		assertFalse("Game should not be ready to start with 2 players yet once again.", game.isReadyToStart());
		assertEquals("Can't start game yet again without enough players.", Game.Result.ERROR_GAME_UNINITIALIZED, game.startRound());
		
		assertEquals("Katie rejoining game should be allowed.", Game.Result.SUCCESS, game.join("katie"));
		assertTrue("Katie should be able to play again.", game.getPlayer("katie").isAbleToPlay());
		assertEquals("Katie rejoining game should increase the number of players.", 3, game.getPlayers().size());
		assertEquals("Katie rejoining game should have triggered join event.", 6, cel.playerJoinedGameEventCounter);
		assertEquals("Katie rejoining game should have triggered min event.", 3, cel.minimumNbrOfPlayersJoinedGameEventCounter);
		assertEquals("Katie rejoining game should not have triggered max event.", 0, cel.maximumNbrOfPlayersJoinedGameEventCounter);
		assertTrue("Game should be ready to start again with 3 players.", game.isReadyToStart());
		
		assertEquals("Joe joining game should be allowed.", Game.Result.SUCCESS, game.join("joe"));
		assertTrue("Joe should be able to play.", game.getPlayer("joe").isAbleToPlay());
		assertEquals("Joe joining game should increase the number of players.", 4, game.getPlayers().size());
		assertEquals("Joe joining game should have triggered join event.", 7, cel.playerJoinedGameEventCounter);
		assertEquals("Joe joining game should have triggered max event.", 1, cel.maximumNbrOfPlayersJoinedGameEventCounter);
		assertEquals("Joe joining game should not have triggered min event.", 3, cel.minimumNbrOfPlayersJoinedGameEventCounter);
		assertTrue("Game should be ready to start with 4 players.", game.isReadyToStart());
		
		assertEquals("Joe leaving game should be allowed.", Game.Result.SUCCESS, game.leave(game.getPlayer("joe")));
		assertEquals("Joe leaving game should decrease the number of players.", 3, game.getPlayers().size());
		assertEquals("Joe leaving game should have triggered leave event.", 4, cel.playerLeftGameEventCounter);
		assertEquals("Joe leaving game should not have triggered not-enough-players event.", 3, cel.notEnoughPlayersToPlayGameEventCounter);
		assertTrue("Game should be ready to start yet again with 3 players.", game.isReadyToStart());
		
		assertEquals("Joe rejoining game should be allowed.", Game.Result.SUCCESS, game.join("joe"));
		assertTrue("Joe should be able to play again.", game.getPlayer("joe").isAbleToPlay());
		assertEquals("Joe rejoining game should increase the number of players.", 4, game.getPlayers().size());
		assertEquals("Joe rejoining game should have triggered join event.", 8, cel.playerJoinedGameEventCounter);
		assertEquals("Joe rejoining game should have triggered max event.", 2, cel.maximumNbrOfPlayersJoinedGameEventCounter);
		assertEquals("Joe rejoining game should not have triggered min event.", 3, cel.minimumNbrOfPlayersJoinedGameEventCounter);
		assertTrue("Game should be ready to start with 4 players.", game.isReadyToStart());
		
//		assertEquals("Game should start successfully.", Game.Result.SUCCESS, game.startRound());
//		assertEquals("RedApple dealt events should have been fired.", config.getCardsPerHand() * game.getPlayers().size(), cel.redAppleDealtEventCounter);
//		assertEquals("Green apple played event should have been triggered.", 1, cel.greenApplePlayedEventCounter);
//		assertEquals("Katie should have a complete hand.", config.getCardsPerHand(), game.getPlayer("katie").getHand().size());
//		assertEquals("Caleb should have a complete hand.", config.getCardsPerHand(), game.getPlayer("caleb").getHand().size());
//		assertEquals("Joshua should have a complete hand.", config.getCardsPerHand(), game.getPlayer("joshua").getHand().size());
	}
	
	@Test
	public void testJoinAndLeaveDuringGameWhenProhibited() {
		CountingEventListener cel = new CountingEventListener();
		GameConfiguration config = new EZGameConfiguration(true, false, 1, 4, 2, 1, true, false, false);
		Game game = new Game(cel, config, redApples, greenApples);
		assertEquals("Joe can join.", Game.Result.SUCCESS, game.join("joe"));
		assertEquals("Katie can join.", Game.Result.SUCCESS, game.join("katie")); // game will autostart since the min number of players have joined.
		assertEquals("Patrick can't join.", Game.Result.ERROR_PROHIBITED, game.join("patrick"));
		/* prohibited overrides no-effect...no biggie */
		//assertEquals("Joe joining has no effect.", Game.Result.NO_EFFECT, game.join("joe"));
	}
	
	@Test
	public void testJoinAndLeaveDuringGame() {
		// TODO
	}
	
	@Test
	public void testSetJudgeWhenInactive() {
		// TODO
	}

	@Test
	public void testDeckExhaustedEvents() {
		// TODO
	}
	
	@Test
	public void testMaxPlayersJoinedEvent() {
		// TODO
	}
}
