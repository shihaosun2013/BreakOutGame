package com.launcher;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.board.Board;
import com.util.GameConstants;


/**
 * Launcher class of the game.
 * 
 * @author shihao
 *
 */
public class Launcher extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8667871570909019613L;
	private static final Logger LOGGER = Logger.getLogger(Board.class.getName());
	private static Board board;
	private static Dimension dim;

	public Launcher() {
		launchGame();
	}

	/**
	 * Main method loads and initiates the game.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Launcher();
	}

	private void launchGame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("Brick Breaker 3.0");
		setSize(GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		board = new Board(GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);
		getContentPane().add(board);

		// Place frame in the middle of the screen
		try {
			dim = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		} catch (Exception e) {
			LOGGER.warning(e.getMessage() + e);
		}
		setVisible(true);
	}
}