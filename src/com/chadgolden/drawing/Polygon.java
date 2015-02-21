package com.chadgolden.drawing;

import java.util.*;

/**
 * Created by chad on 2/17/15.
 */
public class Polygon extends Shape {

    private Dot[] vertices;

    public Polygon(Dot... listOfDots) {
//        listOfDots[0] = new Dot(componentOptions, 10, 10);
//        listOfDots[1] = new Dot(componentOptions, 35, 35);
//        listOfDots[2] = new Dot(componentOptions, 35, 9);
        this.vertices = listOfDots;
        draw();
    }

    public void draw() {
        for (int i = 0; i < vertices.length - 1; i++) {
            new Line(vertices[i], vertices[i + 1]);
        }
        new Line(vertices[vertices.length - 1], vertices[0]);
        scanLineWithParity();
    }

    @Override
    public void midpointScan() {
//        for (Dot vertex : vertices) {
//            vertex.draw();
//        }
    }

    @Override
    public void fill() {
        int polyCorners = vertices.length;
        int[] polyX = new int[vertices.length];
        int incr = 0;
        for (Dot dot : vertices) {
            polyX[incr++] = dot.getX();
        }
        incr = 0;
        int[] polyY = new int[vertices.length];

        for (Dot dot : vertices) {
            polyY[incr++] = dot.getY();
        }

        int IMAGE_TOP = 0;
        int IMAGE_BOTTOM = 100;
        int IMAGE_RIGHT = 100;
        int IMAGE_LEFT = 0;

        int MAX_POLY_CORNERS = vertices.length;
        int nodes;
        int[] nodeX = new int[MAX_POLY_CORNERS];
        int pixelY;
        int i, j, swap ;

//  Loop through the rows of the image.
        for (pixelY = IMAGE_TOP; pixelY < IMAGE_BOTTOM; pixelY++) {
            //  Build a list of nodes.
            nodes = 0; j = polyCorners - 1;
            for (i = 0; i < polyCorners; i++) {
                if (polyY[i]< pixelY && polyY[j]>= pixelY
                        ||  polyY[j]<pixelY && polyY[i]>= pixelY) { System.out.println("Reached.");
                    nodeX[nodes++]= polyX[i]+(pixelY-polyY[i])/(polyY[j]-polyY[i])
                            *(polyX[j]-polyX[i]); }
                j=i;}

            //  Sort the nodes, via a simple “Bubble” sort.
            i=0;
            while (i<nodes-1) {
                if (nodeX[i]>nodeX[i+1]) {
                    swap=nodeX[i]; nodeX[i]=nodeX[i+1]; nodeX[i+1]=swap; if (i == 1) i--; }
                else {
                    i++; }}

            //  Fill the pixels between node pairs.
            for (i=0; i<nodes; i+=2) {
                if   (nodeX[i  ]>=IMAGE_RIGHT) break;
                if   (nodeX[i+1]> IMAGE_LEFT ) {
                    if (nodeX[i  ]< IMAGE_LEFT ) nodeX[i  ]=IMAGE_LEFT ;
                    if (nodeX[i+1]> IMAGE_RIGHT) nodeX[i+1]=IMAGE_RIGHT;
                    for (j=nodeX[i]; j<nodeX[i+1]; j++) {new Dot(j , pixelY);} }}}
    }

