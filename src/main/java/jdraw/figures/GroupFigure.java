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
public class GroupFigure extends AbstractObservableFigure implements FigureGroup {

    private final List<Figure> parts = new LinkedList<>();

    public GroupFigure(List<Figure> figures) {
        for(Figure f : figures) {
            add(f);
        }
    }

    public GroupFigure(GroupFigure gf) {
        for(Figure f : gf.getFigureParts()){
            parts.add(f.clone());   //deep copy
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
        notifyFigureObservers();
    }

    @Override
    public boolean contains(int x, int y) {
        return getBounds().contains(x, y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {

    }

    @Override
    public Rectangle getBounds() {
        Rectangle rect = parts.get(0).getBounds();
        for (int i = 1; i < parts.size(); i++) {
            rect.add(parts.get(i).getBounds());
        }
        return rect;
    }


    // Needs to be commented out, lol. That's why handles didn't show up. ^^"
   // public List<FigureHandle> getHandles() {        return null;    }

    //add/removeFigureListener von AsbtractObservableFigure geerbt.

    @Override
    public Figure clone() {
        return new GroupFigure(this);
    }

    @Override
    public Iterable<Figure> getFigureParts() {
        //unmodifiableList ONLY allows query acces (get, indexOf...)
        // but prohibits modificational access (set, add, remove) from outside.
        // In otherwords. This group figure will be protected against modifications from outside.
        return Collections.unmodifiableList(parts);
    }
}
