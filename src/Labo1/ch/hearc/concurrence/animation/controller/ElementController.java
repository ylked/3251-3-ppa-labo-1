package ch.hearc.concurrence.animation.controller;

import java.util.List;

import ch.hearc.concurrence.animation.model.Element;

public class ElementController implements Runnable {
	protected static final int DEFAULT_SIZE = 4;
	private Element element;

	private List<AreaConcurrencyController_A> listConcurrencyArea;

	private Thread thread;

	private boolean isRunning = true;

	private int maxX;
	private int maxY;

	// Constructor
	public ElementController(double x, double y, double initAngle,
			List<AreaConcurrencyController_A> listConcurrencyArea, int maxX, int maxY) {
		this(x, y, DEFAULT_SIZE, initAngle, listConcurrencyArea, maxX, maxY);
	}

	public ElementController(double x, double y, int size, double initAngle,
			List<AreaConcurrencyController_A> listConcurrencyArea, int maxX, int maxY) {
		this.listConcurrencyArea = listConcurrencyArea;
		this.maxX = maxX;
		this.maxY = maxY;
		this.element = new Element(x, y, size, initAngle);

		thread = new Thread(this);
		thread.start();
	}

	public void destroy() {
		isRunning = false;
	}

	public void run() {

		while (isRunning) {

			element.move(this.maxX, this.maxY);

			for (AreaConcurrencyController_A area : listConcurrencyArea) {
				try {
					area.checkConcurrencyAreaRestriction_AskedFromThread(this);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (isRunning) {
				// Waiting 10 ms between each move.
				try {
					Thread.currentThread();
					Thread.sleep(10);
				} catch (InterruptedException e) {
					System.out.println("ERROR while waiting between moves...");
					e.printStackTrace();
				}
			}
		}

		for (AreaConcurrencyController_A area : listConcurrencyArea) {
			if (area.isManaging(this)) {
				area.removeElement_AskedFromThread(this);
			}
		}
	}

	public Element getElement() {
		return element;
	}

	public boolean isRunning() {
		return isRunning;
	}
}
