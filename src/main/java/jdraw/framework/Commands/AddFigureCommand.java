package jdraw.framework.Commands;

import jdraw.framework.DrawCommand;
import jdraw.framework.DrawModel;
import jdraw.framework.Figure;

import java.util.ArrayList;

/**
 * Created by degonas on 15.12.2018.
 */
public class AddFigureCommand implements DrawCommand {

    /** The model from which to remove the figure. */
    private final DrawModel model;
    /** The figure to remove. */
    private final Figure figure;
    /** index of the figure in the model. */
    private int index;

    public AddFigureCommand(DrawModel model, Figure figure) {
        this.model = model;
        this.figure = figure;
        index = FindIndexOf(figure);
    }

    private int FindIndexOf(Figure figure){
        ArrayList<Figure> a = (ArrayList<Figure>) model.getFigures();
       return a.size();
    }

    @Override
    public void redo() {
        model.addFigure(figure);
        model.setFigureIndex(figure, index);
    }

    @Override
    public void undo() {
        model.removeFigure(figure);
    }
}
