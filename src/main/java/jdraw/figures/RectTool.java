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
import jdraw.framework.Figure;

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
        super(context, "Rectangle");
	}

	// a factory method, using inheritance.
    @Override
    public Figure getNewFigure(int x, int y) {
        Rect r =  new Rect(x, y, 0, 0);
        registerAddCommand(r);
        return r;
    }
}
