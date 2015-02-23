package com.chadgolden.drawing;

import com.chadgolden.util.ComponentOptions;
import com.chadgolden.util.Edge;
import com.chadgolden.util.EdgeTable;
import javafx.scene.paint.Color;

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

    public Polygon(boolean wantToFill, double sizeCoefficient, Dot... listOfDots) {
        this.vertices = listOfDots;
        scale(sizeCoefficient);
        draw();
        if (wantToFill) {
            fill();
        }
    }

    public Polygon(double sizeCoefficient, int numberOfFillDots, Dot... listOfDots) {
        this.vertices = listOfDots;
        scale(sizeCoefficient);
        draw();
        fill(numberOfFillDots);
    }

    @Override
    public void draw() {
        mouseAdjust();
        scaleOffsetAdjust();
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
                for (int x = -100; x < 200; x++) {
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

    /**
     * Fills this polygon using raster scan line filling technique, but only to a specified number of dots.
     */
    public void fill(int numberOfDotsToFill) {

        int numberOfDotsFilled = 0;

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
                for (int x = -100; x < 200; x++) {
                    if (numberOfDotsFilled >= numberOfDotsToFill) {
                        return;
                    }
                    // Check the x-values for the edges in the active EdgeTable.
                    // If an x-value matches the current position of x, then increase parity by 1.
                    //boolean shouldSkip = false;
                    for (Edge activeEdge : active) {
                        int edgeValue = (int)Math.ceil(activeEdge.getXValue());
                        if (edgeValue == x && activeEdge.getYMin() != scanLine) {
                            parity++;
                        }
                    }

                    // If parity is odd, draw a pixel at x = current x-position and y = scan line.
                    if (parity % 2 != 0) {
                        numberOfDotsFilled++;
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

    private void mouseAdjust() {
        int mouseX = ComponentOptions.getInstance().getOffsetX();
        int mouseY = ComponentOptions.getInstance().getOffsetY();
        //System.out.println("x = " + mouseX + " y = " + mouseY);
        for (int i = 0; i < vertices.length; i++) {
            vertices[i].setX(vertices[i].getX() + mouseX);
            vertices[i].setY(vertices[i].getY() + mouseY);
        }
    }

    private void scaleOffsetAdjust() {
        double maxOffsetX = (maximumX() - minimumX()) / 2;
        double maxOffsetY = (maximumY() - minimumY()) / 2;
        for (Dot dot : vertices) {
            dot.setX(dot.getX() - (int)maxOffsetX);
            dot.setY(dot.getY() - (int)maxOffsetY);
        }
    }

    private void scale(double sizeCoefficient) {
        for (Dot dot : vertices) {
            dot.setX((int)(dot.getX() * sizeCoefficient));
            dot.setY((int)(dot.getY() * sizeCoefficient));
        }
    }

    private int maximumX() {
        int max = vertices[0].getX();
        for (Dot dot : vertices) {
            if (dot.getX() > max) {
                max = dot.getX();
            }
        }
        return max;
    }

    private int maximumY() {
        int max = vertices[0].getY();
        for (Dot dot : vertices) {
            if (dot.getY() > max) {
                max = dot.getY();
            }
        }
        return max;
    }

    private int minimumX() {
        int min = vertices[0].getX();
        for (Dot dot : vertices) {
            if (dot.getX() < min) {
                min = dot.getX();
            }
        }
        return min;
    }

    private int minimumY() {
        int min = vertices[0].getY();
        for (Dot dot : vertices) {
            if (dot.getY() < min) {
                min = dot.getY();
            }
        }
        return min;
    }



}
