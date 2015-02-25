package com.chadgolden.drawing;

import com.chadgolden.util.ComponentOptions;
import javafx.scene.paint.Color;

public abstract class Component {

    public Component() {
    }

    public abstract void midpointScan();

    public void draw() {
        midpointScan();
    }

    public void draw(boolean isDrawn) { if (isDrawn) midpointScan();}

    public void writePixel(int x, int y) {
        int dotSize = (int)ComponentOptions.getInstance().getScale();
        if (ComponentOptions.getInstance().getColor() == null) {
            ComponentOptions.getInstance().setColor(Color.WHITE);
        }
        ComponentOptions.getInstance().getParentComponent().getGraphicsContext2D().setFill(
                ComponentOptions.getInstance().getColor()
        );
        ComponentOptions.getInstance().getParentComponent().getGraphicsContext2D().fillRect(
                x * dotSize, y * dotSize, dotSize, dotSize
        );
        ComponentOptions.getInstance().updateDotMatrix(new Dot(x, y, false));
    }

    public void writePixel(int x, int y, boolean isDrawn) {
        int dotSize = (int)ComponentOptions.getInstance().getScale();
        if (ComponentOptions.getInstance().getColor() == null) {
            ComponentOptions.getInstance().setColor(Color.WHITE);
        }
        ComponentOptions.getInstance().getParentComponent().getGraphicsContext2D().setFill(
                ComponentOptions.getInstance().getColor()
        );
        ComponentOptions.getInstance().getParentComponent().getGraphicsContext2D().fillRect(
                x * dotSize, y * dotSize, dotSize, dotSize
        );
        if (isDrawn) {
            ComponentOptions.getInstance().updateDotMatrix(new Dot(x, y, false));
        }
    }

}