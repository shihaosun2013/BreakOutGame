package com.util;

import java.io.Serializable;

/**
 * This class is used to store states of the game which are then used for undo
 * and replay functionality.
 * 
 * @author shihao
 *
 */
public class GameEvent{

	/**
	 * 
	 */
	private int time;
	private int action;
	private Object eventObject;

	public Object getEventObject() {
		return eventObject;
	}

	public void setEventObject(Object eventObject) {
		this.eventObject = eventObject;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public GameEvent(int time, int action) {
		this.time = time;
		this.action = action;
	}
}
