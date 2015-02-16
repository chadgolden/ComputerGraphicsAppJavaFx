package com.chadgolden.drawing;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 * Created by Chad on 2/16/2015.
 */
public class Dot extends Component {

    private int x;
    private int y;

    public Dot(Canvas parentComponent, int x, int y, Color color, int scale) {
        super(parentComponent, color, scale);
        this.x = x;
        this.y = y;
        draw();
    }

    @Override
    public void midpointScan() {
        writePixel(x, y);
    }
}
