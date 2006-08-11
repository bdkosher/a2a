/**
 * 
 */
package com.ourbabywolf.apples2apples;

/**
 * Standard game configuration for Apples2Apples based on the official
 * rules, except that the minimum number of players is 3 instead of 4.
 * 
 * @author joe@ourbabywolf.com
 */
public class DefaultGameConfiguration implements GameConfiguration {

	/**
	 * Returns 7.
	 * @see com.ourbabywolf.apples2apples.GameConfiguration#getCardsPerHand()
	 */
	public int getCardsPerHand() {
		return 7;
	}

	/**
	 * Returns 12
	 * @see com.ourbabywolf.apples2apples.GameConfiguration#getMaxNbrOfPlayers()
	 */
	public int getMaxNbrOfPlayers() {
		return 12;
	}

	/**
	 * Returns 3, one judge and two players.
	 * @see com.ourbabywolf.apples2apples.GameConfiguration#getMinNbrOfPlayers()
	 */
	public int getMinNbrOfPlayers() {
		return 3;
	}

	/**
	 * For 4- players, 8 green apples win.
	 * For 5 players, 7 green apples win.
	 * For 6 players, 6 green apples win.
	 * For 7 players, 5 green apples win.
	 * For 8+ players, 4 green apples win.
	 * @see com.ourbabywolf.apples2apples.GameConfiguration#getPointsNeededToWin(int)
	 * @link http://www.com-www.com/applestoapples/applestoapples-rules-official-partyset.html
	 */
	public int getPointsNeededToWin(int nbrOfPlayers) {
		if (nbrOfPlayers < 4) {
			return 8;
		} else if (nbrOfPlayers > 8) {
			return 4;
		} else {
			return 12 - nbrOfPlayers;
		}
	}

	/**
	 * Returns true.
	 * @see com.ourbabywolf.apples2apples.GameConfiguration#isJudgeRotated()
	 */
	public boolean isJudgeRotated() {
		return true;
	}

	/**
	 * Returns true.
	 * @see com.ourbabywolf.apples2apples.GameConfiguration#fixPointsNeededToWinAtStart()
	 */
	public boolean fixPointsNeededToWinAtStart() {
		return true;
	}

	/**
	 * Returns true.
	 * @see com.ourbabywolf.apples2apples.GameConfiguration#playersCanJoinDuringGame()
	 */
	public boolean playersCanJoinDuringGame() {
		return true;
	}

	/**
	 * Returns true.
	 * @see com.ourbabywolf.apples2apples.GameConfiguration#playersCanLeaveDuringGame()
	 */
	public boolean playersCanLeaveDuringGame() {
		return true;
	}

	/**
	 * Returns true.
	 * @see com.ourbabywolf.apples2apples.GameConfiguration#autoStartRound()
	 */
	public boolean autoStartRound() {
		return true;
	}

}
