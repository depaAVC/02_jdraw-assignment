package jdraw.figures.handles;

import jdraw.framework.Figure;

import java.awt.*;

/**
 * Created by degonas on 18.10.2018.
 */
public class NorthEastHandle extends AbstractDiagonalHandle {

    public NorthEastHandle(Figure owner) {
        super(owner, Cursor.NE_RESIZE_CURSOR);
    }

    public NorthEastHandle(Figure owner, Point corner) {
        super(owner, Cursor.NE_RESIZE_CURSOR, corner);
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
        return new Point( (int) (loc.x + getOwner().getBounds().getWidth()), loc.y );
    }

    @Override
    public Point getOppositeCorner(int x, int y, Rectangle r) {
        return new Point(r.x, r.y + r.height);
    }
}
