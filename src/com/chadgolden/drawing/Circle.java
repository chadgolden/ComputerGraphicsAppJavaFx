package com.chadgolden.drawing;

import com.chadgolden.util.ComponentOptions;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Circle extends Component {

    private int radius;
    private Color color;

    public Circle(ComponentOptions componentOptions, int radius) {
        super(componentOptions);
        this.radius = radius;
        draw();
    }

    public Circle(Canvas parentComponent, int radius, Color color, int scale) {
        super(parentComponent, color, scale);
        this.radius = radius;
        this.color = color;
        draw();
    }

    @Override
    public void midpointScan() {
        int x = 0;
        int y = radius;
        double d = 5/4 - radius;

        do {
            circleify(x, y);
            if (d < 0) {
                d += 2.0 * x + 3.0;
                x++;
            }
            else {
                d += 2.0 * (x - y) + 5.0;
                x++;
                y--;
            }
        } while (x <= y);
    }

    private void circleify(int x, int y) {
        new Dot(parentComponent, x, y, color, scale);
        new Dot(parentComponent, y, x, color, scale);
        new Dot(parentComponent, y, -x, color, scale);
        new Dot(parentComponent, x, -y, color, scale);
        new Dot(parentComponent, -x, -y, color, scale);
        new Dot(parentComponent, -y, -x, color, scale);
        new Dot(parentComponent, -y, x, color, scale);
        new Dot(parentComponent, -x, y, color, scale);
    }



}