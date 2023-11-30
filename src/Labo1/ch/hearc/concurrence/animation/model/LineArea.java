package ch.hearc.concurrence.animation.model;

import java.awt.Color;
import java.awt.Graphics2D;

public class LineArea extends Area_A {

	private double a;
	private double b;
	private double c;

	public LineArea(double pointX, double pointY, double directionX, double directionY, Color color) {
		super(color);
		if (directionX == 0 && directionY == 0) {
			throw new IllegalArgumentException("(directionX, directionY) ne doit pas valoir (0,0)");
		}

		double normalVectorX = -directionY;
		double normalVectorY = directionX;
		double normalVectorNorm = Math.sqrt(normalVectorX * normalVectorX + normalVectorY * normalVectorY);
		this.a = normalVectorX / normalVectorNorm;
		this.b = normalVectorY / normalVectorNorm;
		this.c = -this.a * pointX - this.b * pointY;
	}

	@Override
	public boolean contains(Element element) {
		double distance = Math.abs(this.a * element.getX() + this.b * element.getY() + c);

		return (distance <= element.getSize() / 2.0);
	}

	@Override
	protected void drawArea(Graphics2D g2d) {
		if (b != 0) {
			int x1 = 0;
			int y1 = (int) (-c / b);
			int x2 = (int) (g2d.getClip().getBounds2D().getWidth());
			int y2 = (int) ((-a * x2 - c) / b);

			g2d.drawLine(x1, y1, x2, y2);
		} else {
			int y1 = 0;
			int x1 = (int) (-c / a);
			int y2 = (int) (g2d.getClip().getBounds2D().getHeight());
			int x2 = (int) ((-b * y2 - c) / a);

			g2d.drawLine(x1, y1, x2, y2);
		}
	}

}
