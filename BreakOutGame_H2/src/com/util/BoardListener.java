package com.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.board.Board;


/**
 * This class implements all the actionListeners configured on various buttons.
 * 
 * @author shihao
 *
 */
public class BoardListener extends KeyAdapter implements ActionListener {

	private Board board;

	public BoardListener(Board board) {
		super();
		this.board = board;
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		int key = ke.getKeyCode();

		if (board.getGameParameter().getMode().equals(GameMode.PLAY) && !board.getGameParameter().isPaused()
				&& key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
			board.getEvents().add(new GameEvent(board.getClock().getTime(), key));
			board.getEvents().peekLast().setEventObject(board.getPaddle().move(key, board.getWidth()));
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent ae) {

		GameParameter gameParameter = board.getGameParameter();
		if (ae.getSource() == board.getStartBtn()) {
			if (true) {//gameParameter.getLives() > GameConstants.MIN_LIVES
				if (!gameParameter.isPaused()) {
					board.getPauseCommand().executeCommand();
					// To bring the focus back to the JPanel default
					board.requestFocus();
					board.getStartBtn().setText("RESUME");
				} else {
					board.getResumeCommand().executeCommand();
					board.getStartBtn().setText("PAUSE");
					// To bring the focus back to the JPanel default
					board.requestFocus();

					// If statement removed, PFTAE-5 fixed

					board.getUndoBtn().setEnabled(true);
					board.getReplayBtn().setEnabled(true);
					board.getStopBtn().setEnabled(true);
				}
			} else {
				board.getPaddle().setWidth(board.getWidth() / 7);
				gameParameter.resetScore();
				gameParameter.resetBricksLeft();
				CommonStructureUtility.makeBricks(board.getBricks());

				gameParameter.setPaused(true);
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 5; j++) {
						board.getBricks()[i][j].setDestroyed(false);
					}
				}
			}
		} else if (ae.getSource() == board.getStopBtn()) {
			// To bring the focus back to the JPanel default
			board.requestFocus();
			board.getStopCommand().executeCommand();
		}

		else if (ae.getSource() == board.getUndoBtn() && gameParameter.getMode().equals(GameMode.PLAY)) {
			if (!board.getEvents().isEmpty()) {
				board.requestFocus();
				board.getStartBtn().setText("RESUME");
				board.getUndoCommand().executeCommand();
			} else {
				board.getUndoBtn().setEnabled(false);
				board.getReplayBtn().setEnabled(false);
			}
		} else if (ae.getSource() == board.getReplayBtn() && gameParameter.getMode().equals(GameMode.PLAY)) {
			board.getEvents().add(new GameEvent(board.getClock().getTime(), GameConstants.GAME_END));
			board.requestFocus();
			board.getReplayCommand().executeCommand();
		}
	}
}
