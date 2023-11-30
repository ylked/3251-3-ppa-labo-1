package ch.hearc.concurrence.animation.model;

import java.awt.Graphics2D;

public class Element {

	private double x, y;
	private int size;

	private double deplacementX;
	private double deplacementY;

	public Element(double x, double y, int size, double initAngle) {
		this.x = x;
		this.y = y;
		this.size = size;

		deplacementX = Math.cos(initAngle);
		deplacementY = Math.sin(initAngle);
	}

	public void move(int maxX, int maxY) {

		// Compute new position
		this.x += deplacementX;
		this.y += deplacementY;

		// Limit to panel size
		if (this.x - size / 2 < 0) {
			this.x = size / 2 - (this.x - size / 2);
			deplacementX = -deplacementX;
		} else if (this.x + size / 2 > maxX) {
			this.x = maxX - size / 2 - ((this.x + size / 2) - maxX);
			deplacementX = -deplacementX;
		}

		if (this.y - size / 2 < 0) {
			this.y = size / 2 - (this.y - size / 2);
			deplacementY = -deplacementY;
		} else if (this.y + size / 2 > maxY) {
			this.y = maxY - size / 2 - ((this.y + size / 2) - maxY);
			deplacementY = -deplacementY;
		}
	}

	public void draw(Graphics2D g2d) {
		g2d.drawOval((int) x - size / 2, (int) y - size / 2, size, size);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getSize() {
		return size;
	}
}
