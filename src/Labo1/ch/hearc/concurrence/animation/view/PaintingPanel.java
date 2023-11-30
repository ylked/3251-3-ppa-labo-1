package ch.hearc.concurrence.animation.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import ch.hearc.concurrence.animation.controller.AreaConcurrencyController_A;
import ch.hearc.concurrence.animation.controller.ElementController;

public class PaintingPanel extends JPanel {

	private List<ElementController> listElements = new LinkedList<ElementController>();
	private List<AreaConcurrencyController_A> listConcurrencyArea = new LinkedList<AreaConcurrencyController_A>();

	private CursorArrow cursorArrow;
	private boolean isRandomAngle = true;
	private static Random random = new Random(System.currentTimeMillis());

	private MainFrame mainFrame;

	public PaintingPanel(MainFrame mainFrame) {
		addListeners();

		// TODO: Implémenter des classes dérivant de "AreaConcurrencyController_A",
		// puis en ajouter des instances dans "listConcurrencyArea" afin qu'elles
		// soient présentes dans la fenêtre principiale et finalement testées lors
		// d'une exécution de ce programme.
		/*
		 * Exemple : 
		listConcurrencyArea.add(new BarrierAreaConcurrencyController(5, new LineArea(120, 120, -1, -0.7, Color.GREEN)));
		listConcurrencyArea.add(new ReentrantLockAreaConcurrencyController(new LineArea(500, 0, 0, 1, Color.RED)));
		listConcurrencyArea
				.add(new RestrictedSeveralElementsAreaConcurrencyController(3, new RectangleArea(200, 200, 200, 100, Color.BLUE)));
		listConcurrencyArea
				.add(new RestrictedOneElementConcurrencyAreaController(new RectangleArea(0, 0, 200, 100, Color.ORANGE)));
		 */

		this.mainFrame = mainFrame;

		cursorArrow = new CursorArrow();
		double randomAngle = 2 * Math.PI * random.nextDouble();
		cursorArrow.setAngle(randomAngle);

		Timer timer = new Timer(16, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PaintingPanel.this.repaint();
			}
		});
		timer.start();
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g2d = (Graphics2D) graphics;

		g2d.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);

		// AntiAlisiang hints
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		for (ElementController elementController : listElements) {
			elementController.getElement().draw(g2d);
		}
		for (AreaConcurrencyController_A concurrencyArea : listConcurrencyArea) {
			concurrencyArea.draw(g2d);
		}

		this.requestFocus();
		this.requestFocusInWindow();

		cursorArrow.draw(g2d);
		
		mainFrame.refreshInfo();
	}

	private void addElement(MouseEvent e) {
		listElements.add(new ElementController(e.getX(), e.getY(), cursorArrow.getAngle(), listConcurrencyArea,
				PaintingPanel.this.getWidth(), PaintingPanel.this.getHeight()));
		if (isRandomAngle) {
			cursorArrow.setAngle(2 * Math.PI * random.nextDouble());
		}
	}

	private void addListeners() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				addElement(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				cursorArrow.setVisible(false);
				Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
				mainFrame.setCursor(cursor);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				cursorArrow.setVisible(true);
				BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
				Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0),
						"blank cursor");
				mainFrame.setCursor(cursor);
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				addElement(e);
				cursorArrow.setX(e.getX());
				cursorArrow.setY(e.getY());
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				cursorArrow.setX(e.getX());
				cursorArrow.setY(e.getY());
			}
		});

		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int rotation = e.getWheelRotation();
				cursorArrow.rotate(rotation);
			}
		});
	}

	public void clearPainting() {
		for (ElementController element : listElements) {
			element.destroy();
		}
		listElements.clear();
		for (AreaConcurrencyController_A area : listConcurrencyArea) {
			area.reset();
		}
	}

	public List<AreaConcurrencyController_A> getListConcurrencyArea() {
		return listConcurrencyArea;
	}

	public void setIsRandomAngle(boolean isRandomAngle) {
		this.isRandomAngle = isRandomAngle;
	}
	
	public int getElementsNumber(){
		return listElements.size();
	}
}
