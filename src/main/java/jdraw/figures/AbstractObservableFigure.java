package jdraw.figures;

import jdraw.figures.handles.*;
import jdraw.figures.handlesStatePattern.Handle;
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

    public AbstractObservableFigure(AbstractObservableFigure aof) {
        //level 1 deep copy: different lists, but same listeners.
        for(FigureListener fl : aof.observers) {
            this.observers.add(fl);
        }
    }

    //required bcs as soon as copy constructor got introduced, there was no implicit
    // default constructor left.
    public AbstractObservableFigure() {
        //empty bcs all fields are initialized before constructor call.
    }

    /**
     * Returns a list of 8 handles for this Rectangle.
     * @return all handles that are attached to the targeted figure.
     * @see jdraw.framework.Figure#getHandles()
     */
    @Override
    public List<FigureHandle> getHandles() {
        List<FigureHandle> handles = new LinkedList<>();
        //addHandlesOfInheritanceApproach(handles);

        handles.add(new Handle(this, Handle.NW, Color.BLUE));
        handles.add(new Handle(this, Handle.SW, Color.ORANGE));
        handles.add(new Handle(this, Handle.NE, Color.RED));
        handles.add(new Handle(this, Handle.SE));

        return handles;
    }


    private void addHandlesOfInheritanceApproach(List<FigureHandle> handles) {
        handles.add(new NorthWestHandle(this));
        handles.add(new NorthEastHandle(this));
        handles.add(new SouthEastHandle(this));
        handles.add(new SouthWestHandle(this));
        handles.add(new NorthHandle(this));
        handles.add(new SouthHandle(this));
        handles.add(new EastHandle(this));
        handles.add(new WestHandle(this));
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
    public Figure clone() {
        try {
            Figure cf = (Figure) super.clone();
            //deep copy of lvl 1 (clone of observer list)?
            //List<FigureListener> listeners = new CopyOnWriteArrayList<>(observers);
            //new AbstractObservableFigure(this) geht wegen abstract nicht.
            //wie auf private Felder zugreifen?
            // Fragt sich, ob die clone() in AbstractObservablefigure leer gelassen werden kann oder
            // eine CloneNotSupportedException wirft.
            return cf;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }
}
