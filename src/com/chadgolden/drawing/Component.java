package com.chadgolden.drawing;


import com.chadgolden.util.ComponentOptions;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public abstract class Component {

    protected Canvas parentComponent;
    protected Color color;
    protected int scale;
    protected ComponentOptions componentOptions;

    public Component(ComponentOptions componentOptions) {
        this.componentOptions = componentOptions;
        this.parentComponent = componentOptions.getParentComponent();
        this.color = componentOptions.getColor();
        this.scale = (int)componentOptions.getScale();
    }

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
        if (componentOptions.getColor() == null) { componentOptions.setColor(Color.WHITE); }
        componentOptions.getParentComponent().getGraphicsContext2D().setFill(componentOptions.getColor());
        componentOptions.getParentComponent().getGraphicsContext2D().fillRect(x * scale, y * scale, scale, scale);
    }

}