package jdraw.figures.handlesStatePattern;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by degonas on 21.10.2018.
 */
public class Handle implements FigureHandle {

    static interface State {
        //default State handleGetOwner() {}
        default void handleLocation(HandleData data, Figure owner) {}
        default void handleCursor(HandleData data) {}
        default void handleOppositeCorner(HandleData data, int x, int y, Rectangle r) {}
        default State handleDragInteraction(HandleData data, Figure owner, int x, int y) {return ERROR; }
    }

    static abstract class AbstractDiagonalState implements State {
        @Override
        public State handleDragInteraction(HandleData data, Figure owner, int x, int y) {
            owner.setBounds(new Point(x, y), data.corner);
            if (x < data.corner.x && y < data.corner.y) return NW;
            if (x < data.corner.x && y > data.corner.y) return SW;
            if (x > data.corner.x && y < data.corner.y) return NE;
            //if (x > data.corner.x && y > data.corner.y) return SE;
            else return SE;
        }
    }

    static class NW extends AbstractDiagonalState {
        @Override
        public void handleLocation(HandleData data, Figure owner) {
            data.location = owner.getBounds().getLocation();
        }

        @Override
        public void handleOppositeCorner(HandleData data, int x, int y, Rectangle r) {
            data.corner = new Point(r.x + r.width, r.y + r.height);
        }

        @Override
        public void handleCursor(HandleData data) { data.cursor = Cursor.NW_RESIZE_CURSOR; }

    }

    static class NE extends AbstractDiagonalState {
        @Override
        public void handleLocation(HandleData data, Figure owner) {
            Point loc = owner.getBounds().getLocation();
            data.location = new Point( (int) (loc.x + owner.getBounds().getWidth()), loc.y );
        }

        @Override
        public void handleOppositeCorner(HandleData data, int x, int y, Rectangle r) {
            data.corner = new Point(r.x, r.y + r.height);
        }

        @Override
        public void handleCursor(HandleData data) { data.cursor = Cursor.NE_RESIZE_CURSOR; }

    }

    static class SW extends AbstractDiagonalState {
        @Override
        public void handleLocation(HandleData data, Figure owner) {
            Point loc = owner.getBounds().getLocation();
            data.location =  new Point( loc.x , (int) (loc.y + owner.getBounds().getHeight()) );
        }

        @Override
        public void handleOppositeCorner(HandleData data, int x, int y, Rectangle r) {
            data.corner = new Point(r.x + r.width, r.y);
        }

        @Override
        public void handleCursor(HandleData data) { data.cursor = Cursor.SW_RESIZE_CURSOR; }
    }

    static class SE extends AbstractDiagonalState {
        @Override
        public void handleLocation(HandleData data, Figure owner) {
            Point loc = owner.getBounds().getLocation();
            data.location =  new Point( (int) (loc.x + owner.getBounds().getWidth()),
                    (int) (loc.y + owner.getBounds().getHeight()) );
        }

        @Override
        public void handleOppositeCorner(HandleData data, int x, int y, Rectangle r) {
            data.corner = new Point(r.x, r.y);
        }

        @Override
        public void handleCursor(HandleData data) { data.cursor = Cursor.SE_RESIZE_CURSOR; }
    }

    static class N implements State {

    }

    static class S implements State {

    }

    static class E implements State {

    }

    static class W implements State {

    }

    static class ErrorState implements State {}

    //public to initialize state externally.
    private static final State ERROR = new ErrorState();
    public static final State NW = new NW();
    public static final State NE = new NE();
    public static final State SW = new SW();
    public static final State SE = new SE();
    public static final State N = new N();
    public static final State S = new S();
    public static final State E = new E();
    public static final State W = new W();

    private State state;

    //state dependent values.
    static class HandleData {
        public int cursor;
        public Point corner;
        public Point location; //needed?
    }

    private HandleData data = new HandleData();


    private final Figure owner;

    // defines the width and height of the handle square.
    private final int size = 6;


    public Handle(Figure owner, State initialState) {
        this.owner = owner;
        state = initialState;
    }

    //for testing purposes.
    private Color fill = Color.WHITE;
    public Handle(Figure owner, State initialState, Color fill) {
        this(owner, initialState);
        this.fill = fill;
    }

    @Override
    public Figure getOwner() {
        return owner;
    }

    @Override
    public Point getLocation() {
        state.handleLocation(data, owner);
        return data.location;
    }

    @Override
    public void draw(Graphics g) {
        Point loc = getLocation();
        int delta = size / 2;
        g.setColor(fill); g.fillRect(loc.x - delta, loc.y - delta, size, size);
        g.setColor(Color.BLACK); g.drawRect(loc.x - delta, loc.y - delta, size, size);
    }

    @Override
    public Cursor getCursor() {
        state.handleCursor(data);
        return Cursor.getPredefinedCursor(data.cursor);
    }

    @Override
    public boolean contains(int x, int y) {
        Point loc = getLocation();
        int delta = size / 2;
        return Math.abs(loc.x - x) < delta && Math.abs(loc.y - y) < delta;
    }

    @Override
    public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
        Rectangle r = owner.getBounds();
        state.handleOppositeCorner(data, x, y, r);
        //data.corner = getOppositeCorner(x, y, r);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        state = state.handleDragInteraction(data, owner, x ,y);
    }

    @Override
    public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
        data.corner = null;
    }

    @Override
    public Point getCorner() {
        return data.corner;
    }
}