    public ArrayList<EdgeTable> globalEdgeTable() {
        ArrayList<EdgeTable> edgeTable = new ArrayList<>();
        Line[] globalEdgeTable = new Line[vertices.length];
        // Initialize lines.
        for (int i = 0; i < vertices.length; i++) {
            if (i != vertices.length - 1) {
                globalEdgeTable[i] = new Line(vertices[i], vertices[i + 1]);
            } else {
                globalEdgeTable[i] = new Line(vertices[i], vertices[0]);
            }
        }
        // Sort by minimum Y of the edge.
        for (int i = 0; i < globalEdgeTable.length - 1; i++) {
            for (int j = 0; j < globalEdgeTable.length - 1 - i; j++) {
                if (globalEdgeTable[j + 1].yMin() < globalEdgeTable[j].yMin()) {
                    Line temp = globalEdgeTable[j];
                    globalEdgeTable[j] = globalEdgeTable[j + 1];
                    globalEdgeTable[j + 1] = temp;
                }
            }
        }

        // Sort by minimum X of the edge if the minimum Ys are the same.
        for (int i = 0; i < globalEdgeTable.length - 1; i++) {
            for (int j = 0; j < globalEdgeTable.length - 1 - i; j++) {
                if (globalEdgeTable[j + 1].yMin() == globalEdgeTable[j].yMin()) {
                    if (globalEdgeTable[j + 1].xMin() < globalEdgeTable[j].xMin()) {
                        Line temp = globalEdgeTable[j];
                        globalEdgeTable[j] = globalEdgeTable[j + 1];
                        globalEdgeTable[j + 1] = temp;
                    }
                }
            }
        }

        for (int i = 0; i < globalEdgeTable.length; i++) {
            edgeTable.add(i, new EdgeTable(
                    globalEdgeTable[i].yMin(),
                    globalEdgeTable[i].yMax(),
                    globalEdgeTable[i].xMin(),
                    globalEdgeTable[i].xMax(),
                    globalEdgeTable[i].getSlope()
            ));
        }

//        for (int i = 0; i < globalEdgeTable.length; i++) {
//            System.out.printf(
//                    "x0 = %d   y0 = %d   x1 = %d   y1 = %d\n",
//                    globalEdgeTable[i].getStart().getX(),
//                    globalEdgeTable[i].getStart().getY(),
//                    globalEdgeTable[i].getEnd().getX(),
//                    globalEdgeTable[i].getEnd().getY()
//            );
//        }

        return edgeTable;
    }

    void fill(int filler) {
        ArrayList<EdgeTable> globalEdgeTable = globalEdgeTable();
        ArrayList<EdgeTable> activeEdgeTable = new ArrayList<>();

        // Move from globalEdgeTable entry y to yMin from the first element in ET
        int y = globalEdgeTable.get(0).yMin;

        // Parity
        int parity = 0; // Even parity

        System.out.println(globalEdgeTable.size() +  " " + activeEdgeTable.size());

        while (!globalEdgeTable.isEmpty()) {

            // Move from ET entry y to AET whose yMin = y.
            for (int i = 0; i < globalEdgeTable.size(); i++) {
                if (globalEdgeTable.get(i).yMin == y) {
                    activeEdgeTable.add(i, globalEdgeTable.remove(i));
                    //System.out.println(globalEdgeTable.size() +  " " + activeEdgeTable.size());
                }
            }
            // Remove from AET those entries for which y = yMax
            for (int i = 0; i < activeEdgeTable.size(); i++) {
                if (activeEdgeTable.get(i).yMax == y) {
                    activeEdgeTable.remove(i);
                }
            }
            // Sort AET on xMin
            for (int i = 0; i < activeEdgeTable.size() - 1; i++) {
                for (int j = 0; j < globalEdgeTable.size() - 1 - i; j++) {
                    if (activeEdgeTable.size() > 2) {
                        if (activeEdgeTable.get(j + 1).yMin < activeEdgeTable.get(j).yMin) {
                            Collections.swap(activeEdgeTable, j, j + 1);
                        }
                    }

                }
            }



        }




    }

    class EdgeTable {

        int yMin;
        int yMax;
        int xMin;
        int xMax;
        double m;

        public EdgeTable(int yMin, int yMax, int xMin, int xMax, double slope) {
            this.yMin = yMin;
            this.yMax = yMax;
            this.xMin = xMin;
            this.xMax = xMax;
            this.m = 1/slope;
        }

    }

    class Edge {

        int yMin;
        int yMax;
        int xValue;
        double slope;

        public Edge(int yMin, int yMax, int xValue, double slope) {
            this.yMin = yMin;
            this.yMax = yMax;
            this.xValue = xValue;
            this.slope = slope;
        }

        @Override
        public String toString() {
            return String.format("%d %d %d %f", yMin, yMax, xValue, slope);
        }

    }

