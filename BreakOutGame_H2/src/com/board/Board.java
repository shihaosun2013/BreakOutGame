package com.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.board.elements.Ball;
import com.board.elements.Brick;
import com.board.elements.Clock;
import com.board.elements.Paddle;
import com.checker.EventChecker;
import com.checker.impl.BrickCollisionChecker;
import com.checker.impl.PaddleCollisionChecker;
import com.checker.impl.PlayerOutChecker;
import com.checker.impl.WallCollisionChecker;
import com.command.GameCommand;
import com.command.impl.Pause;
import com.command.impl.Replay;
import com.command.impl.Reset;
import com.command.impl.Resume;
import com.command.impl.Stop;
import com.command.impl.Undo;
import com.observer.Observer;
import com.observer.Subject;
import com.util.BoardListener;
import com.util.GameConstants;
import com.util.GameEvent;
import com.util.GameMode;
import com.util.GameParameter;

//Class definition
public class Board extends JPanel implements Runnable, Subject, Observer, Serializable {

	/**
	 * 
	 */
	
	// The game
	transient private Thread game;
	private static final Logger LOGGER = Logger.getLogger(Board.class.getName());
	// Items on-screen
	private Paddle paddle;
	private Ball ball;
	private Clock clock;
	private Brick[][] bricks;
	private List<Observer> observers;
	private ArrayDeque<GameEvent> events;
	private GameParameter gameParameter;

	private GameCommand ballMoveCommand;
	private GameCommand commandBroker;
	private GameCommand clockTickCommand;
	private GameCommand pauseCommand;
	private GameCommand replayCommand;
	private GameCommand resetCommand;
	private GameCommand resumeCommand;
	private GameCommand stopCommand;
	private GameCommand undoCommand;

	private EventChecker brickCollisionChecker;
	private EventChecker wallCollisionChecker;
	private EventChecker paddleCollisionChecker;
	private EventChecker playerOutChecker;

	JPanel buttonPanel = new JPanel();
	JPanel buttonContainer = new JPanel();
	private JButton startBtn, stopBtn, undoBtn, replayBtn;

	// Constructor
	public Board(int width, int height) {
		super.setSize(width, height);
		addKeyListener(new BoardListener(this));
		super.setLayout(null);

		// Buttons
		startBtn = styleButton("START");
		stopBtn = styleButton("STOP");
		undoBtn = styleButton("UNDO");
		replayBtn = styleButton("REPLAY");

		startBtn.addActionListener(new BoardListener(this));
		stopBtn.addActionListener(new BoardListener(this));
		undoBtn.addActionListener(new BoardListener(this));
		replayBtn.addActionListener(new BoardListener(this));

		buttonPanel.add(startBtn);
		buttonPanel.add(stopBtn);
		buttonPanel.add(undoBtn);
		buttonPanel.add(replayBtn);

		buttonContainer.setLocation(0, GameConstants.CLOCK_LOCATION_Y + 200);
		buttonContainer.setSize(this.getWidth(), 600);
		buttonContainer.setBackground(new Color(62, 151, 179));

		// adds the Button Panel to the board
		buttonContainer.add(buttonPanel);
		this.add(buttonContainer);

		gameParameter = new GameParameter();
		pauseCommand = new Pause(this);
		resumeCommand = new Resume(this);
		undoCommand = new Undo(this);
		replayCommand = new Replay(this);
		resetCommand = new Reset(this);
		stopCommand = new Stop(this);

		wallCollisionChecker = new WallCollisionChecker(this);
		paddleCollisionChecker = new PaddleCollisionChecker(this);
		brickCollisionChecker = new BrickCollisionChecker(this);
		playerOutChecker = new PlayerOutChecker(this);

		resetCommand.executeCommand();

		setFocusable(true);
		game = new Thread(this);
		game.start();

	}

