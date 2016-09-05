package com.board.elements;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import com.observer.Observer;
import com.observer.Subject;
import com.util.GameConstants;


public class Clock extends Shape implements Observer, Subject, Serializable, Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6839381889557113008L;
	private static final int MILLISEC_TO_SEC = 1000;
	private static final Logger LOGGER = Logger.getLogger(Clock.class.getName());

	private int time;
	private List<Observer> observers;

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

	public List<Observer> getObservers() {
		return observers;
	}

	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}

	@Override
	public void register(Observer observer) {
		// TODO Auto-generated method stub
		observers.add(observer);
	}

	@Override
	public void unregister(Observer observer) {
		// TODO Auto-generated method stub
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(int timeStep) {
		// TODO Auto-generated method stub
		for (Observer observer : observers) {
			observer.update(timeStep);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {

//			executeReplay();
//
//			if (gameParameter.isPaused() || gameParameter.getMode().equals(Mode.UNDO) && clock.getTime() <= events.peekLast().getTime()) {
//				if (gameParameter.getMode().equals(Mode.UNDO)) {
//					checkUndo();
//				}
//				synchronized (game) {
//					try {
//						game.wait();
//					} catch (InterruptedException e) {
//						LOGGER.warning(e.getMessage() + e);
//					}
//				}
//			} else {
//
//				int x1 = ball.getX();
//				int y1 = ball.getY();
//
//				checkPaddleCollision(x1, y1);
//				checkWallCollision(x1, y1);
//				checkBrickCollision(x1, y1);
//
//				livesChecker.check();
//				playerOutChecker.setY(y1);
//				playerOutChecker.check();
//
//				if (!gameParameter.getMode().equals(Mode.UNDO)) {
//					commandBroker.executeCommand();
//				} else {
//					commandBroker.undo();
//				}
//				repaint();

				try {
					Thread.sleep(GameConstants.TIME_STEP);
				} catch (InterruptedException ie) {
					LOGGER.warning(ie.getMessage() + ie);
				}
			}
		}
	
}
