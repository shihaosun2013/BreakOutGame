package com.command.impl;

import java.io.Serializable;
import java.util.ArrayDeque;

import com.board.Board;
import com.command.GameCommand;
import com.util.GameEvent;
import com.util.GameMode;



/**
 * This class specifies replay logic for the game.
 * 
 * @author shihao
 *
 */
public class Replay implements GameCommand, Serializable {

	/**
	 * 
	 */
	
	private Board board;

	public Replay(Board board) {
		super();
		this.board = board;
	}

	@Override
	public void executeCommand() {

		ArrayDeque<GameEvent> tmp = board.getEvents();
		board.getResetCommand().executeCommand();
		board.setEvents(tmp);
		board.getGameParameter().setMode(GameMode.REPLAY);
		board.getResumeCommand().executeCommand();
	}

	@Override
	public void undo() {
	}
}
