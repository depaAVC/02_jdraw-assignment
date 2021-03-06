/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import jdraw.figures.handles.EastHandle;
import jdraw.figures.handles.NorthHandle;
import jdraw.figures.handles.SouthHandle;
import jdraw.figures.handles.WestHandle;
import jdraw.figures.handlesStatePattern.AbstractRectengularFigure;
import jdraw.framework.FigureHandle;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents ovals in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
public class Oval extends AbstractRectengularFigure {

	/**
	 * Use the java.awt..geom.Oval in order to save/reuse code.
	 */
	private final Ellipse2D oval;

	/**
	 * Create a new oval of the given dimension.
	 * @param x the x-coordinate of the upper left corner of the oval
	 * @param y the y-coordinate of the upper left corner of the oval
	 * @param w the oval's width
	 * @param h the oval's height
	 */
	public Oval(int x, int y, int w, int h) { oval = new Ellipse2D.Double(x, y, w, h); }

	public Oval(Oval o) {
		super(o);
		this.oval = (Ellipse2D) o.oval.clone(); //evtl o.oval.clone(). Ja, muss. sonst wird kein zweites Oval in der View angezeigt.
	}

	/**
	 * Draw the oval to the given graphics context.
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval((int) oval.getX(),(int) oval.getY(),(int) oval.getWidth(),(int) oval.getHeight());
		g.setColor(Color.BLACK);
		g.drawOval((int) oval.getX(),(int) oval.getY(),(int) oval.getWidth(),(int) oval.getHeight());
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		if( Math.abs(origin.getX() - corner.getX()) == oval.getWidth() &&
                Math.abs(origin.getY() - corner.getY()) == oval.getHeight()    ) return;
		oval.setFrameFromDiagonal(origin, corner);
		notifyFigureObservers();
	}

	@Override
	public void move(int dx, int dy) {
	    if(dx == 0 && dy == 0) return;
		oval.setFrame(oval.getX() + dx, oval.getY() + dy, oval.getWidth(), oval.getHeight());
		notifyFigureObservers();
	}

	@Override
	public boolean contains(int x, int y) {
		return oval.contains(x, y);
	}

	@Override
	public Rectangle getBounds() {
		return oval.getBounds();
	}

	@Override
	public List<FigureHandle> getHandles() {
		List<FigureHandle> handles = new LinkedList<>();
		handles.add(new NorthHandle(this));
		handles.add(new SouthHandle(this));
		handles.add(new EastHandle(this));
		handles.add(new WestHandle(this));
		return handles;
	}

	@Override
	public Oval clone() {
		//Gegen Java-Cloning entschieden, da man im Scope der Clone() nicht auf
		// die privaten Felder des Klones zugreifen kann, um diese zu kopieren.
		return new Oval(this);
	}
}
