package ch.hearc.concurrence.animation.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

public class RectangleArea extends Area_A {

	private int topLeftX;
	private int topLeftY;
	private int width;
	private int height;
	private Shape shape;

	public RectangleArea(int topLeftX, int topLeftY, int width, int height, Color color) {
		super(color);
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;
		this.width = width;
		this.height = height;
		this.shape = new Rectangle(topLeftX, topLeftY, width, height);
	}

	@Override
	public boolean contains(Element element) {
		return (element.getX() + element.getSize() / 2.0 >= topLeftX
				&& element.getX() - element.getSize() / 2.0f <= topLeftX + width
				&& element.getY() + element.getSize() / 2.0 >= topLeftY
				&& element.getY() - element.getSize() / 2.0f <= topLeftY + height);
	}

	@Override
	public void drawArea(Graphics2D g2d) {
		g2d.draw(this.shape);
	}

}
