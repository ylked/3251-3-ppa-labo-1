package ch.hearc.concurrence.animation.solution;

import ch.hearc.concurrence.animation.controller.AreaConcurrencyController_A;
import ch.hearc.concurrence.animation.controller.ElementController;
import ch.hearc.concurrence.animation.model.Area_A;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SemaphoreArea extends AreaConcurrencyController_A {

    List<ElementController> elements;
    private static final boolean FAIR = true;
    private Semaphore sem;
    public SemaphoreArea(Area_A area, int n) {
        super(area);
        this.elements = new LinkedList<>();
        this.sem = new Semaphore(n, FAIR);
    }

    @Override
    public boolean isManaging(ElementController elementController) {
        return this.elements.contains(elementController);
    }

    @Override
    protected void addElement_AskedFromThread(ElementController elementController) throws Exception {
        this.elements.add(elementController);
        this.sem.acquire();
    }

    @Override
    public void removeElement_AskedFromThread(ElementController elementController) {
        this.elements.remove(elementController);
        this.sem.release();
    }
}
