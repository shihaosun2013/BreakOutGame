package com.board.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import com.util.GameConstants;


public class Brick extends Shape implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6222283223454255947L;
	private boolean destroyed;

	public Brick() {
	}

	/**
	 * Constructor
	 */
	public Brick(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);
		setDestroyed(false);
	}

	public Brick(Brick brick) {
		super(brick);
		this.setDestroyed(brick.isDestroyed());
	}

	/**
	 * Draws a brick
	 */
	@Override
	public void draw(Graphics g) {
		if (!destroyed) {
			g.setColor(color);
			g.fillRect(x, y, width, height);
		}
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	/**
	 * Detect if the brick has been hit on its bottom, top, left, or right sides
	 * 
	 * @param ballX
	 * @param ballY
	 * @return
	 */
	public int checkCollision(int ballX, int ballY) {
		int collisionIdentifier = 0;
		// checkBottom
		if ((ballX + GameConstants.BALL_WIDTH >= x) && (ballX <= x + width) && (ballY == y + height)) {
			collisionIdentifier = 1;
		}
		// checkTop
		if ((ballX + GameConstants.BALL_WIDTH >= x) && (ballX <= x + width) && (ballY + 10 == y)) {
			collisionIdentifier = 1;
		}
		// checkLeft
		if ((ballY + GameConstants.BALL_HEIGHT >= y) && (ballY <= y + height) && (ballX + 10 == x)) {
			collisionIdentifier = 2;
		}
		// checkRight
		if ((ballY + GameConstants.BALL_HEIGHT >= y) && (ballY <= y + height) && (ballX == x + width)) {
			collisionIdentifier = 2;
		}
		if (collisionIdentifier == 0) {
			if (((ballX + 15 == x) && (ballY + 15 == y)) || ((ballX + 15 == x) && (ballY == y + height))
					|| ((ballX == x + width) && (ballY == y + height)) || ((ballX == x + width) && (ballY + 15 == y))) {
				collisionIdentifier = 3;
			}
		}
		return collisionIdentifier;
	}
}