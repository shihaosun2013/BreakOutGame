package com.board.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import com.util.GameConstants;

public class Paddle extends Shape {

	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param color
	 */
	public Paddle(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);
	}

	public Paddle(Paddle paddle) {
		super(paddle);
	}

	/**
	 * Draws the paddle
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}

	/**
	 * Places the paddle back in starting position at center of screen
	 */
	public void reset() {
		x = GameConstants.PADDLE_X_START;
		y = GameConstants.PADDLE_Y_START;
	}

	/**
	 * Checks if the ball hit the paddle
	 * 
	 * @param ballX
	 * @param ballY
	 * @return
	 */
	public int hitPaddle(int ballX, int ballY) {
		int collisionIndicator = 0;
		if ((ballX >= x) && ((ballY + GameConstants.BALL_HEIGHT >= y))) {
			if ((ballX <= x + (width / 2))) {
				collisionIndicator = 1;
			} else {
				collisionIndicator = 2;
			}
		}
		return collisionIndicator;
	}

	/**
	 * Moves the paddle as per user input.
	 * 
	 * @param key
	 * @param windowWidth
	 * @return
	 */
	public Integer move(int key, int windowWidth) {
		Integer distance = 0;
		if (key == KeyEvent.VK_LEFT) {
			if (getX() - 50 >= 0) {
				setX(getX() - 50);
				distance = -50;
			} else {
				distance = -getX();
				setX(0);
			}
		} else if (key == KeyEvent.VK_RIGHT) {
			if (getX() + width + 50 <= windowWidth) {
				setX(getX() + 50);
				distance = 50;
			} else {
				distance = windowWidth - width - getX();
				setX(windowWidth - width);
			}
		}
		return distance;
	}
}