package jdraw.figures.handlesStatePattern;

import jdraw.figures.AbstractObservableFigure;

/**
 * Created by degonas on 19.12.2018.
 */
public abstract class AbstractRectengularFigure extends AbstractObservableFigure {

    public AbstractRectengularFigure(AbstractRectengularFigure f){
        super(f);
    }

    //required bcs as soon as copy constructor got introduced, there was no implicit
    // default constructor left.
    public AbstractRectengularFigure(){
        //empty bcs all fields are initialized before constructor call.
    }

    //Marker class. Todo: refractor shared methods from Rect and Ovel into this one.
}
