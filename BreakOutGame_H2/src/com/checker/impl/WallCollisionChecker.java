package com.checker.impl;

import java.io.Serializable;

import com.board.Board;
import com.checker.EventChecker;

/**
 * This class detects ball and wall collision.
 * 
 * @author shihao
 *
 */
public class WallCollisionChecker implements EventChecker, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2480816459631547900L;
	private int x;
	private int y;
	private Board board;

	public WallCollisionChecker() {
	}

	public WallCollisionChecker(Board board) {
		super();

		this.board = board;
	}

	@Override
	public void check() {

		if (x <= 0 || x >= board.getWidth() - board.getBall().getWidth()) {
			board.getBall().reverseXDir();
		}

		if (y <= 0) {
			board.getBall().reverseYDir();
		}
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}
}
