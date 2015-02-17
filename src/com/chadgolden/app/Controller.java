package com.chadgolden.app;

import com.chadgolden.drawing.Dot;
import com.chadgolden.drawing.Line;
import com.chadgolden.drawing.Polygon;
import com.chadgolden.util.CartesianGrid;
import com.chadgolden.util.ComponentOptions;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller logic for this application.
 */
public class Controller implements Initializable {

    @FXML
    private Canvas canvas;

    @FXML
    private MenuButton optionsMenu;

    @FXML
    private ToggleButton dotToggle;

    @FXML
    private ToggleButton lineToggle;

    @FXML
    private ToggleButton circleToggle;

    @FXML
    private ToggleButton polygonToggle;

    @FXML
    private ToggleButton fillToggle;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private ChoiceBox zoomBox;

    /** Determines the scale and resolution of the canvas. */
    private double scale;

    /** To contain base information (reference to canvas object, color, and scale. */
    private ComponentOptions baseComponentOptions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpCanvas(1.0);
        setUpZoomBox();
    }

    private void setUpZoomBox() {
        zoomBox.getItems().addAll("100%", "200%", "500%");
        zoomBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                switch (new_val.intValue()) {
                    case 0:
                        setUpCanvas(1.0);
                        break;
                    case 1:
                        setUpCanvas(2.0);
                        break;
                    case 2:
                        setUpCanvas(5.0);
                        break;
                    default:
                        setUpCanvas(1.0);
                        break;
                }
            }
        });
    }

    private void configureBaseComponentOptions(Color userSelectedColor) {
        baseComponentOptions = new ComponentOptions(canvas, userSelectedColor, scale);
    }

    private void setUpCanvas(double scale) {
        this.scale = scale * 5;
        fillCanvasBackground(null);
        drawCanvasGridLines(null);
        toggleGroup();
        canvasClick();
        configureBaseComponentOptions(Color.YELLOWGREEN);
    }

    private void toggleGroup() {
        toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(dotToggle, lineToggle, circleToggle, polygonToggle);
    }

    @FXML
    private void say() {
       System.out.println(toggleGroup.getSelectedToggle().getUserData().toString());
    }

    /**
     * Fill the canvas with a background color.
     * @param fillColor The color for the background. Leave null for black.
     */
    @FXML
    private void fillCanvasBackground(Color fillColor) {
        if (fillColor == null) { fillColor = Color.BLACK; }
        canvas.getGraphicsContext2D().setFill(fillColor);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    private void drawCanvasGridLines(Color strokeColor) {
        if (strokeColor == null) { strokeColor = Color.GRAY; }
        canvas.getGraphicsContext2D().setStroke(strokeColor);
        for (double i = 0.5; i < canvas.getHeight(); i += scale) {
            System.out.println(i + "                   " + (canvas.getHeight() / 2 + 0.5));
            if (i == (canvas.getHeight() / 2 + 0.5) ) {
                canvas.getGraphicsContext2D().setStroke(Color.TURQUOISE);
                canvas.getGraphicsContext2D().strokeLine(i, 0.0, i, canvas.getHeight());
                canvas.getGraphicsContext2D().strokeLine(0.0, i, canvas.getHeight(), i);
                canvas.getGraphicsContext2D().setStroke(strokeColor);
            } else {
                canvas.getGraphicsContext2D().strokeLine(i, 0.0, i, canvas.getHeight());
                canvas.getGraphicsContext2D().strokeLine(0.0, i, canvas.getHeight(), i);
            }

        }
    }

    @FXML
    private void canvasClick() {
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        int mouseX = (int)(e.getX()/scale);
                        int mouseY = (int)(e.getY()/scale);
                       // new Dot(canvas, (int)(e.getX()/ scale), (int)(e.getY()/ scale), Color.YELLOW, (int) scale);
                       // new Dot(baseComponentOptions, (int)(e.getX() / scale), (int)(e.getY() / scale));
                        //new Line(canvas, (int)(e.getX()/scale), (int)(e.getY()/scale), 50, 50, Color.YELLOW, (int)scale);
                        Line line1 = new Line(baseComponentOptions, new Dot(baseComponentOptions, mouseX, mouseY, 0), new Dot(baseComponentOptions, 0, 0, 0));
                        //System.out.println(line1.getSlope());
                        //new Circle(canvas, 20, Color.YELLOW, (int)scale);
                        //Dot[] dots = new Dot[3];
                        //new Polygon(baseComponentOptions, dots);
                        //System.out.println(mouseX + "     " + mouseY);
                    }
                });
    }

    @FXML
    private void clearCanvas() {
        fillCanvasBackground(null);
        drawCanvasGridLines(null);
    }

    /**
     * Finish process with exit code 0.
     */
    @FXML
    private void closeApplication() {
        System.out.println("System exiting gracefully!");
        System.exit(0);
    }
}
