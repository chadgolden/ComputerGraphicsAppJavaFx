package com.chadgolden.drawing;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Line extends Component {

    private int x0;
    private int x1;
    private int y0;
    private int y1;
    private Color color;

    public Line(Canvas parentComponent, int x0, int y0, int x1, int y1, Color color, int scale) {
        super(parentComponent, color, scale);
        this.x0 = x0;
        this.x1 = x1;
        this.y0 = y0;
        this.y1 = y1;
        this.color = color;
        draw();
    }

    @Override
    public void midpointScan() {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int d = 2 * dy - dx;
        int incrE = 2 * dy;
        int incrNE = 2 * (dy - dx);
        int x = x0;
        int y = y0;
        do {
            new Dot(parentComponent, x, y, color, scale);
            if (d <= 0) { // Choose E.
                x += 1;
                d+= incrE;
            }
            else { // Choose NE.
                x += 1;
                y += 1;
                d += incrNE;
            }
        } while (x <= x1);
    }

}