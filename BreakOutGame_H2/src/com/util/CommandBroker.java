package com.util;

import java.io.Serializable;
import java.util.List;

import com.command.GameCommand;



/**
 * This class acts as the medium to execute all the commands.
 * 
 * @author shihao
 *
 */
public class CommandBroker implements GameCommand, Serializable {

	/**
	 * 
	 */
	
	private List<GameCommand> gameCommands;

	public CommandBroker(List<GameCommand> gameCommands) {
		super();
		this.gameCommands = gameCommands;
	}

	@Override
	public void executeCommand() {

		for (GameCommand gameCommand : gameCommands) {
			gameCommand.executeCommand();
		}
	}

	@Override
	public void undo() {

		for (GameCommand gameCommand : gameCommands) {
			gameCommand.undo();
		}
	}
}
