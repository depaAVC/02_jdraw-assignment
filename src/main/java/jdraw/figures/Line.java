package jdraw.figures;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Created by degonas on 03.10.2018.
 */
public class Line extends AbstractObservableFigure {

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

    public Line(Line ol) {
        super(ol);
        this.line2D = (Line2D) ol.line2D.clone(); //evtl ol.line2D.clone();Ja, muss. sonst wird kein zweites Oval in der View angezeigt.
    }

    /**
     * Draw the line to the given graphics context.
     * @param g the graphics context to use for drawing.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawLine((int) line2D.getX1(), (int) line2D.getY1(), (int) line2D.getX2(), (int) line2D.getY2() );
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
        if( origin.getX() == line2D.getP1().getX() && origin.getY() == line2D.getP1().getY() &&
                corner.getX() == line2D.getP2().getX() && corner.getY() == line2D.getP2().getY()    ) {
            return;
        }
        line2D.setLine(origin, corner);
        notifyFigureObservers();
    }

    @Override
    public boolean contains(int x, int y) {
        return line2D.ptSegDist(new Point2D.Double(x, y)) < 25;
    }

    @Override
    public Rectangle getBounds() {
        return line2D.getBounds();
    }

    @Override
    public Line clone() {
        return new Line(this);
    }

}
