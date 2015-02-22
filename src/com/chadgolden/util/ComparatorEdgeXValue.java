package com.chadgolden.util;

import java.util.Comparator;

/**
 * Compares by the x-values of two Edge objects.
 * Created by Chad on 2/21/2015.
 */
public class ComparatorEdgeXValue implements Comparator<Edge> {

    @Override
    public int compare(Edge edge1, Edge edge2) {
        return Double.compare(edge1.getXValue(), edge2.getXValue());
    }

}
