package com.chadgolden.util;

import java.util.Comparator;

/**
 * Compares the y-minimum values for two Edge objects and then compares
 * the x-values.
 * Created by Chad on 2/21/2015.
 */
public class ComparatorEdgeYMinXValue implements Comparator<Edge> {

    @Override
    public int compare(Edge o1, Edge o2) {
        int compareY = Integer.compare(o1.getYMin(), o2.getYMin());
        int compareX = Double.compare(o1.getXValue(), o2.getXValue());
        return (compareY == 0) ? compareX : compareY;
    }

}
