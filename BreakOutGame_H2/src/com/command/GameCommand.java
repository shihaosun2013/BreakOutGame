package com.command;

public interface GameCommand {

	/**
	 * This method should be implemented and used to specify command specific
	 * code.
	 */
	void executeCommand();

	/**
	 * This method should be implemented and used to specify undo logic for the
	 * respective command.
	 */
	void undo();
}
