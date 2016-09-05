package com.util;

import java.io.Serializable;

/**
 * This class has all the variables that are used to display game status.
 * 
 * @author shihao
 *
 */
public class GameParameter {


	// Initial Values for some important variables
	private int score;
	private int bricksLeft;

	// Paused state
	private volatile boolean isPaused;

	private GameMode mode;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getBricksLeft() {
		return bricksLeft;
	}

	public void setBricksLeft(int bricksLeft) {
		this.bricksLeft = bricksLeft;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public GameMode getMode() {
		return mode;
	}

	public void setMode(GameMode mode) {
		this.mode = mode;
	}

	public void resetScore() {
		score = 0;
	}

	public void resetBricksLeft() {

		bricksLeft = GameConstants.MAX_BRICKS;
	}

	public void incrementScore(int amt) {

		score += amt;
	}

	public void decrementScore(int amt) {

		score -= amt;
	}

	public void decrementBricks(int amt) {

		bricksLeft -= amt;
	}

	public void incrementBricks(int amt) {

		bricksLeft += amt;
	}

}