    class EdgeComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge edge1, Edge edge2) {

            int compareY = Integer.compare(edge1.yMin, edge2.yMin);
            int compareX = Integer.compare(edge1.xValue, edge2.xValue);

            return (compareY == 0) ? compareX : compareY;

        }
    }

    class EdgeComparatorXValue implements Comparator<Edge> {

        @Override
        public int compare(Edge edge1, Edge edge2) {

            int compareX = Integer.compare(edge1.xValue, edge2.xValue);

            return compareX;

        }
    }

    void scanLineFill() {

        ArrayList<Edge> edgeTable = new ArrayList<>();

        int[] xValues = new int[vertices.length];

        Line[] globalEdgeTable = new Line[vertices.length];
        // Initialize lines.
        for (int i = 0; i < vertices.length; i++) {
            if (i != vertices.length - 1) {
                globalEdgeTable[i] = new Line(vertices[i], vertices[i + 1]);
            } else {
                globalEdgeTable[i] = new Line(vertices[i], vertices[0]);
            }
        }
        // Sort by minimum Y of the edge.
        for (int i = 0; i < globalEdgeTable.length - 1; i++) {
            for (int j = 0; j < globalEdgeTable.length - 1 - i; j++) {
                if (globalEdgeTable[j + 1].yMin() < globalEdgeTable[j].yMin()) {
                    Line temp = globalEdgeTable[j];
                    globalEdgeTable[j] = globalEdgeTable[j + 1];
                    globalEdgeTable[j + 1] = temp;
                }
            }
        }

        // Sort by minimum X of the edge if the minimum Ys are the same.
        for (int i = 0; i < globalEdgeTable.length - 1; i++) {
            for (int j = 0; j < globalEdgeTable.length - 1 - i; j++) {
                if (globalEdgeTable[j + 1].yMin() == globalEdgeTable[j].yMin()) {
                    if (globalEdgeTable[j + 1].xMin() < globalEdgeTable[j].xMin()) {
                        Line temp = globalEdgeTable[j];
                        globalEdgeTable[j] = globalEdgeTable[j + 1];
                        globalEdgeTable[j + 1] = temp;
                    }
                }
            }
        }

        // Place the first edge with a slope that is not equal to zero in the edge table.
        for (int i = 0; i < globalEdgeTable.length; i++) {
            if (globalEdgeTable[i].getSlope() != 0) {
                Line current = globalEdgeTable[i];
                edgeTable.add(new Edge(current.yMin(), current.yMax(), current.getXAssociatedWithYMin(), current.getSlope()));
            }
        }

        // Find highest scan line.
        int maxScanLine = 0;
        for (int i = 0; i < edgeTable.size(); i++) {
            if (maxScanLine < edgeTable.get(i).yMax) {
                maxScanLine = edgeTable.get(i).yMax;
            }
        }

        ArrayList<Integer> list = new ArrayList<>();

        // Loop through starting scan line and ending scan line.
        for (int scanLine = edgeTable.get(0).yMin; scanLine < maxScanLine; scanLine++) {
            list.clear();
            for (int i = 0; i < edgeTable.size(); i++) {
                // Intersects the smaller vertex.
                if (scanLine == edgeTable.get(i).yMin) {
                    if (scanLine == edgeTable.get(i).yMax) {
                        list.add(edgeTable.get(i).xValue);
                        //System.out.println(list.get(list.size() - 1));
                    } else {
                        // do nothing
                    }
                }

                // The scan line intersects the bigger vertex.
                if (scanLine == edgeTable.get(i).yMax) {
                    list.add(edgeTable.get(i).xValue);
                }

                // The scan line intersects the edge.
                if (scanLine > edgeTable.get(i).yMin && scanLine < edgeTable.get(i).yMax) {
                    list.add(edgeTable.get(i).xValue);
                }

            }

            // Sort the list of x values in ascending order.
            Collections.sort(list);

            for (int i = 0; i < list.size(); i += 2) {
                if (i == list.size() - 1) {
                    break;
                }
                Dot start = new Dot(list.get(i), scanLine);
                Dot end = new Dot(list.get(i + 1), scanLine);
                new Line(start, end);
            }

        }



        // For every other edge, start at index 0 and increase the index to the global edge table once each time the
        // current edge's y value is greater than that of the edge at the current index in the global edge table.


    }

    void scanLineWithParity() {
        ArrayList<Edge> edgeTable = new ArrayList<>();

        Line[] globalEdgeTable = new Line[vertices.length];
        // Initialize lines.
        for (int i = 0; i < vertices.length; i++) {
            if (i != vertices.length - 1) {
                globalEdgeTable[i] = new Line(vertices[i], vertices[i + 1]);
            } else {
                globalEdgeTable[i] = new Line(vertices[i], vertices[0]);
            }
        }

        // Sort by minimum Y of the edge.
        for (int i = 0; i < globalEdgeTable.length - 1; i++) {
            for (int j = 0; j < globalEdgeTable.length - 1 - i; j++) {
                if (globalEdgeTable[j + 1].yMin() < globalEdgeTable[j].yMin()) {
                    Line temp = globalEdgeTable[j];
                    globalEdgeTable[j] = globalEdgeTable[j + 1];
                    globalEdgeTable[j + 1] = temp;
                }
            }
        }

        // Sort by minimum X of the edge if the minimum Ys are the same.
        for (int i = 0; i < globalEdgeTable.length - 1; i++) {
            for (int j = 0; j < globalEdgeTable.length - 1 - i; j++) {
                if (globalEdgeTable[j + 1].yMin() == globalEdgeTable[j].yMin()) {
                    if (globalEdgeTable[j + 1].xMin() < globalEdgeTable[j].xMin()) {
                        Line temp = globalEdgeTable[j];
                        globalEdgeTable[j] = globalEdgeTable[j + 1];
                        globalEdgeTable[j + 1] = temp;
                    }
                }
            }
        }

        // Further sort by x-value associated with the minimum y-value if the minimum-x's are the same.
        for (int i = 0; i < globalEdgeTable.length - 1; i++) {
            for (int j = 0; j < globalEdgeTable.length - 1 - i; j++) {
                if (globalEdgeTable[j + 1].yMin() == globalEdgeTable[j].yMin()) {
                    if (globalEdgeTable[j + 1].xMin() == globalEdgeTable[j].xMin()) {
                        if (globalEdgeTable[j + 1].getXAssociatedWithYMin() < globalEdgeTable[j].getXAssociatedWithYMin()) {
                            Line temp = globalEdgeTable[j];
                            globalEdgeTable[j] = globalEdgeTable[j + 1];
                            globalEdgeTable[j + 1] = temp;
                        }
                    }
                }
            }
        }

        // Place the first edge with a slope that is not equal to zero in the edge table.
        for (int i = 0; i < globalEdgeTable.length; i++) {
            if (globalEdgeTable[i].slopeInverse() != Double.NEGATIVE_INFINITY) {
                Line current = globalEdgeTable[i];
                edgeTable.add(
                        new Edge(current.yMin(), current.yMax(), current.getXAssociatedWithYMin(), current.slopeInverse())
                );
            }
        }

        Collections.sort(edgeTable, new EdgeComparator());

        // Find highest scan line.
        int maxScanLine = 0;
        for (int i = 0; i < edgeTable.size(); i++) {
            if (maxScanLine < edgeTable.get(i).yMax) {
                maxScanLine = edgeTable.get(i).yMax;
            }
        }

        // Find starting scan line.
        int scanLine = edgeTable.get(0).yMin;

        // Initial parity is even.
        int parity = 0;

        // Initialize active edge table where y-min is equal to starting scan line (10).
        ArrayList<Edge> activeEdges = new ArrayList<>();
        ArrayList<Edge> deletionList = new ArrayList<>();
        for (Edge edge : edgeTable) {
            if (edge.yMin == scanLine) {
                activeEdges.add(edge);
                deletionList.add(edge);
            }
        }
        edgeTable.removeAll(deletionList);

        System.out.println("State of active Edges before starting: ");
        for (int i = 0; i < activeEdges.size(); i++) {
           System.out.println(activeEdges.get(i));
        }
        System.out.println("End state before starting.");

        System.out.println("State of global Edges before starting: ");
        for (int i = 0; i < edgeTable.size(); i++) {
            System.out.println(edgeTable.get(i));
        }
        System.out.println("End state global edges before starting.");

        while (!activeEdges.isEmpty()) {



//            // Update x values to = x = x + 1/m.
//            for (int i = 0; i < activeEdges.size(); i++) {
//                activeEdges.get(i).xValue = activeEdges.get(i).xValue + (int)activeEdges.get(i).slope;
//            }
//
//            Collections.sort(activeEdges, new EdgeComparatorXValue());
            for (int i = 0; i < 100; i++) {

                boolean nextX = false;

                if (scanLine == 10) {
                    continue; // First Line taken care of by drawing portion.
                }

                for (int j = 0; j < activeEdges.size(); j++) {
                    Edge current = activeEdges.get(j);
                    if ((current.xValue) == i) {
                        System.out.println("Parity at x = " + activeEdges.get(j).xValue);
                        parity++;
                        nextX = !nextX;
                    }
                }

                if (nextX) {
                    continue;
                }

                if (parity % 2 == 0) {

                } else {
                    new Dot(i, scanLine);
                    System.out.println("Dot drawn at: " + i + ", " + scanLine);
                }

//                if (parity % 2 == 0) {
//                    // Even parity, do nothing.
//                } else {
//                    new Dot(i, scanLine);
//                    System.out.println("Dot drawn at: " + i + ", " + scanLine);
//                }


                //Dot draw = (parity % 2 != 0) ? new Dot(i, scanLine) : null;

            }

            // Update x values to = x = x + 1/m.
            for (int i = 0; i < activeEdges.size(); i++) {
                activeEdges.get(i).xValue = activeEdges.get(i).xValue + (int)Math.round(activeEdges.get(i).slope);;
            }

            Collections.sort(activeEdges, new EdgeComparatorXValue());


            for (int i = 0; i < activeEdges.size(); i++) {

            }

            ArrayList<Edge> edgesToDelete = new ArrayList<>();
            for (int i = 0; i < activeEdges.size(); i++) {
                if (activeEdges.get(i).yMax == scanLine) {
                    edgesToDelete.add(activeEdges.get(i));
                }
            }
            activeEdges.removeAll(edgesToDelete);

            System.out.println("State of activeEdges at scanLine = " + scanLine);
            for (int i = 0; i < activeEdges.size(); i++) {
                System.out.println("activeEdges.get(i) = " + activeEdges.get(i));
            }

//            for (int i = 0; i < activeEdges.size(); i++) {
//                activeEdges.get(i).xValue = activeEdges.get(i).xValue + (int)activeEdges.get(i).slope;
//            }

//            for (int i = 0; i < edgeTable.size(); i++) {
//                if (edgeTable.size() == 0) {
//                    break;
//                } else {
//                    Edge toRemove = edgeTable.remove(i);
//                    activeEdges.add(toRemove);
//                }
//
//            }

            edgesToDelete.clear();
            for (int i = 0; i < edgeTable.size(); i++) {
                if (edgeTable.get(i).yMin == scanLine) {
                    edgesToDelete.add(edgeTable.get(i));
                    activeEdges.add(edgeTable.get(i));
                }
            }
            edgeTable.removeAll(edgesToDelete);


            Collections.sort(activeEdges, new EdgeComparatorXValue());

            System.out.println("State of activeEdges at scanLine = " + scanLine + " after additions, revaluing and sorting.");
            for (int i = 0; i < activeEdges.size(); i++) {
                System.out.println("activeEdges.get(i) = " + activeEdges.get(i));
            }

            System.out.println("State of global at scanLine = " + scanLine);
            for (int i = 0; i < edgeTable.size(); i++) {
                System.out.println("global.get(i) = " + edgeTable.get(i));
            }

            // Debug halt
            if (scanLine++ == 19) {
               break;
            }

        }

        for (int i = 0; i < edgeTable.size(); i++) {
            //System.out.println(edgeTable.get(i));
        }

//        for (int currentScanLine = startScanLine; currentScanLine < maxScanLine; currentScanLine++) {
//
//            while (!activeEdges.isEmpty()) {
//                for (int i = 0; i < activeEdges.size(); i++) {
//                    if (parity % 2 == 0) {
//                        new Dot(activeEdges.get(i).xValue, currentScanLine);
//                    } else {
//
//                    }
//                }
//            }
//
//        }

    }
}
