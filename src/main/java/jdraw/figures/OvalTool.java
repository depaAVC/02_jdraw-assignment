/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This tool defines a mode for drawing rectangles.
 *
 * @see jdraw.framework.Figure
 *
 * @author  Christoph Denzler
 */
public class OvalTool extends AbstractDrawTool {

	/**
	 * Temporary variable. During oval creation (during a
	 * mouse down - mouse drag - mouse up cycle) this variable refers
	 * to the new oval that is inserted.
	 */
	private Oval newOval = null;


	/**
	 * Create a new oval tool for the given context.
	 * @param context a context to use this tool in.
	 */
	public OvalTool(DrawContext context) {
		super(context);
		setName("Oval");
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
		setNewFigure( new Oval(x, y, 0, 0) );       //Type dependent constructor!
		getView().getModel().addFigure(getNewFigure());
	}
}
