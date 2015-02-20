package com.chadgolden.util;

import com.chadgolden.drawing.Component;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 * Allows for single global access to canvas, color, and scale references.
 * Created by Chad on 2/16/2015.
 */
public class ComponentOptions {

    /** Single instance of <code>ComponentOptions</code>. */
    private static ComponentOptions singletonInstance = null;

    private Canvas parentComponent;
    private Color color;
    private double scale;

    private ComponentOptions() {
    }

    public static ComponentOptions getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new ComponentOptions();
        }
        return singletonInstance;
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
