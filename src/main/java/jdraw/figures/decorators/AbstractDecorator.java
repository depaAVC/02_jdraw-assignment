package jdraw.figures.decorators;

import jdraw.figures.AbstractObservableFigure;
import jdraw.framework.Figure;

import java.awt.*;

/**
 * Created by degonas on 19.12.2018.
 */
public abstract class AbstractDecorator extends AbstractObservableFigure {

    private final Figure inner;

    public AbstractDecorator(Figure innerFig) {
        inner = innerFig;
    }

    public Figure getInner() {
        return inner;
    }


    @Override
    public void draw(Graphics g) {
        inner.draw(g);
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
