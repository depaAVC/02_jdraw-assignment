package jdraw.figures.decorators;

import jdraw.figures.AbstractObservableFigure;
import jdraw.framework.Figure;

import java.awt.*;

/**
 * Created by degonas on 20.11.2018.
 */
public class GreenFigure extends AbstractObservableFigure{

    private final Figure inner;

    public GreenFigure(Figure innerFig) {
        inner = innerFig;
    }

    public Figure getInner() {
        return inner;
    }


    @Override
    public void draw(Graphics g) {
        //immutable Graphic (--> decorator which ignores certain colors in setColor).
        inner.draw(g);
        g.setColor(Color.GREEN);
    }

    @Override
    public void move(int dx, int dy) {
        inner.move(dx, dy);
    }

    @Override
    public boolean contains(int x, int y) {
        return inner.contains(x, y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        inner.setBounds(origin, corner);
    }

    @Override
    public Rectangle getBounds() {
        return inner.getBounds();
    }
}
