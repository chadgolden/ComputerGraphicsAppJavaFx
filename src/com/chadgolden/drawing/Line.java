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

        int octant = getOctant();

        System.out.println(octant);

        if (octant == 0) {
            // do nothing
        } else if (octant == 1) {
            this.x1 = endDot.getY();
            this.y1 = endDot.getX();
        } else if (octant == 2) {
            this.x1 = -endDot.getY();
            this.y1 = endDot.getX();
        } else if (octant == 3) {
            this.x1 = -endDot.getX();
            this.y1 = endDot.getY();
        } else if (octant == 4) {
            this.x1 = -endDot.getX();
            this.y1 = -endDot.getY();
        } else if (octant == 5) {
            this.x1 = -endDot.getY();
            this.y1 = -endDot.getX();
        } else if (octant == 6) {
            this.x1 = endDot.getY();
            this.y1 = -endDot.getX();
        } else if (octant == 7) {
            this.x1 = endDot.getX();
            this.y1 = -endDot.getY();
        }
//        if (startDot.getX() < 0 && getSlope() < 1.0) {
//            this.x0 = startDot.getX();
//            this.y0 = startDot.getY();
//            this.x1 = endDot.getX();
//            this.y1 = endDot.getY();
//        } else if (startDot.getX() > 0 && getSlope() < 1.0) {
//            this.x0 = endDot.getX();
//            this.y0 = endDot.getY();
//            this.x1 = startDot.getX();
//            this.y1 = startDot.getY();
//        } else if (startDot.getX() < 0 && getSlope() >= 1.0) {
//            this.x0 = endDot.getX();
//            this.y0 = endDot.getY();
//            this.x1 = startDot.getX();
//            this.y1 = startDot.getY();
//        }
//        else {
//            this.x0 = startDot.getY();
//            this.y0 = startDot.getX();
//            this.x1 = endDot.getX();
//            this.y1 = endDot.getY();
//            System.out.println("Hi");
//        }
//        draw();
        midpointScan(octant);
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
        //System.out.printf("x = %d   y = %d\n", dx, dy);
        if (dx == 0) { return 0.0; }
        //System.out.printf("slope = %f\n", (double)dy/dx);
        return (double)dy/dx;
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
            switchToOctantFrom(getOctant(), x, y);
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

    public void midpointScan(int octant) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int d = 2 * dy - dx;
        int incrementEast = 2 * dy;
        int incrementNorthEast = 2 * (dy - dx);
        int x = x0;
        int y = y0;
        System.out.printf("x=%d y=%d x1=%d\n", x, y, x1);
        do {
            switchToOctantFrom(octant, x, y);
            //System.out.println("Hi");
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

    public void midpointScan(int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int d = 2 * dy - dx;
        writePixel(x0, y0);
        int y = y0;

        for (int x = x0; x <= x1; x++) {
            if (d > 0) {
                y = y + 1;
                System.out.println("Octant: " + getOctant());
                if (getOctant() == 0) {
                    writePixel(x, y);
                } else if (getOctant() == 1) {
                    writePixel(y, x);
                }

                d = d + (2 * dy - 2 * dx);
            } else {
                if (getOctant() == 0) {
                    writePixel(x, y);
                } else if (getOctant() == 1) {
                    writePixel(y, x);
                }
                d = d + (2 * dy);
            }
        }
    }

    public int getOctant() {
        if (x1 > 0 && getSlope() >= 0.0 && getSlope() < 1.0) {
            return 0;
        } else if (x1 > 0 && getSlope() >= 1.0) {
            return 1;
        } else if (x1 <= 0 && getSlope() <= -1.0) {
            return 2;
        } else if (x1 <= 0 && getSlope() > -1.0 && getSlope() <= 0.0) {
            return 3;
        } else if (x1 <= 0 && getSlope() >= 0.0 && getSlope() < 1.0) {
            return 4;
        } else if (x1 <= 0 && getSlope() >= 1.0) {
            return 5;
        } else if (x1 > 0 && getSlope() <= -1.0) {
            return 6;
        } else if (x1 > 0 && getSlope() > -1.0 && getSlope() <= 0.0) {
            return 7;
        }
        return -1;
    }

    public void switchToOctantFrom(int octant, int x, int y) {
        switch (octant) {
            case 0:
                new Dot(componentOptions, x, y);
                break;
            case 1:
                new Dot(componentOptions, y, x);
                break;
            case 2:
                new Dot(componentOptions, -y, x);
                break;
            case 3:
                new Dot(componentOptions, -x, y);
                break;
            case 4:
                new Dot(componentOptions, -x, -y);
                break;
            case 5:
                new Dot(componentOptions, -y, -x);
                break;
            case 6:
                new Dot(componentOptions, y, -x);
                break;
            case 7:
                new Dot(componentOptions, x, -y);
                break;
        }
    }

}