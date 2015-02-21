package com.chadgolden.drawing;

import com.chadgolden.util.ComponentOptions;

/**
 * Created by Chad on 2/18/2015.
 */
public class Circle extends Shape {

    private Dot center;
    private int radius;

    public Circle(Dot center, int radius) {
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

        new Dot(center.getX(), center.getY() + radius);
        new Dot(center.getX(), center.getY() - radius);
        new Dot(center.getX() + radius, center.getY());
        new Dot(center.getX() - radius, center.getY());

        while (x < y) {
            if (f >= 0) {
                y--;
                ddF_y += 2;
                f += ddF_y;
            }
            x++;
            ddF_x += 2;
            f += ddF_x + 1;
            new Dot(center.getX() + x, center.getY() + y);
            new Dot(center.getX() - x, center.getY() + y);
            new Dot(center.getX() + x, center.getY() - y);
            new Dot(center.getX() - x, center.getY() - y);
            new Dot(center.getX() + y, center.getY() + x);
            new Dot(center.getX() - y, center.getY() + x);
            new Dot(center.getX() + y, center.getY() - x);
            new Dot(center.getX() - y, center.getY() - x);
        }
    }

    @Override
    public void fill() {

    }
}
