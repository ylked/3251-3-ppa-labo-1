package ch.hearc.concurrence.animation.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class CursorArrow {

	private int x, y;
	private double angle;
	private final int length = 15;
	private boolean isVisible = false;

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void rotate(int rotation) {
		this.angle += rotation * Math.PI / 18.0;
	}

	public void draw(Graphics2D g2d) {
		if (isVisible) {
			g2d.setColor(Color.BLUE);
			g2d.setBackground(Color.BLUE);
			double offset = (length / 4.0);
			double x1 = (x + Math.cos(angle) * offset);
			double y1 = (y + Math.sin(angle) * offset);
			double x2 = (x + Math.cos(angle) * length);
			double y2 = (y + Math.sin(angle) * length);
			g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);

			double x3 = (x2 + Math.sin(angle - Math.PI * 5 / 6.0 + Math.PI / 2.0) * length / 2.0);
			double y3 = (y2 - Math.cos(angle - Math.PI * 5 / 6.0 + Math.PI / 2.0) * length / 2.0);
			double x4 = (x2 + Math.sin(angle - Math.PI * 7 / 6.0 + Math.PI / 2.0) * length / 2.0);
			double y4 = (y2 - Math.cos(angle - Math.PI * 7 / 6.0 + Math.PI / 2.0) * length / 2.0);
			g2d.drawLine((int) x2, (int) y2, (int) x3, (int) y3);
			g2d.drawLine((int) x2, (int) y2, (int) x4, (int) y4);

			Ellipse2D.Double circle = new Ellipse2D.Double(x - offset, y - offset, 2.0 * offset, 2.0 * offset);
			g2d.fill(circle);
		}
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
