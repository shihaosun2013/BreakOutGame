package com.command.impl;

import java.io.Serializable;

import com.board.elements.Clock;
import com.command.GameCommand;
import com.util.GameConstants;



/**
 * This class controls the clock that is displayed on the game screen.
 * 
 * @author shihao
 *
 */
public class ClockTick implements GameCommand, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7700377574161624513L;
	private Clock clock;

	public ClockTick(Clock clock) {
		super();
		this.clock = clock;
	}

	@Override
	public void executeCommand() {
		clock.update(GameConstants.TIME_STEP);
	}

	@Override
	public void undo() {
		clock.update(-GameConstants.TIME_STEP);
	}
}
