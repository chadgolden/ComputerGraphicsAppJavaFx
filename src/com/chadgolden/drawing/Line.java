package com.chadgolden.drawing;

/**
 * Created by Chad on 2/18/2015.
 */
public class Line extends Component {

    private Dot start;
    private Dot end;

    public Line(Dot start, Dot end) {
        this.start = start;
        this.end = end;
        draw();
    }

    /** Constructs a line object from two Dot objects. Optionally draws the line. */
    public Line(Dot start, Dot end, boolean wantToDraw) {
        this.start = start;
        this.end = end;
        if (wantToDraw) {
            draw();
        }
    }

    /**
     * Bresenham's Line Algorithm implementation. Uses error calculation to achieve slopes not within 0 <= m < 1.
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

        int error = ((dx > dy) ? dx : -dy)/2;
        int error2;

        for(;;) {
            new Dot(x0, y0);
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
     * @return The starting end point.
     */
    public Dot getStart() {
        return start;
    }

    /**
     * @return The ending end point.
     */
    public Dot getEnd() {
        return end;
    }

    public int yMin() {
        if (start.getY() <= end.getY()) {
            return start.getY();
        } else {
            return end.getY();
        }
    }

    public int xMin() {
        if (start.getX() <= end.getX()) {
            return start.getX();
        } else {
            return end.getX();
        }
    }

    public int yMax() {
        if (start.getY() >= end.getY()) {
            return start.getY();
        } else {
            return end.getY();
        }
    }

    public int xMax() {
        if (start.getX() >= end.getX()) {
            return start.getX();
        } else {
            return end.getX();
        }
    }

    public int getXAssociatedWithYMin() {
        if (start.getY() == yMin()) {
            return start.getX();
        } else {
            return end.getX();
        }
    }

    public int dx() {
        return end.getX() - start.getX();
    }

    public int dy() {
        return end.getY() - start.getY();
    }

    /**
     * @return 1 for a positive slope, 0 for a zero slope, and -1 for a negative slope.
     */
    public int signOfSlope() {
        if (getSlope() >= 1.0) {
            return 1;
        } else if (getSlope() == 0.0) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * @return The slope for the line.
     */
    public double getSlope() {
        int dx = end.getX() - start.getX();
        int dy = end.getY() - start.getY();
        if (dx == 0) { return 0; }
        return (double)dy/dx;
    }

    /**
     * @return dx/dy. e.g.: 1/m.
     */
    public double slopeInverse() {
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();
        return dx/dy;
    }
}
