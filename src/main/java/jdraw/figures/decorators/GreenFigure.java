package jdraw.figures.decorators;

import jdraw.figures.AbstractObservableFigure;
import jdraw.framework.Figure;

import java.awt.*;

/**
 * Created by degonas on 20.11.2018.
 */
public class GreenFigure extends AbstractDecorator{


    public GreenFigure(Figure innerFig) {
        super(innerFig);
    }


    @Override
    public void draw(Graphics g) {
        //immutable Graphic (--> decorator which ignores certain colors in setColor).
        System.out.println("drawing green decoration.");
        getInner().draw(g);
        g.setColor(Color.GREEN);
        //Todo: how to change draw color so that it works?
    }

}
