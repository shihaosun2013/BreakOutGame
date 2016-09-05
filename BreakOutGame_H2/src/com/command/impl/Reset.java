package com.command.impl;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

import com.board.Board;
import com.board.elements.Ball;
import com.board.elements.Brick;
import com.board.elements.Clock;
import com.board.elements.Paddle;
import com.command.GameCommand;
import com.observer.Observer;
import com.util.CommandBroker;
import com.util.CommonStructureUtility;
import com.util.GameConstants;
import com.util.GameEvent;
import com.util.GameMode;

/**
 * This class specifies reset logic for the game.
 * 
 * @author shihao
 *
 */
public class Reset implements GameCommand, Serializable {

	/**
	 * 
	 */
	
	private Board board;

	public Reset(Board board) {
		super();
		this.board = board;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	@Override
	public void executeCommand() {

		board.getGameParameter().resetScore();
		board.getGameParameter().resetBricksLeft();

		board.setBricks(new Brick[GameConstants.BRICK_COLUMNS][GameConstants.BRICK_ROWS]);
		CommonStructureUtility.makeBricks(board.getBricks());
		board.setPaddle(new Paddle(GameConstants.PADDLE_X_START, GameConstants.PADDLE_Y_START,
				GameConstants.PADDLE_WIDTH, GameConstants.PADDLE_HEIGHT, Color.BLACK));
		board.setBall(new Ball(GameConstants.BALL_X_START, GameConstants.BALL_Y_START, GameConstants.BALL_WIDTH,
				GameConstants.BALL_HEIGHT, Color.BLACK));

		board.setClock(new Clock(GameConstants.CLOCK_LOCATION_X, board.getHeight() / 2, GameConstants.BALL_WIDTH,
				GameConstants.BALL_HEIGHT, Color.red));

		board.setBallMoveCommand(new BallMove(board.getBall()));
		board.setClockTickCommand(new ClockTick(board.getClock()));
		board.setCommandBroker(new CommandBroker(Arrays.asList(board.getBallMoveCommand(), board.getClockTickCommand())));

		board.setEvents(new ArrayDeque<GameEvent>());

		board.getEvents().add(new GameEvent(board.getClock().getTime(), GameConstants.GAME_BEGIN));

		board.getBall().reset();

		board.getGameParameter().setPaused(true);
		board.getGameParameter().setMode(GameMode.PLAY);

		board.setObservers(new ArrayList<Observer>());
		board.register(board.getBall());
		board.register(board.getClock());

		board.getStartBtn().setText("START");

		board.getUndoBtn().setEnabled(false);
		board.getReplayBtn().setEnabled(false);
		board.getStopBtn().setEnabled(false);
	}

	@Override
	public void undo() {
	}
}
