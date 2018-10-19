package jdraw.figures.handles;

import jdraw.framework.Figure;

import java.awt.*;

/**
 * Created by degonas on 18.10.2018.
 */
public class WestHandle extends AbstractFigureHandle {

    public WestHandle(Figure owner) {
        super(owner, Cursor.W_RESIZE_CURSOR);
    }

    /**
     * Returns the location of this handle. The result depends on the location
     * of the owning figure.
     *
     * @return location of this handle
     */
    @Override
    public Point getLocation() {
        Point loc = getOwner().getBounds().getLocation();
        return new Point( loc.x, (int) (loc.y + (getOwner().getBounds().getHeight() / 2)));
    }

    @Override
    public Point getOppositeCorner(int x, int y, Rectangle r) {
        return new Point(r.x + r.width, r.y + r.height);
    }

    @Override
    protected void handleDragInteraction (int x, int y, Figure owner, Point corner) {
        Point loc = owner.getBounds().getLocation();
        owner.setBounds(new Point(x, loc.y), corner);
    }
}
