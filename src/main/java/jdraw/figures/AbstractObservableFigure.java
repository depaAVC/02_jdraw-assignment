package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by degonas on 05.10.2018.
 */
public abstract class AbstractObservableFigure implements Figure {

    /*  CopyOnWriteArrayList - a multithread safe collection:
    *   Prevents ConcurrentModificationException when a listener is removed while others are still
    *   being notified. CopyOnWriteArrayList creates a duplicate of the ArrayList during mutative operations
    *   such as add or set.
    *
    *   Why did this a problem occur?:
    *   Iterators have a modification counter. If the collection they are iterating over
    *   gets modified and thus leading the iterator to be outdated, they throw a ConcurrentModificationException.
    *
    *   See: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CopyOnWriteArrayList.html.
    */
    private final List<FigureListener> observers = new CopyOnWriteArrayList<>();

    // Todo: find another solution to prevent notification cycles.
    // prevents notification cycles.
    private boolean notifying = false;

    @Override
    public abstract void draw(Graphics g);

    @Override
    public abstract void move(int dx, int dy);

    @Override
    public abstract boolean contains(int x, int y);

    @Override
    public abstract void setBounds(Point origin, Point corner);

    @Override
    public abstract Rectangle getBounds();

    /**
     * Returns a list of 8 handles for this Rectangle.
     * @return all handles that are attached to the targeted figure.
     * @see jdraw.framework.Figure#getHandles()
     */
    @Override
    public List<FigureHandle> getHandles() {
        return null;
    }

    @Override
    public void addFigureListener(FigureListener listener) {
        if (listener == null) throw new NullPointerException();
        observers.add(listener);
    }

    @Override
    public void removeFigureListener(FigureListener listener) {
        if (listener == null) throw new NullPointerException();
        observers.remove(listener);
    }

    void notifyFigureObservers() {
        if (!notifying) {
            notifying = true;   //prevents execution of inner block twice at the same time.
            for(FigureListener obs : observers) {
                obs.figureChanged( new FigureEvent(this));
            }
            notifying = false;
        }
    }

    @Override
    public Figure clone() { return null; }
}
