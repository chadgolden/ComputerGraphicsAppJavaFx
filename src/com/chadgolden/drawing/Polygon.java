package com.chadgolden.drawing;

import com.chadgolden.util.ComponentOptions;

/**
 * Created by chad on 2/17/15.
 */
public class Polygon extends Component {

    private Dot[] vertices;

    public Polygon(ComponentOptions componentOptions, Dot... listOfDots) {
        super(componentOptions);
        listOfDots[0] = new Dot(componentOptions, 5, 5);
        listOfDots[1] = new Dot(componentOptions, 10, 15);
        listOfDots[2] = new Dot(componentOptions, 0, 15);
    }

    @Override
    public void midpointScan() {
//        for (Dot vertex : vertices) {
//            vertex.draw();
//        }
    }
}
