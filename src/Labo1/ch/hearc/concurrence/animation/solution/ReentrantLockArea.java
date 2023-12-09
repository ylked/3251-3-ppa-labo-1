package ch.hearc.concurrence.animation.solution;

import ch.hearc.concurrence.animation.controller.AreaConcurrencyController_A;
import ch.hearc.concurrence.animation.controller.ElementController;
import ch.hearc.concurrence.animation.model.Area_A;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockArea extends AreaConcurrencyController_A {

    private final ReentrantLock lock;
    private final Set<ElementController> elements;
    public ReentrantLockArea(Area_A area) {
        super(area);
        this.elements = new HashSet<>();
        this.lock = new ReentrantLock(true);
    }

    @Override
    public boolean isManaging(ElementController elementController) {
        return this.elements.contains(elementController);
    }

    @Override
    protected void addElement_AskedFromThread(ElementController elementController) throws Exception {
        this.lock.lock();
        this.elements.add(elementController);
    }

    @Override
    public void removeElement_AskedFromThread(ElementController elementController) {
        this.lock.unlock();
        this.elements.remove(elementController);
    }

    @Override
    public void reset(){
        this.lock.unlock();
    }
}
