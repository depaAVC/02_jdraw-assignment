package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by degonas on 03.10.2018.
 */
public class LineTool extends AbstractDrawTool {

    /**
     * Create a new rectangle tool for the given context.
     * @param context a context to use this tool in.
     */
    public LineTool(DrawContext context) {
        super(context, "Line");
    }

    // a factory method, using inheritance.
    @Override
    public Figure getNewFigure(int x, int y) {
        Line line = new Line(x, y, x, y);
        registerAddCommand(line);
        return line;
    }
}
