package jdraw.figures.handles;

import jdraw.framework.Figure;

import java.awt.*;

/** A concrete state - class. handleLocation() == getLocation() for example.
 * Created by degonas on 18.10.2018.
 */
public class SouthWestHandle extends AbstractDiagonalHandle {


    public SouthWestHandle(Figure owner) {
        super(owner, Cursor.SW_RESIZE_CURSOR);
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
        return new Point( loc.x , (int) (loc.y + getOwner().getBounds().getHeight()) );
    }

    @Override
    public Point getOppositeCorner(int x, int y, Rectangle r) {
        return new Point(r.x + r.width, r.y);
    }
}
