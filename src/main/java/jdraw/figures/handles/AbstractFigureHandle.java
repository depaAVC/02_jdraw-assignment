package jdraw.figures.handles;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by degonas on 18.10.2018.
 */
public abstract class AbstractFigureHandle implements FigureHandle {

    private final Figure owner;

    // defines the width and height of the handle square.
    private final int size = 6;

    //tracks the opposite corner
    private Point corner;

    private final int cursor;

    protected AbstractFigureHandle(Figure owner, int cursor) {
        this.owner = owner;
        this.cursor = cursor;
    }

    /**
     * Gets the handle's owner.
     *
     * @return the figure which owns the handle
     */
    @Override
    public Figure getOwner() {
        return owner;
    }


    /**
     * Draws this handle.
     *
     * @param g the graphics context to use for painting.
     */
    @Override
    public void draw(Graphics g) {
        Point loc = getLocation();
        int delta = size / 2;
        g.setColor(Color.WHITE); g.fillRect(loc.x - delta, loc.y - delta, size, size);
        g.setColor(Color.BLACK); g.drawRect(loc.x - delta, loc.y - delta, size, size);
    }


    /**
     * Returns a curser which should be displayed when the mouse is over the
     * handle. Signals the type of operation which can be performed using this
     * handle.
     * <P>
     * A default implementation may return Cursor.getDefaultCursor().
     *
     * @return handle's Cursor
     */
    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(cursor);
    }

    /**
     * Tests if a point is contained in the handle.
     *
     * @param x x-coordinate of mouse position
     * @param y y-coordinate of mouse position
     * @return <tt>true</tt>, if coordinates are contained in the figure,
     *         <tt>false</tt> otherwise
     */
    @Override
    public boolean contains(int x, int y) {
        Point loc = getLocation();
        int delta = size / 2;
        return Math.abs(loc.x - x) < delta && Math.abs(loc.y - y) < delta;
    }

    /**
     * Tracks the start of an interaction. Usually, the position where an
     * interaction starts is stored.
     *
     * @param x the x position where the interaction started
     * @param y the y position where the interaction started
     * @param e the mouse event which initiated the start interaction
     * @param v the view in which the interaction is performed
     */
    @Override
    public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
        Rectangle r = owner.getBounds();
        corner = getOppositeCorner(x, y, r);
    }

    //returns the position of the opposite handle.
    protected abstract Point getOppositeCorner(int x, int y, Rectangle r);

    /**
     * Tracks a step of a started interaction.
     *
     * @param x the current x position
     * @param y the current y position
     * @param e the mouse event which initiated the drag interaction
     * @param v the view in which the interaction is performed
     */
    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        owner.setBounds(new Point(x, y), corner);
    }


    /**
     * Tracks the end of a running interaction.
     *
     * @param x the current x position
     * @param y the current y position
     * @param e the mouse event which stoped the start interaction
     * @param v the view in which the interaction is performed
     */
    @Override
    public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
        corner = null;
    }
}
