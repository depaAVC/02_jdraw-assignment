package jdraw.figures;

import jdraw.figures.handles.*;
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

    /** Methoden, die vom Interface geerbt werden, müssen nicht mehr als abstract aufgelistet werden.
     * draw(), move(), contains(), setBounds(), getBounds().
     * e.g.
     *      public abstract void draw(Graphics g);
     *
     *      ...ist überflüssig.
     * */


    /**
     * Returns a list of 8 handles for this Rectangle.
     * @return all handles that are attached to the targeted figure.
     * @see jdraw.framework.Figure#getHandles()
     */
    @Override
    public List<FigureHandle> getHandles() {
        List<FigureHandle> handles = new LinkedList<>();
        handles.add(new NorthWestHandle(this));
        handles.add(new NorthEastHandle(this));
        handles.add(new SouthEastHandle(this));
        handles.add(new SouthWestHandle(this));
        handles.add(new NorthHandle(this));
        handles.add(new SouthHandle(this));
        handles.add(new EastHandle(this));
        handles.add(new WestHandle(this));
        return handles;
    }

    @Override
    public final void addFigureListener(FigureListener listener) {
        if (listener == null) throw new NullPointerException();
        observers.add(listener);
    }

    @Override
    public final void removeFigureListener(FigureListener listener) {
        if (listener == null) throw new NullPointerException();
        observers.remove(listener);
    }

    //protected = package-private + access from subclasses.
    protected void notifyFigureObservers() {
        if (!notifying) {
            notifying = true;   //prevents execution of inner block twice at the same time.
            FigureEvent e = new FigureEvent(this);
            for(FigureListener obs : observers) {
                obs.figureChanged( e );
            }
            notifying = false;
        }
    }

    @Override
    public Figure clone() { return null; }
}
