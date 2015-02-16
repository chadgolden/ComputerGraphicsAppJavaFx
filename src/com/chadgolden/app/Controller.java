package com.chadgolden.app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller logic for this application.
 */
public class Controller implements Initializable{

    @FXML
    private Canvas canvas;

    @FXML
    private Slider slider;

    @FXML
    private MenuButton optionsMenu;

    @FXML
    private RadioButton lineRadio;

    @FXML
    private RadioButton circleRadio;

    private ToggleGroup toggleGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillCanvasBackground(null);
        drawCanvasGridLines(null);
        sliderAction();
        toggleGroup();
    }

    private void toggleGroup() {
        toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(lineRadio, circleRadio);
    }

    @FXML
    private void say() {
       System.out.println(toggleGroup);
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
        for (double i = 0.5; i < canvas.getHeight(); i+=5.0) {
            canvas.getGraphicsContext2D().strokeLine(i, 0.0, i, canvas.getHeight());
            canvas.getGraphicsContext2D().strokeLine(0.0, i, canvas.getHeight(), i);
        }
    }

    @FXML
    private void sliderAction() {
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                System.out.println((int)slider.getValue());
            }
        });
    }

    @FXML
    private void clearCanvas() {
        fillCanvasBackground(null);
        drawCanvasGridLines(null);
    }

    @FXML
    private void buttonAction() {
        writePixel(4, 4, null);
    }

    @FXML
    private void writePixel(int x, int y, Color pixelColor) {
        if (pixelColor == null) { pixelColor = Color.YELLOW; }
        canvas.getGraphicsContext2D().setFill(pixelColor);
        canvas.getGraphicsContext2D().fillRect(x * 5.0, y * 5.0, 5.0, 5.0);
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
