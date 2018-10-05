package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by degonas on 03.10.2018.
 */
public class Line implements Figure {

    private final List<FigureListener> observers = new CopyOnWriteArrayList<>(); //COWAL prevents ConcurrentModificationException when multiple observers are notified.

    // Todo: find another solution to prevent notification cycles.
    // prevents notification cycles.
    private boolean notifying = false;


    /**
     * Use the java.awt.geom.Line2D in order to save/reuse code.
     */
    private final Line2D line2D;

    /**
     * Constructs and initializes a <code>Line2D</code> from the
     * specified coordinates.
     * @param x1 the X coordinate of the start point
     * @param y1 the Y coordinate of the start point
     * @param x2 the X coordinate of the end point
     * @param y2 the Y coordinate of the end point
     * @since 1.2
     */
    public Line(double x1, double y1, double x2, double y2) {
        line2D = new Line2D.Double(x1, y1, x2, y2);
    }

    /**
     * Draw the line to the given graphics context.
     * @param g the graphics context to use for drawing.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawLine((int) line2D.getX1(), (int) line2D.getY1(), (int) line2D.getX2(), (int) line2D.getY2() );
        //g.setColor(Color.BLACK);
        //g.drawLine((int) line2D.getX1(), (int) line2D.getY1(), (int) line2D.getX2(), (int) line2D.getY2() );
    }

    //Todo: refactor into abstract super class.
    private void notifyFigureObservers() {
        if (!notifying) {
            notifying = true;   //prevents execution of inner block twice at the same time.
            for(FigureListener obs : observers) {
                obs.figureChanged( new FigureEvent(this));
            }
            notifying = false;
        }
    }

    @Override
    public void move(int dx, int dy) {
        if(dx == 0 && dy == 0) return;
        line2D.setLine(new Line2D.Double(line2D.getX1() + dx, line2D.getY1() + dy,
                line2D.getX2() + dx, line2D.getY2() + dy));
        notifyFigureObservers();
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        /*if( Math.abs(origin.getX() - corner.getX()) == rectangle.getWidth() &&
                Math.abs(origin.getY() - corner.getY()) == rectangle.getHeight()    ) return;
                */
        line2D.setLine(origin, corner);
        notifyFigureObservers();
    }

    @Override
    public boolean contains(int x, int y) {
        return line2D.contains(x, y);
    }

    @Override
    public Rectangle getBounds() {
        return line2D.getBounds();
    }

    /**
     * Returns a list of 8 handles for this Line.
     * @return all handles that are attached to the targeted figure.
     * @see jdraw.framework.Figure#getHandles()
     */
    @Override
    public List<FigureHandle> getHandles() {
        return null;
    }

    //Todo: refactor into abstract super class.
    @Override
    public void addFigureListener(FigureListener listener) {
        if (listener == null) throw new NullPointerException();
        observers.add(listener);
    }

    //Todo: refactor into abstract super class.
    @Override
    public void removeFigureListener(FigureListener listener) {
        if (listener == null) throw new NullPointerException();
        observers.remove(listener);
    }

    @Override
    public Figure clone() {
        return null;
    }
}
