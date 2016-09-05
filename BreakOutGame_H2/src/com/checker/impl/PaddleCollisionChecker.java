package com.checker.impl;

import java.io.Serializable;

import com.board.Board;
import com.checker.EventChecker;
import com.util.GameMode;



/**
 * This class checks for ball and paddle collision.
 * 
 * @author shihao
 *
 */
public class PaddleCollisionChecker implements EventChecker, Serializable {

	/**
	 * 
	 */
	
	private int x;
	private int y;
	private Board board;

	public PaddleCollisionChecker(Board board) {
		super();
		this.board = board;
	}

	@Override
	public void check() {
		int collisionIndicator = board.getPaddle().hitPaddle(x, y);
		switch (collisionIndicator) {
		case 1:
			board.getBall().setYDir(board.getBall().INITIAL_Y_DIR);
			board.getBall().setXDir(-1);
			break;
		case 2:
			board.getBall().setYDir(board.getBall().INITIAL_Y_DIR);
			board.getBall().setXDir(1);
			break;
		default:
			break;
		}
		if (!(collisionIndicator == 0) && board.getGameParameter().getMode().equals(GameMode.UNDO)) {
			board.getBall().setYDir(-board.getBall().INITIAL_Y_DIR);
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
