package jdraw.figures.decorators;

import jdraw.framework.Figure;

import java.awt.*;

/**
 * Created by degonas on 19.12.2018.
 */
public class BorderDecorator extends AbstractDecorator {

    private static final int BORDER_OFFSET = 5;

    public BorderDecorator(Figure innerFig){
        super(innerFig);
    }


    @Override
    public Rectangle getBounds() {
        Rectangle r = getInner().getBounds();
        r.grow(BORDER_OFFSET, BORDER_OFFSET);
        return r;
    }

    @Override
    public void draw(Graphics g) {
        getInner().draw(g);
        Rectangle b = getBounds();  //from BorderDecorator

        g.setColor(Color.white);
        g.drawLine(b.x, b.y, b.x, b.y + b.height);
        g.drawLine(b.x, b.y, b.x + b.width, b.y);
        g.setColor(Color.gray);
        g.drawLine(b.x + b.width, b.y, b.x + b.width, b.y + b.height);
        g.drawLine(b.x, b.y + b.height, b.x + b.width, b.y + b.height);
    }
}
