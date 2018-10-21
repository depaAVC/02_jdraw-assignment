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
        default State handleLocation(HandleData data, Figure owner) {return ERROR; }
        default State handleCursor(HandleData data) {return ERROR; }
        default State handleOppositeCorner(HandleData data, int x, int y, Rectangle r) {return ERROR; }
        default State handleDragInteraction(HandleData data, Figure owner, int x, int y) {return ERROR; }
    }

    static class NW implements State {
        @Override
        public State handleLocation(HandleData data, Figure owner) {
            data.location = owner.getBounds().getLocation();
            return this;
        }

        @Override
        public State handleOppositeCorner(HandleData data, int x, int y, Rectangle r) {
            data.corner = new Point(r.x + r.width, r.y + r.height);
            return this;
        }

        @Override
        public State handleCursor(HandleData data) { data.cursor = Cursor.NW_RESIZE_CURSOR; return this; }

        @Override
        public State handleDragInteraction(HandleData data, Figure owner, int x, int y) {
            owner.setBounds(new Point(x, y), data.corner);
            return this;
        }
    }

    static class NE implements State {

    }

    static class SW implements State {

    }

    static class SE implements State {

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

    @Override
    public Figure getOwner() {
        return owner;
    }

    @Override
    public Point getLocation() {
        state = state.handleLocation(data, owner);
        return data.location;
    }

    @Override
    public void draw(Graphics g) {
        Point loc = getLocation();
        int delta = size / 2;
        g.setColor(Color.WHITE); g.fillRect(loc.x - delta, loc.y - delta, size, size);
        g.setColor(Color.BLACK); g.drawRect(loc.x - delta, loc.y - delta, size, size);
    }

    @Override
    public Cursor getCursor() {
        state = state.handleCursor(data);
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
        state = state.handleOppositeCorner(data, x, y, r);
        //data.corner = getOppositeCorner(x, y, r);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        state = state.handleDragInteraction(data,owner, x ,y);
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
