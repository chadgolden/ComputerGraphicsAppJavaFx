package com.chadgolden.drawing;

import com.chadgolden.util.Edge;
import com.chadgolden.util.EdgeTable;

/**
 * Created by chad on 2/17/15.
 */
public class Polygon extends Shape {

    /** An array of Dot objects expressed at vertices for this polygon. */
    private Dot[] vertices;

    public Polygon(boolean wantToFill, Dot... listOfDots) {
        this.vertices = listOfDots;
        draw();
        if (wantToFill) {
            fill();
        }
    }

    @Override
    public void draw() {
        for (int i = 0; i < vertices.length - 1; i++) {
            new Line(vertices[i], vertices[i + 1]);
        }
        new Line(vertices[vertices.length - 1], vertices[0]);
    }

    @Override
    public void midpointScan() {
        throw new UnsupportedOperationException("Polygon does not implement midpointScan().");
    }

    /**
     * Fills this polygon using raster scan line filling technique.
     */
    @Override
    public void fill() {
        // Initialize an EdgeTable of all edges for this polygon.
        EdgeTable initialTable = new EdgeTable(vertices, 0);

        // Create a global EdgeTable with all of the edges where the slope is not 0.
        EdgeTable global = new EdgeTable();
        for (Edge edge : initialTable) {
            // Value of infinity for an Edge object's slope indicates dy/dx == 0.
            if (edge.getSlope() != Double.NEGATIVE_INFINITY && edge.getSlope() != Double.POSITIVE_INFINITY) {
                global.add(edge);
            }
        }

        // Sort the global EdgeTable by minimum y-values, then x-values if the y-values tie.
        global.sortByYMinimumAndXValues();

        // Find the starting and ending scan lines.
        final int startScanLine = global.minimumYValue();
        final int endScanLine = global.maximumYValue();

        // Initialize parity to zero. (Even)
        int parity = 0;

        // Initialize an active EdgeTable where the edges' y-minimum values are equal to starting scan line.
        // Move from global EdgeTable to active EdgeTable.
        EdgeTable active = new EdgeTable();
        EdgeTable delete = new EdgeTable(); // To collect Edge objects for deletion within global.
        for (com.chadgolden.util.Edge edge : global) {
            if (edge.getYMin() == startScanLine) {
                delete.add(edge); // Collect edges to delete from global.
                active.add(edge); // Add the edges to active.
            }
        }
        global.removeAll(delete);
        delete.clear(); // Clear for usage later.

        // Loop through each scan line.
        for (int scanLine = startScanLine; scanLine < endScanLine; scanLine++) {
            /*
               Move edges from the global EdgeTable to the active EdgeTable
               where globals' y-minimum matches the scan line.
             */
            for (Edge edge : global) {
                if (edge.getYMin() == scanLine) {
                    delete.add(edge);
                    active.add(edge);
                }
            }
            global.removeAll(delete);
            delete.clear();

            if (!active.isEmpty()) {
                // Sort the active EdgeTable by x-values in increasing order.
                active.sortByXValues();

                // Loop through each pixel for the current scan line.
                for (int x = 0; x < 100; x++) {
                    // Check the x-values for the edges in the active EdgeTable.
                    // If an x-value matches the current position of x, then increase parity by 1.
                    for (Edge activeEdge : active) {
                        int edgeValue = (int)Math.ceil(activeEdge.getXValue());
                        if (edgeValue == x && activeEdge.getYMin() != scanLine) {
                            parity++;
                        }
                    }
                    // If parity is odd, draw a pixel at x = current x-position and y = scan line.
                    if (parity % 2 != 0) {
                        new Dot(x, scanLine);
                    }
                }

                // Delete finished edges from the active EdgeTable.
                for (Edge edge : active) {
                    if (edge.getYMax() == scanLine) {
                        delete.add(edge);
                    }
                }
                active.removeAll(delete);
                delete.clear();

                // Update the x-values in the active EdgeTable by x = x + 1/m.
                active.updateXValues(0);
            }
        }
    }

}
