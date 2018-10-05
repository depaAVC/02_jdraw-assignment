package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by degonas on 03.10.2018.
 */
public class LineTool extends AbstractDrawTool {

    /**
     * Create a new rectangle tool for the given context.
     * @param context a context to use this tool in.
     */
    public LineTool(DrawContext context) {
        super(context);
        setName("Line");
    }

    // XXX Provisional solution: Eventually replace it by using Factory pattern.
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
    public void mouseDown(int x, int y, MouseEvent e) {
        // XXX Provisional solution: Eventually replace it by using Factory pattern.
        if (getNewFigure() != null) {
            throw new IllegalStateException();
        }
        setAnchor( new Point(x, y) );
        setNewFigure( new Line(x, y, 0, 0) );       //Type dependent constructor!
        getView().getModel().addFigure(getNewFigure());
    }

}
