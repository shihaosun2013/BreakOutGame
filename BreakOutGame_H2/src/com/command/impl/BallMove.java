package com.command.impl;

import java.io.Serializable;

import com.board.elements.Ball;
import com.command.GameCommand;
import com.util.GameConstants;



/**
 * This command moves the ball as specified.
 * 
 * @author shihao
 *
 */
public class BallMove implements GameCommand, Serializable {

	/**
	 * 
	 */
	
	private Ball ball;

	public BallMove(Ball ball) {
		super();
		this.ball = ball;
	}

	@Override
	public void executeCommand() {
		ball.update(GameConstants.TIME_STEP);
	}

	@Override
	public void undo() {
		ball.update(-GameConstants.TIME_STEP);
	}
}
