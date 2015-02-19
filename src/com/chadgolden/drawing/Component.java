package com.chadgolden.drawing;

import com.chadgolden.app.Controller;
import com.chadgolden.util.ComponentOptions;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public abstract class Component {

    protected ComponentOptions componentOptions;

    public Component(ComponentOptions componentOptions) {
        this.componentOptions = componentOptions;
    }

    public abstract void midpointScan();

    public void draw() {
        midpointScan();
    }

    public void writePixel(int x, int y) {
        int dotSize = (int)componentOptions.getScale();
        if (componentOptions.getColor() == null) { componentOptions.setColor(Color.WHITE); }
        componentOptions.getParentComponent().getGraphicsContext2D().setFill(componentOptions.getColor());
        componentOptions.getParentComponent().getGraphicsContext2D().fillRect(
                x * dotSize, y * dotSize, dotSize, dotSize);
    }

}