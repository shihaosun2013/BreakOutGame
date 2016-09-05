package com.checker.impl;

import java.io.Serializable;

import com.board.Board;
import com.board.elements.Ball;
import com.checker.EventChecker;
import com.util.GameConstants;
import com.util.GameEvent;
import com.util.GameMode;
import com.util.GameParameter;


/**
 * This class checks if the ball is out of game screen.
 * 
 * @author shihao
 *
 */
public class PlayerOutChecker implements EventChecker, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 177133003984516975L;
	private Board board;
	private int y;

	public PlayerOutChecker(Board board) {
		super();
		this.board = board;
	}

	@Override
	public void check() {
		GameParameter gameParameter = board.getGameParameter();
		if (y > GameConstants.PADDLE_Y_START + GameConstants.BALL_HEIGHT && !gameParameter.getMode().equals(GameMode.UNDO)) {
			gameParameter.decrementScore(50);
			if (gameParameter.getMode().equals(GameMode.PLAY)) {
				board.getEvents().add(new GameEvent(board.getClock().getTime(), GameConstants.GAME_LOST));
			}
			board.getEvents().peekLast().setEventObject(new Ball(board.getBall()));
			board.getBall().reset();
			if (gameParameter.getMode().equals(GameMode.PLAY)) {
				board.getEvents().add(new GameEvent(board.getClock().getTime(), GameConstants.GAME_BEGIN));
			}
			board.repaint();
		}
	}

	@Override
	public void setX(int x) {
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}
}
