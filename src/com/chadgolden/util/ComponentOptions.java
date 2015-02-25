package com.chadgolden.util;

import com.chadgolden.drawing.Dot;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Allows for single global access to the canvas, color, frame buffer, and mouse information.
 * Created by Chad on 2/16/2015.
 */
public class ComponentOptions {

    /** Single instance of <code>ComponentOptions</code>. */
    private static ComponentOptions singletonInstance = null;

    private Color[][] dotMatrix;

    private Canvas parentComponent;
    private Color color;
    private double scale;
    private int offsetX; // Mouse offset for x.
    private int offsetY; // Mouse offset for y.

    private ComponentOptions() {
    }

    public static synchronized ComponentOptions getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new ComponentOptions();
            singletonInstance.initializeDotMatrix();
        }
        return singletonInstance;
    }

    public void initializeDotMatrix() {
        dotMatrix = new Color[100][100];
        for (int i = 0; i < dotMatrix.length; i++) {
            for (int j = 0; j < dotMatrix[i].length; j++) {
                dotMatrix[i][j] = Color.BLACK;
            }
        }
    }

    public Canvas getParentComponent() {
        return parentComponent;
    }

    public void setParentComponent(Canvas parentComponent) {
        this.parentComponent = parentComponent;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public int getOffsetX() { return offsetX; }

    public void setOffsetX(int x) { offsetX = x; }

    public int getOffsetY() { return offsetY; }

    public void setOffsetY(int y) { offsetY = y; }

    public Color[][] getDotMatrix() { return dotMatrix; }

    /**
     * Returns the Color object at the x and y coordinates of a point in the dot matrix/frame buffer.
     * @param x
     * @param y
     * @return The specified Color from the dot matrix.
     */
    public Color getColorFromDotMatrix(int x, int y) {
        if (x < 0) {
            x = 0;
        } else if (x >= 100) {
            x = 99;
        } else if (y < 0) {
            y = 0;
        } else if (y >= 100) {
            y = 99;
        }
        return dotMatrix[y][x];
    }

    /**
     * Update the dot matrix with the current color at the specified point.
     * @param dot
     */
    public void updateDotMatrix(Dot dot) {
        if (dot.getX() < 0 || dot.getX() >= 100 || dot.getY() < 0 || dot.getY() >= 100) {
            return;
        }
        dotMatrix[dot.getY()][dot.getX()] = this.color;
    }

    /**
     * Prints a matrix of 0s and 1s to the console that represent the current frame buffer.
     */
    public void printBinaryDotMatrix() {
        for (int i = 0; i < dotMatrix.length; i++) {
            for (int j = 0; j < dotMatrix[i].length; j++) {
                if (dotMatrix[i][j].equals(Color.BLACK)) {
                    System.out.print("0 ");
                }
                else {
                    System.out.print("1 ");
                }
            }
            System.out.println();
        }
    }

    /**
     * For testing.
     * @param args
     */
    public static void main(String[] args) {
        getInstance().printBinaryDotMatrix();
    }

}
