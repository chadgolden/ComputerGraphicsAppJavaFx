package com.chadgolden.drawing;

/**
 * Created by Chad on 2/16/2015.
 */
public class Dot extends Component {

    private int x;
    private int y;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
        draw();
    }

    public Dot(int x, int y, boolean drawOnInitialization) {
        this.x = x;
        this.y = y;
        if (drawOnInitialization) {
            draw();
        }
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

    public void setX(int x) { this.x = x; }

    public void setY(int y) { this.y = y; }

}
