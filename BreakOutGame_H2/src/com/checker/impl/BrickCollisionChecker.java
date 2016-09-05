package com.checker.impl;

import java.io.Serializable;

import com.board.Board;
import com.checker.EventChecker;
import com.util.GameConstants;
import com.util.GameEvent;
import com.util.GameMode;
import com.util.GameParameter;



/**
 * This class checks for ball and brick collision.
 * 
 * @author shihao
 *
 */
public class BrickCollisionChecker implements EventChecker, Serializable {

	/**
	 * 
	 */
	
	private int x;
	private int y;
	private Board board;

	public BrickCollisionChecker(Board board) {
		super();
		this.board = board;
	}

	@Override
	public void check() {

		GameParameter gameParameter = board.getGameParameter();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 5; j++) {
				if (board.getBricks()[i][j].isDestroyed()) {
					continue;
				}
				int collisionIdentifier = board.getBricks()[i][j].checkCollision(x, y);
				if (collisionIdentifier == 1) {
					board.getBall().reverseYDir();
				} else if (collisionIdentifier == 2) {
					board.getBall().reverseXDir();
				} else if (collisionIdentifier == 3) {
					board.getBall().reverseXDir();
					board.getBall().reverseYDir();
				} else {
					continue;
				}

				if (!gameParameter.getMode().equals(GameMode.UNDO)) {
					board.getBricks()[i][j].setDestroyed(true);
					gameParameter.decrementBricks(1);
					gameParameter.incrementScore(50);
					if (gameParameter.getMode().equals(GameMode.PLAY)) {
						board.getEvents().add(new GameEvent(board.getClock().getTime(), GameConstants.BRICK_COLLISION));
						board.getEvents().peekLast().setEventObject(board.getBricks()[i][j]);
					}
				} else {
					gameParameter.incrementBricks(1);
					gameParameter.decrementScore(50);
				}
			}
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
