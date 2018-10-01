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
public class StdDrawModel implements DrawModel, FigureListener{

	private ArrayList<Figure> allFigures = new ArrayList<>();
    private LinkedList<DrawModelListener> observers = new LinkedList<>();


    private void notifyModelObservers(Figure f, DrawModelEvent.Type type) {
        for(DrawModelListener obs : observers){
            obs.modelChanged( new DrawModelEvent(this, f, type) );
        }
    }

	@Override
	public void addFigure(Figure f) {
        if( allFigures.contains(f) ) return;
        allFigures.add( f );
        //notify observers
        notifyModelObservers(f, DrawModelEvent.Type.FIGURE_ADDED);
        f.addFigureListener(this);
	}

	@Override
	public Iterable<Figure> getFigures() {
        return allFigures;
	}

	@Override
	public void removeFigure(Figure f) {
	    if( !allFigures.contains(f) ) return;
        allFigures.remove(f);
        notifyModelObservers(f, DrawModelEvent.Type.FIGURE_REMOVED);
        f.removeFigureListener( this );
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
        if (f == null) throw new NullPointerException();
        if (!allFigures.contains(f)) throw new IllegalArgumentException();
        if (index < 0 || index >= allFigures.size()) throw new IndexOutOfBoundsException();

	    int currentIndex = allFigures.indexOf(f);
	    if (currentIndex == index) return;
	    //move
        Figure currentFig;
        int i;
	    if (index < currentIndex) {
	        i = currentIndex - 1;
            while(i >= 0 && i >= index) {
	            currentFig = allFigures.get(i);
	            allFigures.set(i + 1, currentFig);
	            --i;
            }
        } else {
            i = currentIndex + 1;
            while(i < allFigures.size() && i <= index) {
                currentFig = allFigures.get(i);
                allFigures.set(i - 1, currentFig);
                ++i;
            }
        }
        allFigures.set(index, f);
	    notifyModelObservers(f, DrawModelEvent.Type.DRAWING_CHANGED);
	}

	@Override
	public void removeAllFigures() {
        for(Figure f : allFigures) {
            notifyModelObservers(f, DrawModelEvent.Type.DRAWING_CLEARED);
            f.removeFigureListener(this);       //wenn dieser Schritt weggelassen wird, was geschieht?
        }
		allFigures.clear();
    }

	// StdDrawModel is also a Listener (Observer) of Figures.
    // Because it is between StdDrawView and Figure, it is a so called 'Mediator'.
    @Override
    public void figureChanged(FigureEvent e) {
        notifyModelObservers(e.getFigure(), DrawModelEvent.Type.FIGURE_CHANGED);
    }
}
