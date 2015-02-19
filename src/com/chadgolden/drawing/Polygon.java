package com.chadgolden.drawing;

import com.chadgolden.util.ComponentOptions;

/**
 * Created by chad on 2/17/15.
 */
public class Polygon extends Component {

    private Dot[] vertices;

    public Polygon(ComponentOptions componentOptions, Dot... listOfDots) {
        super(componentOptions);
//        listOfDots[0] = new Dot(componentOptions, 10, 10);
//        listOfDots[1] = new Dot(componentOptions, 35, 35);
//        listOfDots[2] = new Dot(componentOptions, 35, 9);
        this.vertices = listOfDots;
        draw();
    }

    public void draw() {
        for (int i = 0; i < vertices.length - 1; i++) {
            new Line(componentOptions, vertices[i], vertices[i + 1]);
        }
        new Line(componentOptions, vertices[vertices.length - 1], vertices[0]);
    }

    @Override
    public void midpointScan() {
//        for (Dot vertex : vertices) {
//            vertex.draw();
//        }
    }
}
