package com.chadgolden.util;

/**
 * Created by Chad on 2/16/2015.
 */
public class CartesianGrid {

    /**
     * No instantiation.
     */
    private CartesianGrid() { }

    public static int CanvasToCartesianX(int x) {
        return x;
    }

    public static int CanvasToCartesianY(int y) {
        return -1 * y;
    }

}
