package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureGroup;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by degonas on 25.10.2018.
 */
public class GroupFigure implements Figure, FigureGroup {

    private List<Figure> parts = new LinkedList<>();
    private Rectangle rect = new Rectangle();

    public GroupFigure(List<Figure> figures) {
        for(Figure f : figures) {
            add(f);
        }
    }

    private void add(Figure f) {
        //Todo: parent attribute in all figures
        parts.add(f);
    }

    @Override
    public void draw(Graphics g) {
        for (Figure f : parts) {
            f.draw(g);
        }
    }

    @Override
    public void move(int dx, int dy) {
        for (Figure f : parts) {
            f.move(dx, dy);
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return rect.contains(x, y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {

    }

    @Override
    public Rectangle getBounds() {
        for (Figure f : parts) {
            rect.add(f.getBounds());
        }
        return rect;
    }

    @Override
    public List<FigureHandle> getHandles() {
        return null;
    }

    @Override
    public void addFigureListener(FigureListener listener) {
        //todo: inherit from AbstractObservableFigure?
    }

    @Override
    public void removeFigureListener(FigureListener listener) {

    }

    @Override
    public Figure clone() {
        return null;
    }

    @Override
    public Iterable<Figure> getFigureParts() {
        return parts;
    }
}
