package com.checker;

public interface EventChecker {

	/**
	 * This method should be used to implement component specific collision
	 * code.
	 */
	public void check();

	/**
	 * Set Ball's X coordinate using this method
	 * 
	 * @param x
	 */
	public void setX(int x);

	/**
	 * Set Ball's Y coordinate using this method
	 * 
	 * @param x
	 */
	public void setY(int y);
}
