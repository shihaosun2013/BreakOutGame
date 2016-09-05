package com.util;

import java.awt.Color;

import com.board.elements.Brick;


/**
 * This class is used to abstract common functionalities of the game.
 * 
 * @author hp
 *
 */
public class CommonStructureUtility {

	/**
	 * This method creates an array of bricks and provides it to the board.
	 * 
	 * @param bricks
	 */
	public static void makeBricks(Brick bricks[][]) {

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 5; j++) {
				bricks[i][j] = new Brick((i * (GameConstants.BRICK_WIDTH + 15)), (j * (GameConstants.BRICK_HEIGHT + 15)),
						GameConstants.BRICK_WIDTH, GameConstants.BRICK_HEIGHT, new Color(86, 163, 210));
			}
		}
	}
}
