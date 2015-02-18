package com.chadgolden.drawing;

import com.chadgolden.util.ComponentOptions;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Line extends Component {

    private int x0;
    private int x1;
    private int y0;
    private int y1;

    public Line(ComponentOptions componentOptions, Dot startDot, Dot endDot) {
        super(componentOptions);
        this.x0 = startDot.getX();
        this.y0 = startDot.getY();
        this.x1 = endDot.getX();
        this.y1 = endDot.getY();
        double slope = getSlope();
        System.out.println(getSlope());
        if (startDot.getX() < 0 && getSlope() < 1.0) {
            this.x0 = startDot.getX();
            this.y0 = startDot.getY();
            this.x1 = endDot.getX();
            this.y1 = endDot.getY();
        } else if (startDot.getX() > 0 && getSlope() < 1.0) {
            this.x0 = endDot.getX();
            this.y0 = endDot.getY();
            this.x1 = startDot.getX();
            this.y1 = startDot.getY();
        } else if (startDot.getX() < 0 && getSlope() >= 1.0) {
            this.x0 = endDot.getX();
            this.y0 = endDot.getY();
            this.x1 = startDot.getX();
            this.y1 = startDot.getY();
        }
        else {
            this.x0 = startDot.getY();
            this.y0 = startDot.getX();
            this.x1 = endDot.getX();
            this.y1 = endDot.getY();
            System.out.println("Hi");
        }
        draw();
    }

    public Line(ComponentOptions componentOptions, int x0, int y0, int x1, int y1) {
        super(componentOptions);
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
        draw();
    }

    public Line(Canvas parentComponent, int x0, int y0, int x1, int y1, Color color, int scale) {
        super(parentComponent, color, scale);
        this.x0 = x0;
        this.x1 = x1;
        this.y0 = y0;
        this.y1 = y1;
        draw();
    }

    public double getSlope(int x0, int x1, int y0, int y1) {
        int deltaX = x1 - x0;
        int deltaY = y1 - y0;

        System.out.printf("(%d - %d) / (%d - %d) = \n", x1, x0, y1, y0);

        if (deltaX == 0) return 0.0;

        int slope = deltaY / deltaX;
        return slope;
    }

    public double getSlope() {
        int dx = x1 - x0;
        int dy = y1 - y0;
        if (dx == 0) { return 0.0; }
        return dy/dx;
    }

    @Override
    public void midpointScan() {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int d = 2 * dy - dx;
        int incrementEast = 2 * dy;
        int incrementNorthEast = 2 * (dy - dx);
        int x = x0;
        int y = y0;
        do {
            // Needs cartesian style coordinates.
            if (getSlope() >= 1.0) {
                System.out.println("Hi loop");
                new Dot(componentOptions, y, x);
            } else {
                new Dot(componentOptions, x, y);
            }
            if (d <= 0) { // Choose E.
                x += 1;
                d += incrementEast;
            }
            else { // Choose NE.
                x += 1;
                y += 1;
                d += incrementNorthEast;
            }
        } while (x <= x1);
    }

}