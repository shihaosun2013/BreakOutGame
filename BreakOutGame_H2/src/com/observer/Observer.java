package com.observer;

public interface Observer {
	/**
	 * This method should be used to notify the observers.
	 * 
	 * @param timeStep
	 */
	void update(int timeStep);
}
