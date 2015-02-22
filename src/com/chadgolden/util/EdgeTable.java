package com.chadgolden.util;

import com.chadgolden.drawing.Dot;
import com.chadgolden.drawing.Line;
import com.chadgolden.drawing.Polygon;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Chad on 2/21/2015.
 */
public class EdgeTable extends ArrayList<Edge> {

    public EdgeTable() {
    }

    /** Constructs an EdgeTable from an array of Dots. */
    public EdgeTable(Dot[] vertices) {
        Line[] edges = new Line[vertices.length];
        for (int i = 0; i < vertices.length - 1; i++) {
            edges[i] = new Line(
                    new Dot(vertices[i].getX(), vertices[i].getY(), false),
                    new Dot(vertices[i + 1].getX(), vertices[i + 1].getY(), false),
                    false
            );
        }
        edges[edges.length - 1] = new Line(
                new Dot(vertices[vertices.length - 1].getX(), vertices[vertices.length - 1].getY(), false),
                new Dot(vertices[0].getX(), vertices[0].getY(), false)
        );
        for (Line line : edges) {
            this.add(new Edge(line.yMin(), line.yMax(), line.getXAssociatedWithYMin(), line.slopeInverse()));
        }
    }

    /** Construct an EdgeTable from an array of Dot objects. */
    public EdgeTable(Dot[] vertices, int value) {
        Line[] edges = new Line[vertices.length];
        for (int i = 0; i < vertices.length - 1; i++) {
            edges[i] = new Line(
                    new Dot(vertices[i].getX(), vertices[i].getY(), false),
                    new Dot(vertices[i + 1].getX(), vertices[i + 1].getY(), false),
                    false
            );
        }
        edges[edges.length - 1] = new Line(
                new Dot(vertices[vertices.length - 1].getX(), vertices[vertices.length - 1].getY(), false),
                new Dot(vertices[0].getX(), vertices[0].getY(), false)
        );
        for (Line line : edges) {
            this.add(new Edge(line));
        }
    }

    /** Constructs an EdgeTable from an array of Line objects. */
    public EdgeTable(Line[] edges) {
        for (Line line : edges) {
            this.add(new Edge(line.yMin(), line.yMax(), line.getXAssociatedWithYMin(), line.slopeInverse()));
        }
    }

    /** Constructs an EdgeTable from an array of Line objects. */
    public EdgeTable(Line[] edges, int test) {
        for (Line line : edges) {
            this.add(new Edge(line));
        }
    }

    /** Sorts this EdgeTable by y-minimum values, then by x-values all in ascending order. */
    public void sortByYMinimumAndXValues() {
        Collections.sort(this, new ComparatorEdgeYMinXValue());
    }

    /** Sorts this EdgeTable by x-values in ascending order. */
    public void sortByXValues() {
        Collections.sort(this, new ComparatorEdgeXValue());
    }

    /**
     * @return The maximum y-value in this table.
     */
    public int maximumYValue() {
        if (this.isEmpty()) {
            return -1;
        }
        int max = get(0).getYMax();
        for (Edge edge : this) {
            if (max < edge.getYMax()) {
                max = edge.getYMax();
            }
        }
        return max;
    }

    /**
     * @return The minimum y-value in this table.
     */
    public int minimumYValue() {
        if (this.isEmpty()) {
            return -1;
        }
        int min = get(0).getYMin();
        for (Edge edge : this) {
            if (min > edge.getYMin()) {
                min = edge.getYMin();
            }
        }
        return min;
    }

    /**
     * Updates the x-values in this table using the model:
     * <code>xValue += dx/dy</code>
     */
    public void updateXValues() {
        if (isEmpty()) return;
        for (Edge edge : this) {
            edge.update(0);
        }
    }

    /**
     * Updates the x-values in this table using the model:
     * <code>xValue += dx/dy</code>
     */
    public void updateXValues(int value) {
        if (isEmpty()) return;
        for (Edge edge : this) {
            edge.update(value);
        }
    }

    /**
     * Prints a line for each Edge's toString() in this EdgeTable.
     */
    public void print() {
        for (Edge edge : this) {
            System.out.println(edge);
        }
    }

    /**
     * @param table EdgeTable that is being compared.
     * @return If this EdgeTable is equal to the passed EdgeTable.
     */
    public boolean equals(EdgeTable table) {
        if (this == table) {
            return true;
        }

        if (this.size() != table.size()) {
            return false;
        }

        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(table.get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * For testing Edge logic...
     * @param args
     */
    public static void main(String... args) {

        /*ComponentOptions instance = ComponentOptions.getInstance();
        instance.setColor(Color.BLACK);
        instance.setScale(0);
        instance.setParentComponent(new Canvas());

        //Hexagon
        Dot dot1 = new Dot(10, 5, false);
        Dot dot2 = new Dot(20, 5, false);
        Dot dot3 = new Dot(25, 15, false);
        Dot dot4 = new Dot(20, 25, false);
        Dot dot5 = new Dot(10, 25, false);
        Dot dot6 = new Dot(5, 15, false);

        Line[] lines = new Line[] {
                new Line(dot1, dot2, false),
                new Line(dot2, dot3, false),
                new Line(dot3, dot4, false),
                new Line(dot4, dot5, false),
                new Line(dot5, dot6, false),
                new Line(dot6, dot1, false),
        };

        EdgeTable table3 = new EdgeTable(lines, 0);

        table3.print();

        table3.print();

        System.out.println();
        System.out.println();

        EdgeTable table4 = new EdgeTable(lines, 0);*/


    }



}
