package jdraw.figures.handles;

import jdraw.framework.Figure;

import java.awt.*;

/**
 * Created by degonas on 18.10.2018.
 */
public class NorthWestHandle extends AbstractFigureHandle {


    public NorthWestHandle(Figure owner) {
        super(owner, Cursor.NW_RESIZE_CURSOR);
    }

    /**
     * Returns the location of this handle. The result depends on the location
     * of the owning figure.
     *
     * @return location of this handle
     */
    @Override
    public Point getLocation() {
        return getOwner().getBounds().getLocation();
    }

    @Override
    public Point getOppositeCorner(int x, int y, Rectangle r) {
        return new Point(r.x + r.width, r.y + r.height);
    }
}
