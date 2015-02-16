package com.chadgolden.app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller logic for this application.
 */
public class Controller implements Initializable{

    @FXML
    private Canvas canvas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillCanvasBackground(null);
        drawCanvasGridLines(null);
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
