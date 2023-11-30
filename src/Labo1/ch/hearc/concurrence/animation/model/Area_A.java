package ch.hearc.concurrence.animation.model;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Area_A {

	private Color color;

	public Area_A(Color color) {
		this.color = color;
	}

	/***
	 * This method is used to test (using x, y and size of an element) if it's
	 * in or out the area.
	 * 
	 * @param element
	 *            : The element testing if it's in or out the area.
	 * @return
	 */
	public abstract boolean contains(Element element);

	/***
	 * Set the color and draw the area's shape.
	 * 
	 * @param g2d
	 *            : It comes from the panel and is used to draw the area.
	 */
	public void draw(Graphics2D g2d) {
		g2d.setColor(color);
		drawArea(g2d);
	}

	/***
	 * The implementation of this method should draw the area's shape.
	 * Additional information can also be drawn.
	 * 
	 * @param g2d
	 *            : It comes from the panel and must be use to draw the area.
	 *            It's color is define in the method draw in Area_A.
	 */
	protected abstract void drawArea(Graphics2D g2d);
}
