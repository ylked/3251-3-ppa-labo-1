package ch.hearc.concurrence.animation.solution;

import ch.hearc.concurrence.animation.controller.AreaConcurrencyController_A;
import ch.hearc.concurrence.animation.controller.ElementController;
import ch.hearc.concurrence.animation.model.Area_A;

import java.util.*;
import java.util.concurrent.CyclicBarrier;

public class BarrierArea extends AreaConcurrencyController_A {
    private final CyclicBarrier barrier;
    private final Area_A area;
    private final List<ElementController> elements;

    public BarrierArea(Area_A area, int n) {
        super(area);
        this.area = area;
        this.barrier = new CyclicBarrier(n);
        this.elements = new LinkedList<>();
    }

    @Override
    public boolean isManaging(ElementController elementController) {
        if(this.area.contains(elementController.getElement()) && this.elements.contains(elementController)){
            return true;
        } else{
            return false;
        }
    }

    @Override
    protected void addElement_AskedFromThread(ElementController elementController) throws Exception {
        this.barrier.await();
        this.elements.add(elementController);
    }

    @Override
    public void removeElement_AskedFromThread(ElementController elementController) {
        //nothing..
        this.elements.remove(elementController);
    }

    @Override
    public void reset(){
        this.barrier.reset();
    }
}
