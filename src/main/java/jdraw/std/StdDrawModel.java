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

	private ArrayList<Figure> allFigures = new ArrayList<>();
    private LinkedList<DrawModelListener> observers = new LinkedList<>();


    private void notifyObservers(Figure f, DrawModelEvent.Type type) {
        for(DrawModelListener obs : observers){
            obs.modelChanged( new DrawModelEvent(this, f, type) );
        }
    }

	@Override
	public void addFigure(Figure f) {
        if( allFigures.contains(f) ) return;
        allFigures.add( f );
        //notify observers
        notifyObservers(f, DrawModelEvent.Type.FIGURE_ADDED);
        f.addFigureListener(e -> {
            //TODO What to do here?
            notifyObservers(f, DrawModelEvent.Type.FIGURE_CHANGED);
        });
	}

	@Override
	public Iterable<Figure> getFigures() {
        return allFigures;
	}

	@Override
	public void removeFigure(Figure f) {
	    if( !allFigures.contains(f) ) return;
        allFigures.remove(f);
        notifyObservers(f, DrawModelEvent.Type.FIGURE_REMOVED);
        f.removeFigureListener( e -> { /* TODO What to put in here? */} );
	}

	@Override
	public void addModelChangeListener(DrawModelListener listener) {
        observers.add(listener);
	}

	@Override
	public void removeModelChangeListener(DrawModelListener listener) {
		observers.remove(listener);
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
        for(Figure f : allFigures) {
            notifyObservers(f, DrawModelEvent.Type.FIGURE_REMOVED);
            //f.removeFigureListener(/* TODO Where to get the listener from? */);
        }
		allFigures.clear();
        //observers.clear();
		//Todo: remove listeners
	}

}
