package jdraw.grid;

import jdraw.framework.DrawGrid;

import java.awt.*;

/**
 * Created by degonas on 24.10.2018.
 */
public class FixedGrid implements DrawGrid {

    private int stepX;
    private int stepY;

    public FixedGrid(int stepX, int stepY) {
        this.stepX = Math.max(1, stepX);
        this.stepY = Math.max(1, stepY);
    }

    @Override
    public Point constrainPoint(Point p) {
        int x = ((p.x + stepX/2)/stepX) * stepX;
        int y = ((p.y + stepY/2)/stepY) * stepY;
        System.out.println("constrainPoint():" + x + " : " + y);
        return new Point(x, y);
    }

    @Override
    public int getStepX(boolean right) {
        return stepX;
    }

    @Override
    public int getStepY(boolean down) {
        return stepY;
    }

    @Override
    public void activate() {
        System.out.println("SimpleGrid:activate()");
    }

    @Override
    public void deactivate() {
        System.out.println("SimpleGrid:deactivate()");
    }

    @Override
    public void mouseDown() {
        System.out.println("SimpleGrid:mouseDown()");
    }

    @Override
    public void mouseUp() {
        System.out.println("SimpleGrid:mouseUp()");
    }

}
