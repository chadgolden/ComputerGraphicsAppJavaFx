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
            //new Dot(parentComponent, x, y, color, scale);
            new Dot(componentOptions, x, y);
            if (d <= 0) { // Choose E.
                x += 1;
                d+= incrementEast;
            }
            else { // Choose NE.
                x += 1;
                y += 1;
                d += incrementNorthEast;
            }
        } while (x <= x1);
    }

}