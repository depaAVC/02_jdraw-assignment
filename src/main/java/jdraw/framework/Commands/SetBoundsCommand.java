package jdraw.framework.Commands;

import jdraw.figures.Rect;
import jdraw.framework.DrawCommand;
import jdraw.framework.Figure;

import java.awt.*;

/**
 * Created by degonas on 16.12.2018.
 */
public class SetBoundsCommand implements DrawCommand {

    private Figure receiver;
    private Rectangle previousBounds;
    private Rectangle newBounds;

    private Point prevOrigin, prevCorner;
    private Point newOrigin, newCorner;


    public SetBoundsCommand(Figure receiver, Rectangle previousBounds, Rectangle newBounds){
        this.receiver = receiver;
        //this.previousBounds = previousBounds;
        //this.newBounds = newBounds;

        prevOrigin = new Point(previousBounds.x, previousBounds.y);
        prevCorner = new Point(previousBounds.x + previousBounds.width, previousBounds.y + previousBounds.height);

        newOrigin = new Point(newBounds.x, newBounds.y);
        newCorner = new Point(newBounds.x + newBounds.width, newBounds.y + newBounds.height);
    }

    @Override
    public void redo() {
        receiver.setBounds(newOrigin, newCorner);
    }

    @Override
    public void undo() {
        receiver.setBounds(prevOrigin, prevCorner);
    }
}
