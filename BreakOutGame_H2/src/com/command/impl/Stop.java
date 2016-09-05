package com.command.impl;

import java.io.Serializable;

import com.board.Board;
import com.command.GameCommand;

/**
 * This class specifies stop logic for the game.
 * 
 * @author shihao
 *
 */
public class Stop implements GameCommand, Serializable {

	/**
	 * 
	 */
	
	private Board board;

	public Stop(Board board) {
		super();
		this.board = board;
	}

	@Override
	public void executeCommand() {
		board.getResetCommand().executeCommand();
		board.repaint();
	}

	@Override
	public void undo() {
	}
}
