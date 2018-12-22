package jdraw.figures.decorators;

import jdraw.figures.AbstractObservableFigure;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

import java.awt.*;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * Created by degonas on 19.12.2018.
 */
public abstract class AbstractDecorator extends AbstractObservableFigure {

    private Figure inner; //can't be declared final due to java cloning.


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


    @Override
    public AbstractDecorator clone() {
        AbstractDecorator f = (AbstractDecorator) super.clone();
        f.inner = inner.clone();
        //f.handles= null;
        return f;
    }

    @Override
    public java.util.List<FigureHandle> getHandles() {
        java.util.List<FigureHandle> handles = new LinkedList<>();
        for(FigureHandle h : getInner().getHandles()) {
            handles.add(new HandleDecorator(h));
        }
        return Collections.unmodifiableList(handles);
    }

    //inner class for figure handles.
    private final class HandleDecorator implements FigureHandle {

        private final FigureHandle inner;

        public HandleDecorator(FigureHandle fh){
            inner = fh;
        }

        @Override
        public Figure getOwner() {
            return AbstractDecorator.this;
        }

        @Override
        public Point getLocation() {
            return inner.getLocation();
        }

        @Override
        public void draw(Graphics g) {
            inner.draw(g);
        }

        @Override
        public Cursor getCursor() {
            return inner.getCursor();
        }

        @Override
        public boolean contains(int x, int y) {
            return inner.contains(x, y);
        }

        @Override
        public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
            inner.startInteraction(x, y, e, v);
        }

        @Override
        public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
            inner.dragInteraction(x, y, e, v);
        }

        @Override
        public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
            inner.stopInteraction(x, y, e, v);
        }

        @Override
        public Point getCorner() {
            return inner.getCorner();
        }
    }
}
