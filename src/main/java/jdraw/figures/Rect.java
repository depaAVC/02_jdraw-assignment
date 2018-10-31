/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import jdraw.framework.Figure;

import java.awt.*;

/**
 * Represents rectangles in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
public class Rect extends AbstractObservableFigure {

	/**
	 * Use the java.awt.Rectangle in order to save/reuse code.
	 */
	private final Rectangle rectangle;
	
	/**
	 * Create a new rectangle of the given dimension.
	 * @param x the x-coordinate of the upper left corner of the rectangle
	 * @param y the y-coordinate of the upper left corner of the rectangle
	 * @param w the rectangle's width
	 * @param h the rectangle's height
	 */
	public Rect(int x, int y, int w, int h) {
		rectangle = new Rectangle(x, y, w, h);
	}


	public Rect(Rect r) {
		super(r);
		this.rectangle = (Rectangle) r.rectangle.clone(); //evtl r.rectangle.clone();
	}
	/**
	 * Draw the rectangle to the given graphics context.
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		g.setColor(Color.BLACK);
		g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		if( Math.abs(origin.getX() - corner.getX()) == rectangle.getWidth() &&
                Math.abs(origin.getY() - corner.getY()) == rectangle.getHeight()    ) return;
		rectangle.setFrameFromDiagonal(origin, corner);
		notifyFigureObservers();
	}

	@Override
	public void move(int dx, int dy) {
	    if(dx == 0 && dy == 0) return;
		rectangle.setLocation(rectangle.x + dx, rectangle.y + dy);
		notifyFigureObservers();
	}

	@Override
	public boolean contains(int x, int y) {
		return rectangle.contains(x, y);
	}

	@Override
	public Rectangle getBounds() {
		return rectangle.getBounds();
	}


	@Override
	public Rect clone() {
		//Gegen Java-Cloning entschieden, da man im Scope der Clone() nicht auf
		// die privaten Felder des Klones zugreifen kann, um diese zu kopieren.
		return new Rect(this);
	}
}
