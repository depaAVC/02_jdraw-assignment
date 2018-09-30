/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.std;

import java.util.ArrayList;
import java.util.LinkedList;

import jdraw.framework.*;

/**
 * Provide a standard behavior for the drawing model. This class initially does not implement the methods
 * in a proper way.
 * It is part of the course assignments to do so.
 * @author Stefan Wohlgensinger
 *
 */
public class StdDrawModel implements DrawModel {

	private ArrayList<Figure> queue = new ArrayList<>();
    private LinkedList<DrawModelListener> observers = new LinkedList<>();

	@Override
	public void addFigure(Figure f) {
        if( queue.contains( f ) ) return;
        queue.add( f );
        //notify observers
        for( DrawModelListener obs : observers ){
            obs.modelChanged( new DrawModelEvent(this, f, DrawModelEvent.Type.FIGURE_ADDED) );
        }
	}

	@Override
	public Iterable<Figure> getFigures() {

        return queue;
	}

	@Override
	public void removeFigure(Figure f) {
		// TODO to be implemented  
		System.out.println("StdDrawModel.removeFigure has to be implemented");
	}

	@Override
	public void addModelChangeListener(DrawModelListener listener) {
        observers.add( listener );
	}

	@Override
	public void removeModelChangeListener(DrawModelListener listener) {
		// TODO to be implemented  
		System.out.println("StdDrawModel.removeModelChangeListener has to be implemented");
	}

	/** The draw command handler. Initialized here with a dummy implementation. */
	// TODO initialize with your implementation of the undo/redo-assignment.
	private DrawCommandHandler handler = new EmptyDrawCommandHandler();

	/**
	 * Retrieve the draw command handler in use.
	 * @return the draw command handler.
	 */
	@Override
	public DrawCommandHandler getDrawCommandHandler() {
		return handler;
	}

	@Override
	public void setFigureIndex(Figure f, int index) {
		// TODO to be implemented  
		System.out.println("StdDrawModel.setFigureIndex has to be implemented");
	}

	@Override
	public void removeAllFigures() {
		// TODO to be implemented  
		System.out.println("StdDrawModel.removeAllFigures has to be implemented");
	}

}
