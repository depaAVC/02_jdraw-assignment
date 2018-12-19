package jdraw.figures.decorators;

import jdraw.figures.Rect;
import jdraw.framework.Figure;

import java.awt.*;

/**
 * Created by degonas on 19.12.2018.
 */
public class BorderDecorator extends AbstractDecorator {

    public BorderDecorator(Figure innerFig){
        super(innerFig);
    }


    @Override
    public void draw(Graphics g) {
        getInner().draw(g);
        Rectangle b = getInner().getBounds();

        //XXX: symbolic implementation
        g.draw3DRect(b.x - (b.width / 10), b.y, b.width / 10, b.height, true);
        g.draw3DRect(b.x + b.width, b.y, b.width / 10, b.height, true);
        g.draw3DRect(b.x, b.y - (b.height / 10), b.width, b.height / 10, true);
        g.draw3DRect(b.x, b.y + + b.height, b.width, b.height / 10, true);
        //todo: How to draw border in AWT according to screenshot in pdf?
    }
}
