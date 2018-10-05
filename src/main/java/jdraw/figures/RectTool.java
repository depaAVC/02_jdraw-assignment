/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;

/**
 * This tool defines a mode for drawing rectangles.
 *
 * @see jdraw.framework.Figure
 *
 * @author  Christoph Denzler
 */
public class RectTool extends AbstractDrawTool {


	/**
	 * Create a new rectangle tool for the given context.
	 * @param context a context to use this tool in.
	 */
	public RectTool(DrawContext context) {
        super(context);
		super.setName("Rectangle");
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
        setNewFigure( new Rect(x, y, 0, 0) );       //Type dependent constructor!
        getView().getModel().addFigure(getNewFigure());
    }

}
