package com.board.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import com.observer.Observer;
import com.util.GameConstants;


public class Ball extends Shape implements Observer, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1939877456670815266L;
	public final int INITIAL_X_DIR = 1, INITIAL_Y_DIR = -1;
	private boolean onScreen;
	private int xDir = INITIAL_X_DIR, yDir = INITIAL_Y_DIR;

	/*
	 * Constructor
	 */
	public Ball(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);
		setOnScreen(true);
	}

	public Ball(Ball ball) {
		super(ball);
		xDir = ball.xDir;
		yDir = ball.yDir;
	}

	public void set(Ball ball) {
		super.set(ball);
		xDir = ball.xDir;
		yDir = ball.yDir;
	}

	/*
	 * Draw the ball
	 * (non-Javadoc)
	 * @see com.brickout.shape.Shape#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, width, height);
	}

	/**
	 * Resets the ball to original position at center of screen
	 */
	public void reset() {
		x = GameConstants.BALL_X_START;
		y = GameConstants.BALL_Y_START;
		xDir = INITIAL_X_DIR;
		yDir = INITIAL_Y_DIR;
	}

	public void setXDir(int xDir) {
		this.xDir = xDir;
	}

	public void setYDir(int yDir) {
		this.yDir = yDir;
	}

	public void setOnScreen(boolean onScreen) {
		this.onScreen = onScreen;
	}

	public int getXDir() {
		return xDir;
	}

	public int getYDir() {
		return yDir;
	}

	public boolean isOnScreen() {
		return onScreen;
	}

	@Override
	public void update(int timeStep) {
		if (timeStep > 0) {
			x += xDir;
			y += yDir;
		} else if (timeStep < 0) {
			x -= xDir;
			y -= yDir;
		}
	}

	/**
	 * Reverse the direction of Ball
	 */
	public void reverseMove() {
		reverseXDir();
		reverseYDir();
	}

	/**
	 * Reverse the X-direction of Ball
	 */
	public void reverseXDir() {
		setXDir(-getXDir());
	}

	/**
	 * Reverse the Y-direction of Ball
	 */
	public void reverseYDir() {
		setYDir(-getYDir());
	}
}