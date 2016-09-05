package com.board.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/**
 * This class abstracts all the common properties of various components such as
 * its dimensions.
 * 
 * @author shihao
 *
 */
public abstract class Shape {
	protected int x, y, width, height;
	protected Color color;

	public Shape() {
	}

	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param color
	 */
	public Shape(int x, int y, int width, int height, Color color) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setColor(color);
	}

	public Shape(Shape structure) {
		this(structure.x, structure.y, structure.width, structure.height, structure.color);
	}

	public void set(Shape struc) {
		setX(struc.x);
		setY(struc.y);
		setWidth(struc.width);
		setHeight(struc.height);
		setColor(struc.color);
	}

	/**
	 * Draw the structure
	 * @param g
	 */
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Color getColor() {
		return color;
	}
}