package com.chadgolden.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 * Created by Chad on 2/16/2015.
 */
public class DynamicCanvas extends Canvas {

    private double scale;

    public DynamicCanvas(double scale) {
        this.scale = scale;
        fillCanvasBackground(null);
        drawCanvasGridLines(null);
    }

    private void fillCanvasBackground(Color fillColor) {
        if (fillColor == null) { fillColor = Color.BLACK; }
        this.getGraphicsContext2D().setFill(fillColor);
        this.getGraphicsContext2D().fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    private void drawCanvasGridLines(Color strokeColor) {
        if (strokeColor == null) { strokeColor = Color.GRAY; }
        this.getGraphicsContext2D().setStroke(strokeColor);
        for (double i = 0.5; i < this.getHeight(); i += scale) {
            this.getGraphicsContext2D().strokeLine(i, 0.0, i, this.getHeight());
            this.getGraphicsContext2D().strokeLine(0.0, i, this.getHeight(), i);
        }
    }

}
