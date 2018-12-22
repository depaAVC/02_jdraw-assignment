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

    private final Handle NW = new Handle(this, Handle.NW, Color.BLUE);
    private final Handle SW = new Handle(this, Handle.SW, Color.ORANGE);
    private final Handle NE = new Handle(this, Handle.NE, Color.RED);
    private final Handle SE = new Handle(this, Handle.SE);
    private final Handle E = new Handle(this, Handle.E);
    private final Handle W = new Handle(this, Handle.W);
    private final Handle N = new Handle(this, Handle.N);
    private final Handle S = new Handle(this, Handle.S);

    public AbstractObservableFigure(AbstractObservableFigure aof) {
        //level 1 deep copy: different lists, but same listeners.
        System.out.println("Observerlist: before" + observers.size());

        for(FigureListener fl : aof.observers) {
            this.observers.add(fl);
        }
        System.out.println("Observerlist after: " + observers.size());
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

        handles.add(NW);    //NW = a predefined handle / an attribute of this figure
        handles.add(SW);
        handles.add(NE);
        handles.add(SE);
        /*handles.add(E);   //not implemented Handle.states
        handles.add(W);
        handles.add(N);
        handles.add(S);*/

        return handles;
    }

    public void swapHandlesHorizontal() {
        Handle.State NWstate = NW.getState();
        Handle.State NEstate = NE.getState();
        Handle.State SWstate = SW.getState();
        Handle.State SEstate = SE.getState();
        Handle.State WState = W.getState();
        Handle.State EState = E.getState();
        NW.setState(NEstate);
        NE.setState(NWstate);
        SW.setState(SEstate);
        SE.setState(SWstate);
        W.setState(EState);
        E.setState(WState);
    }

    public void swapHandlesVertically() {

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
        System.out.println("Observerlist: addFigure" + observers.size());
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
