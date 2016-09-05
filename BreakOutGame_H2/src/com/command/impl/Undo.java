package com.command.impl;

import java.io.Serializable;

import com.board.Board;
import com.board.elements.Ball;
import com.command.GameCommand;
import com.util.GameConstants;
import com.util.GameEvent;
import com.util.GameMode;
import com.util.GameParameter;


/**
 * This class specifies undo logic.
 * 
 * @author shihao
 *
 */
public class Undo implements GameCommand, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -368961402010147215L;
	private Board board;

	public Undo(Board board) {
		super();
		this.board = board;
	}

	@Override
	public void executeCommand() {

		GameParameter gameParameter = board.getGameParameter();

		gameParameter.setMode(GameMode.UNDO);
		board.getPauseCommand().executeCommand();

		GameEvent event = board.getEvents().peekLast();
		if (event.getAction() == GameConstants.GAME_WIN || event.getAction() == GameConstants.GAME_LOST) {
			if (event.getAction() == GameConstants.GAME_WIN) {
				gameParameter.setScore(gameParameter.getScore() - 100);
			} else {
				gameParameter.setScore(gameParameter.getScore() + 50);
			}

			board.getBall().set((Ball) event.getEventObject());

			board.getEvents().pollLast();
			event = board.getEvents().peekLast();
		}
		board.getResumeCommand().executeCommand();
	}

	@Override
	public void undo() {
	}
}
