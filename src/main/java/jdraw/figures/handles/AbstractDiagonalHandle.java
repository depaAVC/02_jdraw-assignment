package jdraw.figures.handles;

import jdraw.framework.Figure;

import java.awt.*;

/** NW; NE; SW; SE.
 * Created by degonas on 19.10.2018.
 */
public abstract class AbstractDiagonalHandle extends AbstractFigureHandle {


    protected AbstractDiagonalHandle(Figure owner, int cursor) {
        super(owner, cursor);
    }

    protected AbstractDiagonalHandle(Figure owner, int cursor, Point corner) {
        super(owner, cursor, corner);
    }

    @Override
    protected void handleDragInteraction (int x, int y, Figure owner, Point corner) {
        owner.setBounds(new Point(x, y), corner);
    }

}
