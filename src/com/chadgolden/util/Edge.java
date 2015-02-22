package com.chadgolden.util;

import com.chadgolden.drawing.Line;

/**
 * Created by Chad on 2/21/2015.
 */
public class Edge {

    private int yMin;
    private int yMax;
    private double xValue;
    private double slope;

    public Edge(int yMin, int yMax, int xValue, double slope) {
        this.yMin = yMin;
        this.yMax = yMax;
        this.xValue = xValue;
        this.slope = slope;
    }

    public Edge(Line line) {
        this.yMin = line.yMin();
        this.yMax = line.yMax();
        this.xValue = line.getXAssociatedWithYMin();
        this.slope = line.slopeInverse();
    }

    public int getYMin() {
        return yMin;
    }

    public int getYMax() {
        return yMax;
    }

    public double getXValue() {
        return xValue;
    }

    public double getSlope() {
        return slope;
    }

    /** Increment xValue by dx/dy. */
    public void update(int value) {
        xValue += slope;
    }

    @Override
    public String toString() {
        return String.format("%d %d %f %f", yMin, yMax, xValue, slope);
    }

    /**
     * @param edge
     * @return If this edge is equivalent to the compared edge.
     */
    public boolean equals(Edge edge) {
        if (this == edge) {
            return true;
        }
        if (this.yMin == edge.yMin
                && this.yMax == edge.yMax
                && this.xValue == edge.xValue
                && this.slope == edge.slope) {
            return true;
        }
        return false;
    }

}
