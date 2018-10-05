package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

/**
 * Created by degonas on 03.10.2018.
 */
public class Line implements Figure {

    /**
     * Use the java.awt.Rectangle in order to save/reuse code.
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

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine((int) line2D.getX1(), (int) line2D.getY1(), (int) line2D.getX2(), (int) line2D.getY2() );
    }

    @Override
    public void move(int dx, int dy) {

    }

    @Override
    public boolean contains(int x, int y) {
        return false;
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        /*if( Math.abs(origin.getX() - corner.getX()) == rectangle.getWidth() &&
                Math.abs(origin.getY() - corner.getY()) == rectangle.getHeight()    ) return;
                */
        line2D.setLine(origin, corner);
        //notifyFigureObservers();
    }

    @Override
    public Rectangle getBounds() {
        return line2D.getBounds();
    }

    @Override
    public List<FigureHandle> getHandles() {
        return null;
    }

    @Override
    public void addFigureListener(FigureListener listener) {

    }

    @Override
    public void removeFigureListener(FigureListener listener) {

    }

    @Override
    public Figure clone() {
        return null;
    }
}
