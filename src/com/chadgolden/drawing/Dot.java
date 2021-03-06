package com.chadgolden.drawing;

import com.chadgolden.util.CartesianGrid;
import com.chadgolden.util.ComponentOptions;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 * Created by Chad on 2/16/2015.
 */
public class Dot extends Component {

    private int x;
    private int y;

    public Dot(ComponentOptions componentOptions, int x, int y) {
        super(componentOptions);
        this.x = x;
        this.y = y;
        draw();
    }

    public Dot(ComponentOptions componentOptions, int x, int y, int flag) {
        super(componentOptions);
        this.x = x;
        this.y = y;
    }

    public Dot(Canvas parentComponent, int x, int y, Color color, int scale) {
        super(parentComponent, color, scale);
        this.x = x;
        this.y = y;
        draw();
    }

    private void toCanvas() {
        this.x = CartesianGrid.cartesianToCanvasX(this.x);
        this.y = CartesianGrid.cartesianToCanvasY(this.y);
    }

    @Override
    public void midpointScan() {
        writePixel(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
