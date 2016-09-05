package com.command.impl;

import java.io.Serializable;

import com.board.Board;
import com.command.GameCommand;



/**
 * This class specifies resume logic for the game.
 * 
 * @author shihao
 *
 */
public class Resume implements GameCommand, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3724650002278526964L;
	private Board board;

	public Resume(Board board) {
		super();
		this.board = board;
	}

	@Override
	public void executeCommand() {
		board.getGameParameter().setPaused(false);
		synchronized (board.getGame()) {
			board.getGame().notifyAll();
		}
	}

	@Override
	public void undo() {
	}
}
