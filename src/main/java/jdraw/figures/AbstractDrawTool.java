package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by degonas on 05.10.2018.
 */
public abstract class AbstractDrawTool implements DrawTool {


    private String name = "";

    /**
     * the image resource path.
     */
    private static final String IMAGES = "/images/";

    /**
     * The context we use for drawing.
     */
    private DrawContext context;

    /**
     * The context's view. This variable can be used as a shortcut, i.e.
     * instead of calling context.getView().
     */
    private DrawView view;

    /**
     * Temporary variable. During rectangle creation (during a
     * mouse down - mouse drag - mouse up cycle) this variable refers
     * to the new rectangle that is inserted.
     */
    private AbstractObservableFigure newFigure = null;

    /**
     * Temporary variable.
     * During line creation this variable refers to the point the
     * mouse was first pressed.
     */
    private Point anchor = null;

    public AbstractDrawTool(DrawContext context) {
        this.context = context;
        this.view = context.getView();
    }

    /**
     * Activates the Rectangle Mode. There will be a
     * specific menu added to the menu bar that provides settings for
     * Rectangle attributes
     */
    @Override
    public void activate() {
        this.context.showStatusText(name + " Mode");
    }

    /**
     * Deactivates the current mode by resetting the cursor
     * and clearing the status bar.
     * @see jdraw.framework.DrawTool#deactivate()
     */
    @Override
    public void deactivate() {
        this.context.showStatusText("");
    }

    /**
     * Initializes a new Oval object by setting an anchor
     * point where the mouse was pressed. A new Oval is then
     * added to the model.
     * @param x x-coordinate of mouse
     * @param y y-coordinate of mouse
     * @param e event containing additional information about which keys were pressed.
     *
     * @see DrawTool#mouseDown(int, int, MouseEvent)
     */
    @Override
    public abstract void mouseDown(int x, int y, MouseEvent e);

    /**
     * During a mouse drag, the Oval will be resized according to the mouse
     * position. The status bar shows the current size.
     *
     * @param x   x-coordinate of mouse
     * @param y   y-coordinate of mouse
     * @param e   event containing additional information about which keys were
     *            pressed.
     *
     * @see DrawTool#mouseDrag(int, int, MouseEvent)
     */
    @Override
    public void mouseDrag(int x, int y, MouseEvent e) {
        newFigure.setBounds(anchor, new Point(x, y));
        java.awt.Rectangle r = newFigure.getBounds();
        this.context.showStatusText("w: " + r.width + ", h: " + r.height);
    }

    /**
     * When the user releases the mouse, the Oval object is updated
     * according to the color and fill status settings.
     *
     * @param x   x-coordinate of mouse
     * @param y   y-coordinate of mouse
     * @param e   event containing additional information about which keys were
     *            pressed.
     *
     * @see DrawTool#mouseUp(int, int, MouseEvent)
     */
    @Override
    public void mouseUp(int x, int y, MouseEvent e) {
        newFigure = null;
        anchor = null;
        this.context.showStatusText(name + " Mode");
    }


    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }

    @Override
    public Icon getIcon() {
        return new ImageIcon(getClass().getResource(IMAGES + name.toLowerCase() + ".png"));
    }

    @Override
    public String getName() {
        return name;
    }

    void setName(String n) {
        this.name = n;
    }

    // XXX Provisional solution: Eventually replace it by using Factory pattern.
    public DrawView getView() {
        return view;
    }

    public AbstractObservableFigure getNewFigure() {
        return newFigure;
    }

    public void setNewFigure(AbstractObservableFigure newFigure) {
        this.newFigure = newFigure;
    }

    public void setAnchor(Point anchor) {
        this.anchor = anchor;
    }
}
