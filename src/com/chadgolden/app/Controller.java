package com.chadgolden.app;

import com.chadgolden.drawing.*;
import com.chadgolden.util.ComponentOptions;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
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

    @FXML
    private ColorPicker colorPicker;

    /** Determines the scale and resolution of the canvas. */
    private int dotSize;

    /** To contain base information (reference to canvas object, color, and scale. */
    private ComponentOptions baseComponentOptions;

    private String nameOfSelectedComponent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpCanvas(5);
        setUpZoomBox();
    }

    /**
     * Action when clicking the canvas.
     */
    @FXML
    private void canvasClick() {
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        int mouseX = (int)(e.getX()/dotSize);
                        int mouseY = (int)(e.getY()/dotSize);
                        Dot origin = new Dot(baseComponentOptions, 25, 25, false);
                        Dot mouse = new Dot(baseComponentOptions, mouseX, mouseY, false);
                        new Line(baseComponentOptions, origin, mouse);
//                        new NewCircle(baseComponentOptions, new Dot(baseComponentOptions, mouseX, mouseY, false), 5);
//                        new Polygon(baseComponentOptions, new Dot[3]);
                    }
                });

    }

    private void setUpZoomBox() {
        zoomBox.getItems().addAll("100%", "200%", "500%");
        zoomBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                switch (new_val.intValue()) {
                    case 0:
                        setUpCanvas(5);
                        break;
                    case 1:
                        setUpCanvas(10);
                        break;
                    case 2:
                        setUpCanvas(25);
                        break;
                    default:
                        setUpCanvas(10);
                        break;
                }
            }
        });
    }

    private void configureBaseComponentOptions() {
        baseComponentOptions = new ComponentOptions(canvas, colorPicker.getValue(), dotSize);
    }

    private void setUpCanvas(int dotSize) {
        this.dotSize = dotSize;
        fillCanvasBackground(null);
        drawCanvasGridLines(null);
        toggleGroup();
        canvasClick();
        configureBaseComponentOptions();

    }

    private void toggleGroup() {
        toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(dotToggle, lineToggle, circleToggle, polygonToggle);
    }

    @FXML
    private void colorPickerSelection() {
        baseComponentOptions.setColor(colorPicker.getValue());
    }

    /**
     * Fill the canvas with a background color.
     * @param fillColor The color for the background. Leave null for black.
     */
    @FXML
    private void fillCanvasBackground(Color fillColor) {
        if (fillColor == null) {
            fillColor = Color.BLACK;
        }
        canvas.getGraphicsContext2D().setFill(fillColor);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    private void drawCanvasGridLines(Color strokeColor) {
        if (strokeColor == null) { strokeColor = Color.GRAY; }
        canvas.getGraphicsContext2D().setStroke(strokeColor);
        for (double i = 0.5; i < canvas.getHeight(); i += dotSize) {
            canvas.getGraphicsContext2D().strokeLine(i, 0.0, i, canvas.getHeight());
            canvas.getGraphicsContext2D().strokeLine(0.0, i, canvas.getHeight(), i);
        }
    }

    /**
     * Reset the canvas.
     */
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
