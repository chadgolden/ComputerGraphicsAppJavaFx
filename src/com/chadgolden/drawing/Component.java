package com.chadgolden.drawing;


import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public abstract class Component {

    protected Canvas parentComponent;
    protected Color color;
    protected int scale;

    public Component(Canvas parentComponent, Color color, int scale) {
        this.parentComponent = parentComponent;
        this.color = color;
        this.scale = scale;
    }

    public abstract void midpointScan();

    public void draw() {
        midpointScan();
    }

    public Canvas getParentComponent() {
        return parentComponent;
    }

    public void writePixel(int x, int y) {
        if (color == null) { color = Color.WHITE; }
        parentComponent.getGraphicsContext2D().setFill(color);
        parentComponent.getGraphicsContext2D().fillRect(x * scale, y * scale, scale, scale);
    }

}