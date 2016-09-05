package com.command.impl;

import java.io.Serializable;

import com.board.Board;
import com.command.GameCommand;

/**
 * This class specifies Pause logic for the game.
 * 
 * @author shihao
 *
 */
public class Pause implements GameCommand, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4718086331882020264L;
	private Board board;

	public Pause(Board board) {
		super();
		this.board = board;
	}

	@Override
	public void executeCommand() {
		board.getGameParameter().setPaused(true);
	}

	@Override
	public void undo() {
	}
}
