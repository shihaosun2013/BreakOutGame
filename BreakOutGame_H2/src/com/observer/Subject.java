package com.observer;

public interface Subject {

	/**
	 * Use this method to register new observers.
	 * 
	 * @param o
	 */
	public void register(Observer o);

	/**
	 * Use this method to remove observers from subscriber list.
	 * 
	 * @param o
	 */
	public void unregister(Observer o);

	/**
	 * Use this method to notify observers.
	 * 
	 * @param timeStep
	 */
	public void notifyObservers(int timeStep);
}
