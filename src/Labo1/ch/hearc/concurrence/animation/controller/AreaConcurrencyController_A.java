package ch.hearc.concurrence.animation.controller;

import java.awt.Graphics2D;

import ch.hearc.concurrence.animation.model.Area_A;

public abstract class AreaConcurrencyController_A {

	private Area_A area;

	public AreaConcurrencyController_A(Area_A area) {
		this.area = area;
	}

	// TODO: Extends this class with classes which have the awaited behaviour.
	// You will need to implement all abstract methods :
	// -draw
	// -isIn
	// -addElementIfNotManaged_AskedFromThread
	// -removeElementIfManaged_AskedFromThread
	// And maybe (for at least one class) you will need to redefine the "reset"
	// method which actually does nothing.

	/**
	 * This method is called <b>by the thread</b> which manage the <b>given
	 * element</b> so we can ask to "java.util.concurrent" objects to do
	 * concurrency business (or call other "..._AskedFromThread" methods).<br>
	 * This method remove the element if it's no longer in an area or add it if
	 * it enters. This method is <b>already implemented</b> and is <b>based on
	 * abstract method</b>s:
	 * <ul>
	 * <li>isIn(...)</li>
	 * <li>addElement_AskedFromThread(...)</li>
	 * <li>removeElement_AskedFromThread(...)</li>
	 * </ul>
	 * 
	 * @param elementController
	 * @throws Exception
	 */
	public void checkConcurrencyAreaRestriction_AskedFromThread(ElementController elementController) throws Exception {
		if (!area.contains(elementController.getElement()) && isManaging(elementController)) {
			removeElement_AskedFromThread(elementController);
		} else if (area.contains(elementController.getElement()) && !isManaging(elementController)) {
			addElement_AskedFromThread(elementController);
		}
	}

	/**
	 * The implementation of this method should draw the concurrency area to see
	 * its behaviour at execution. It's recommended to use a new color for each
	 * concurrency area.
	 * 
	 * @param g2d
	 *            : It comes from the panel and must be use to draw the area.
	 */
	public void draw(Graphics2D g2d) {
		area.draw(g2d);
	}

	/**
	 * Check if the given element controller is actually managed by this area
	 * concurrency controller.
	 */
	public abstract boolean isManaging(ElementController elementController);

	/**
	 * The implementation of this method should <b>add the given element to
	 * managed element</b>.<br>
	 * This method is called when an element <u>enter the concurrency area</u>.
	 * <br>
	 * This method is called <b>by the thread</b> which manage the <b>given
	 * element</b> so we can ask to "java.util.concurrent" objects to
	 * <i>aquire/lock/asking for a permit...</i><br>
	 * <br>
	 * Once it has correctly asked the concurrency, it also need to be managed
	 * as "in the area" to allow it to move without asking again.<br>
	 * <i>It assume that the element is <u>currently in contact</u> with the
	 * area and is not called otherwise.</i>
	 * 
	 * @param elementController
	 *            : The element's controller which is in contact with the area
	 *            and ask to enter.
	 * @throws Exception
	 */
	protected abstract void addElement_AskedFromThread(ElementController elementController) throws Exception;

	/**
	 * The implementation of this method should <b>remove the given element to
	 * managed element</b>.<br>
	 * This method is called when an element <u>leave the concurrency area</u>
	 * but also when we <u>clear the panel (this is why it's a public
	 * method)</u><br>
	 * This method is called <b>by the thread</b> which manage the <b>given
	 * element</b> so we can ask to "java.util.concurrent" objects to
	 * <i>release/unlock for a permit...</i><br>
	 * <br>
	 * Once it has correctly release the concurrency, it also need to be removed
	 * from the "in the area" elements so the next time it will have to ask
	 * again to enter.<br>
	 * <i>It assume that the element is <u>no longer in contact with the
	 * area</u> and is not called otherwise.</i>
	 * 
	 * @param elementController
	 *            : The element's controller which is leaving the area (is just
	 *            outside the area).
	 */
	public abstract void removeElement_AskedFromThread(ElementController elementController);

	/**
	 * Called at the end of the "Clear" action when all the elements are removed
	 * from the area using <i>removeElementIfManaged_AskedFromThread</i>. The
	 * "java.util.concurrent" objects may need to be reset at their initial
	 * state.<br>
	 * <b>Not all the area need to re-implement this <u>but some does !</u></b>
	 */
	public void reset() {
		// Nothing
	}

}
