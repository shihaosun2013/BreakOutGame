package com.util;

/**
 * This interface holds all the game constants
 * 
 * @author shihao
 *
 */
public final class GameConstants {
	// Window Size
	public static final int WINDOW_WIDTH = 650;
	// Increased window height to make room for buttons
	public static final int WINDOW_HEIGHT = 650;

	// Ball
	public static final int BALL_WIDTH = 10;
	public static final int BALL_HEIGHT = 10;
	public static final int BALL_RIGHT_BOUND = 490;
	public static final int BALL_X_START = 245;
	public static final int BALL_Y_START = 245;

	// Paddle
	public static final int PADDLE_WIDTH = 70;
	public static final int PADDLE_HEIGHT = 10;
	public static final int PADDLE_RIGHT_BOUND = 430;
	public static final int PADDLE_X_START = 225;
	public static final int PADDLE_Y_START = 450;
	public static final int PADDLE_MIN = 35;
	public static final int PADDLE_MAX = 140;

	// Bricks
	public static final int BRICK_WIDTH = 50;
	public static final int BRICK_HEIGHT = 25;
	public static final int MAX_BRICKS = 50;
	public static final int NO_BRICKS = 0;
	public static final int BRICK_ROWS = 5;
	public static final int BRICK_COLUMNS = 10;

	// Directions
	public static final String X_DIRECTION = "X";
	public static final String Y_DIRECTION = "Y";

	public static final int CLOCK_LOCATION_X = 10;
	public static final int CLOCK_LOCATION_Y = WINDOW_HEIGHT / 2;
	public static final int TIME_STEP = 5;

	// code of action
	public static final int GAME_END = -1;
	public static final int BRICK_COLLISION = -2;
	public static final int GAME_BEGIN = -3;
	public static final int GAME_LOST = -4;
	public static final int GAME_WIN = -5;

	
}