	@Override
	public void run() {
		while (true) {

			executeReplay();

			if (gameParameter.isPaused() || gameParameter.getMode().equals(GameMode.UNDO)
					&& clock.getTime() <= events.peekLast().getTime()) {
				if (gameParameter.getMode().equals(GameMode.UNDO)) {
					checkUndo();
				}
				synchronized (game) {
					try {
						game.wait();
					} catch (InterruptedException e) {
						LOGGER.warning(e.getMessage() + e);
					}
				}
			} else {

				int x1 = ball.getX();
				int y1 = ball.getY();

				checkPaddleCollision(x1, y1);
				checkWallCollision(x1, y1);
				checkBrickCollision(x1, y1);

				playerOutChecker.setY(y1);
				playerOutChecker.check();

				if (!gameParameter.getMode().equals(GameMode.UNDO)) {
					commandBroker.executeCommand();
				} else {
					commandBroker.undo();
				}
				repaint();

				try {
					Thread.sleep(GameConstants.TIME_STEP);
				} catch (InterruptedException ie) {
					LOGGER.warning(ie.getMessage() + ie);
				}
			}
		}
	}

	/**
	 * This method performs replay functionality.
	 */
	private void executeReplay() {
		// In replay mode, re-apply stored events
		while (gameParameter.getMode().equals(GameMode.REPLAY) && clock.getTime() == events.peekFirst().getTime()) {
			GameEvent event = events.pollFirst();
			if (event.getAction() == KeyEvent.VK_LEFT || event.getAction() == KeyEvent.VK_RIGHT) {
				paddle.move(event.getAction(), getWidth());
			} else if (event.getAction() == GameConstants.GAME_END) {
				stopCommand.executeCommand();
			}
		}
	}

	/**
	 * This method rolls back the last action popped up from ArrayDeque.
	 */
	private void checkUndo() {
		GameEvent event = events.pollLast();
		if (event.getAction() == KeyEvent.VK_RIGHT || event.getAction() == KeyEvent.VK_LEFT) {
			paddle.setX(paddle.getX() - (Integer) event.getEventObject());
		} else if (event.getAction() == GameConstants.BRICK_COLLISION) {
			((Brick) event.getEventObject()).setDestroyed(false);
			brickCollisionChecker.setX(ball.getX());
			brickCollisionChecker.setY(ball.getY());
			brickCollisionChecker.check();
			commandBroker.undo();
		}

		repaint();
		gameParameter.setMode(GameMode.PLAY);
		pauseCommand.executeCommand();
	}

	/**
	 * This method checks for paddle collision
	 * 
	 * @param x1
	 * @param y1
	 */
	private void checkPaddleCollision(int x1, int y1) {
		paddleCollisionChecker.setX(x1);
		paddleCollisionChecker.setY(y1);
		paddleCollisionChecker.check();
	}

	/**
	 * This method checks for brick collision
	 * 
	 * @param x1
	 * @param y1
	 */
	private void checkBrickCollision(int x1, int y1) {
		brickCollisionChecker.setX(x1);
		brickCollisionChecker.setY(y1);
		brickCollisionChecker.check();
	}

