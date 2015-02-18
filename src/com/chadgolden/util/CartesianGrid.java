package com.chadgolden.util;

/**
 * Created by Chad on 2/16/2015.
 */
public class CartesianGrid {

    /**
     * No instantiation.
     */
    private CartesianGrid() { }

    public static int canvasToCartesianX(int x) {
        return x - 50;
    }

    public static int canvasToCartesianY(int y) {
        return 50 - y;
    }

    public static int cartesianToCanvasX(int x) {
        if (x < -50) {
            throw new UnsupportedOperationException("Coordinate must be at least -50.");
        }
        else if (x >= -50 && x < 0) {
            return x + 50;
        }
        else if (x == 0) {
            return 0 + 50;
        }
        else if (x > 0 && x <= 50) {
            return x + 50;
        }
        else {
            throw new UnsupportedOperationException("Coordinate must be between -50 and 50.");
        }
    }

    public static int cartesianToCanvasY(int y) {
        return cartesianToCanvasX(-y);
    }

}
