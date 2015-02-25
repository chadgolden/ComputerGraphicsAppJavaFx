package com.chadgolden.drawing;

import com.chadgolden.util.ComponentOptions;
import javafx.scene.paint.Color;

import java.util.Objects;

/**
 * Created by Chad on 2/20/2015.
 */
public abstract class Shape extends Component {

    public abstract void fill();

    public void floodFill(Dot origin, Color colorToReplace) {
        Color fillColor = ComponentOptions.getInstance().getColor();
       // Color atCurrentLocation = ComponentOptions.getInstance().getDotMatrix()[origin.getY()][origin.getX()];
        Color atCurrentLocation = ComponentOptions.getInstance().getColorFromDotMatrix(origin.getX(),origin.getY());
        if (!colorToReplace.equals(atCurrentLocation)) {
            return;
        }
        if (colorToReplace.equals(fillColor)) {
            return;
        }
        new Dot(origin.getX(), origin.getY(), true);
        floodFill(new Dot(origin.getX() - 1, origin.getY(), false), colorToReplace);
        floodFill(new Dot(origin.getX(), origin.getY() - 1, false), colorToReplace);
        floodFill(new Dot(origin.getX() + 1, origin.getY(), false), colorToReplace);
        floodFill(new Dot(origin.getX(), origin.getY() + 1, false), colorToReplace);
    }

    public static void floodFill(Dot origin, Color colorToReplace, Color fillColor) {
        Color atCurrentLocation = ComponentOptions.getInstance().getColorFromDotMatrix(origin.getX(),origin.getY());
        if (!colorToReplace.equals(atCurrentLocation)) {
            return;
        }
        if (colorToReplace.equals(fillColor)) {
            return;
        }
        new Dot(origin.getX(), origin.getY(), true);
        floodFill(new Dot(origin.getX() - 1, origin.getY(), false), colorToReplace, fillColor);
        floodFill(new Dot(origin.getX(), origin.getY() - 1, false), colorToReplace, fillColor);
        floodFill(new Dot(origin.getX() + 1, origin.getY(), false), colorToReplace, fillColor);
        floodFill(new Dot(origin.getX(), origin.getY() + 1, false), colorToReplace, fillColor);
    }

}
