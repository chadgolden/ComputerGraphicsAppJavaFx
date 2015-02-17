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
        return x - 50;
    }

    public static int CanvasToCartesianY(int y) {
        return 50 - y;
    }

}
