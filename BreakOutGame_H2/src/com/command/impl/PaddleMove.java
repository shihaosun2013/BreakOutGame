package com.command.impl;

import com.board.elements.Paddle;
import com.command.GameCommand;

/**
 * This class can be used to implement paddle specific movements and actions.
 * 
 * @author shihao
 *
 */
public class PaddleMove implements GameCommand {

	private Paddle paddle;

	public PaddleMove(Paddle paddle) {
		super();
		this.paddle = paddle;
	}

	@Override
	public void executeCommand() {
	}

	@Override
	public void undo() {
	}
}
