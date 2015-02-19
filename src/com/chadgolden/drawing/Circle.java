package com.chadgolden.drawing;

import com.chadgolden.util.ComponentOptions;

/**
 * Created by Chad on 2/18/2015.
 */
public class Circle extends Component {

    private Dot center;
    private int radius;

    public Circle(ComponentOptions componentOptions, Dot center, int radius) {
        super(componentOptions);
        this.center = center;
        this.radius = radius;
        draw();
    }

    @Override
    public void midpointScan() {
        int f = 1 - radius;
        int ddF_x = 0;
        int ddF_y = -2 * radius;
        int x = 0;
        int y = radius;

        new Dot(componentOptions, center.getX(), center.getY() + radius);
        new Dot(componentOptions, center.getX(), center.getY() - radius);
        new Dot(componentOptions, center.getX() + radius, center.getY());
        new Dot(componentOptions, center.getX() - radius, center.getY());

        while (x < y) {
            if (f >= 0) {
                y--;
                ddF_y += 2;
                f += ddF_y;
            }
            x++;
            ddF_x += 2;
            f += ddF_x + 1;
            new Dot(componentOptions, center.getX() + x, center.getY() + y);
            new Dot(componentOptions, center.getX() - x, center.getY() + y);
            new Dot(componentOptions, center.getX() + x, center.getY() - y);
            new Dot(componentOptions, center.getX() - x, center.getY() - y);
            new Dot(componentOptions, center.getX() + y, center.getY() + x);
            new Dot(componentOptions, center.getX() - y, center.getY() + x);
            new Dot(componentOptions, center.getX() + y, center.getY() - x);
            new Dot(componentOptions, center.getX() - y, center.getY() - x);
        }
    }
}
