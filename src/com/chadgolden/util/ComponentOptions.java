package com.chadgolden.util;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 * Created by Chad on 2/16/2015.
 */
public class ComponentOptions {

    private Canvas parentComponent;
    private Color color;
    private double scale;

    public ComponentOptions(Canvas parentComponent, Color color, double scale) {
        this.setParentComponent(parentComponent);
        this.setColor(color);
        this.setScale(scale);
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
}
