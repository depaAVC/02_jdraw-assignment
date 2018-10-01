/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

/**
 * Represents rectangles in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
public class Rect implements Figure {

    //private LinkedList<FigureListener> observers = new LinkedList<>();    //led to ConcurrentModificationException in RectangleTest.testRemoveListener
    /*  CopyOnWriteArrayList - a multithread safe collection:
    *   Prevents ConcurrentModificationException when a listener is removed while others are still
    *   being notified. CopyOnWriteArrayList creates a duplicate of the ArrayList during mutative operations
    *   such as add or set.
    *
    *   Why did this a problem occur?:
    *   Iterators have a modification counter. If the collection they are iterating over
    *   gets modified and thus leading the iterator to be outdated, they throw a ConcurrentModificationException.
    *
    *   See: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CopyOnWriteArrayList.html.
    */
    private List<FigureListener> observers = new CopyOnWriteArrayList<>();

    // prevents notification cycles.
    private boolean notifying = false;

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

	private void notifyFigureObservers() {
	    if (!notifying) {
            notifying = true;   //prevents execution of inner block twice at the same time.
            for(FigureListener obs : observers) {
                obs.figureChanged( new FigureEvent(this));
            }
            notifying = false;
        }
    }
	
	@Override
	public void setBounds(Point origin, Point corner) {
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

	/**
	 * Returns a list of 8 handles for this Rectangle.
	 * @return all handles that are attached to the targeted figure.
	 * @see jdraw.framework.Figure#getHandles()
	 */	
	@Override
	public List<FigureHandle> getHandles() {
		return null;
	}

	@Override
	public void addFigureListener(FigureListener listener) {
        if (listener == null) return;
	    observers.add(listener);
	}

	@Override
	public void removeFigureListener(FigureListener listener) {
        if (listener == null) return;
		observers.remove(listener);
	}

	@Override
	public Figure clone() {
		return null;
	}

}