	/**
	 * This method checks for wall collision
	 * 
	 * @param x1
	 * @param y1
	 */
	private void checkWallCollision(int x1, int y1) {
		wallCollisionChecker.setX(x1);
		wallCollisionChecker.setY(y1);
		wallCollisionChecker.check();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paddle.draw(g);
		ball.draw(g);
		clock.draw(g);

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 5; j++) {
				bricks[i][j].draw(g);
			}
		}
		g.setColor(Color.BLUE);
		g.setFont(new Font("Tahoma", Font.BOLD, 14));

		g.drawString("Score: " + gameParameter.getScore(), GameConstants.CLOCK_LOCATION_X,
				GameConstants.CLOCK_LOCATION_Y + 160);

	}

	public void register(Observer observer) {
		observers.add(observer);
	}

	public void unregister(Observer observer) {
		observers.remove(observer);
	}

	public void notifyObservers(int timeStep) {
		for (Observer observer : observers) {
			observer.update(timeStep);
		}
	}

	public Paddle getPaddle() {
		return paddle;
	}

	public void setPaddle(Paddle paddle) {
		this.paddle = paddle;
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}

	public Clock getClock() {
		return clock;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	public Brick[][] getBricks() {
		return bricks;
	}

	public void setBricks(Brick[][] bricks) {
		this.bricks = bricks;
	}

	public List<Observer> getObservers() {
		return observers;
	}

	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}

	public ArrayDeque<GameEvent> getEvents() {
		return events;
	}

	public void setEvents(ArrayDeque<GameEvent> events) {
		this.events = events;
	}

	public Thread getGame() {
		return game;
	}

	public void setGame(Thread game) {
		this.game = game;
	}

	public int getTIME_STEP() {
		return GameConstants.TIME_STEP;
	}

	public GameParameter getGameParameter() {
		return gameParameter;
	}

	public void setGameParameter(GameParameter gameParameter) {
		this.gameParameter = gameParameter;
	}

	public GameCommand getPauseCommand() {
		return pauseCommand;
	}

	public void setPauseCommand(GameCommand pauseCommand) {
		this.pauseCommand = pauseCommand;
	}

	public GameCommand getResetCommand() {
		return resetCommand;
	}

	public void setResetCommand(GameCommand resetCommand) {
		this.resetCommand = resetCommand;
	}

	public GameCommand getResumeCommand() {
		return resumeCommand;
	}

	public void setResumeCommand(GameCommand resumeCommand) {
		this.resumeCommand = resumeCommand;
	}

	public GameCommand getUndoCommand() {
		return undoCommand;
	}

	public void setUndoCommand(GameCommand undoCommand) {
		this.undoCommand = undoCommand;
	}

	public GameCommand getReplayCommand() {
		return replayCommand;
	}

	public void setReplayCommand(GameCommand replayCommand) {
		this.replayCommand = replayCommand;
	}

	public GameCommand getStopCommand() {
		return stopCommand;
	}

	public void setStopCommand(GameCommand stopCommand) {
		this.stopCommand = stopCommand;
	}

	public JButton getStartBtn() {
		return startBtn;
	}

	public void setStartBtn(JButton startBtn) {
		this.startBtn = startBtn;
	}

	public JButton getStopBtn() {
		return stopBtn;
	}

	public void setStopBtn(JButton stopBtn) {
		this.stopBtn = stopBtn;
	}

	public JButton getUndoBtn() {
		return undoBtn;
	}

	public void setUndoBtn(JButton undoBtn) {
		this.undoBtn = undoBtn;
	}

	public JButton getReplayBtn() {
		return replayBtn;
	}

	public void setReplayBtn(JButton replayBtn) {
		this.replayBtn = replayBtn;
	}

	public GameCommand getBallMoveCommand() {
		return ballMoveCommand;
	}

	public void setBallMoveCommand(GameCommand ballMoveCommand) {
		this.ballMoveCommand = ballMoveCommand;
	}

	public GameCommand getClockTickCommand() {
		return clockTickCommand;
	}

	public void setClockTickCommand(GameCommand clockTickCommand) {
		this.clockTickCommand = clockTickCommand;
	}

	public GameCommand getCommandBroker() {
		return commandBroker;
	}

	public void setCommandBroker(GameCommand commandBroker) {
		this.commandBroker = commandBroker;
	}

	/***
	 * Create custom buttons for game UI.
	 * 
	 * @param label
	 * @return
	 */
	private JButton styleButton(final String label) {
		JButton button = new JButton(label) {
			@Override
			public void paintComponent(Graphics g) {
				if (!"Grid".equals(label)) {
					g.setColor(new Color(92, 204, 204));
				} else {
					g.setColor(Color.DARK_GRAY);
				}
				g.fillRect(0, 0, getSize().width, getSize().height);
				super.paintComponent(g);
			}
		};
		button.setContentAreaFilled(false);
		button.setForeground(Color.BLACK);
		button.setFocusPainted(false);
		button.setFont(new Font("Tahoma", Font.BOLD, 12));
		if (label.equals("Grid")) {
			button.setFont(new Font("Verdana", Font.BOLD, 20));
		}
		return button;
	}

	@Override
	public void update(int timeStep) {
		// TODO Auto-generated method stub

	}
}
