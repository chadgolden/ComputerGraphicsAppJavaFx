package com.chadgolden.drawing;

import com.chadgolden.util.ComponentOptions;

/**
 * Created by Chad on 2/18/2015.
 */
public class Line extends Component {

    private Dot start;
    private Dot end;

    public Line(ComponentOptions componentOptions, Dot start, Dot end) {
        super(componentOptions);
        this.start = start;
        this.end = end;
        draw();
    }

    /**
     * Bresenham's Line Algorithm that uses error calculation for x and y instead of reflections.
     */
    @Override
    public void midpointScan() {
        int x0 = start.getX();
        int y0 = start.getY();
        int x1 = end.getX();
        int y1 = end.getY();

        int dx = Math.abs(x1 - x0);
        int sx = (x0 < x1) ? 1 : -1;

        int dy = Math.abs(y1 - y0);
        int sy = (y0 < y1) ? 1 : -1;

        int error = (dx>dy ? dx : -dy)/2;
        int error2;

        for(;;) {
            new Dot(componentOptions, x0, y0);
            if (x0 == x1 && y0 == y1) {
                break;
            }
            error2 = error;
            if (error2 >-dx)
            {
                error -= dy;
                x0 += sx;
            }
            if (error2 < dy) {
                error += dx;
                y0 += sy;
            }
        }
    }

    /**
     * @return The slope for the line.
     */
    public double getSlope() {
        int dx = end.getX() - start.getX();
        int dy = end.getY() - start.getY();
        if (dx == 0) { return 0.0; }
        return (double)dy/dx;
    }
}
