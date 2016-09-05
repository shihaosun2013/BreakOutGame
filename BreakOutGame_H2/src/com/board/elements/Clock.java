package com.board.elements;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;

import com.observer.Observer;


public class Clock extends Shape implements Observer, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6839381889557113008L;
	private static final int MILLISEC_TO_SEC = 1000;
	private int time;

	public Clock(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);
		time = 0;
	}

	public Clock(Clock clock) {
		super(clock);
		time = clock.time;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		Font currentFont = g.getFont();
		g.setFont(new Font("Tahoma", Font.BOLD, 30));
		int mm = time / MILLISEC_TO_SEC / 60 % 60;
		int ss = time / MILLISEC_TO_SEC % 60;
		g.drawString(String.format("%02d:%02d", mm, ss), 300, y + 180);
		g.setFont(currentFont);
	}

	@Override
	public void update(int timeStep) {
		time = (time + timeStep) % (3600 * MILLISEC_TO_SEC);
	}

	public int getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}
	
}
