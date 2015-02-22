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
    private ToggleButton starToggle;

    @FXML
    private ToggleButton octagonToggle;

    @FXML
    private ToggleButton fishToggle;

    @FXML
    private ToggleButton placeholderToggle;

    @FXML
    private ToggleButton fillToggle;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private ChoiceBox zoomBox;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Label selectionLabel;

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    private TextField textField3;

    @FXML
    private TextField textField4;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label errorLabel;

    @FXML
    private Button optionSetButton;

    @FXML
    private Label mouseLabel;

    /** Determines the scale and resolution of the canvas. */
    private int dotSize;

    /* Some options for shapes drawing derived from user input. */
    private Dot userOptionsDot;
    private int userCircleRadius;
    private double userPolygonScale;

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
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        mouseEvent(e);
                    }
                });
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        mouseEvent(e);
                    }
                });
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        int mouseX = (int) (e.getX() / dotSize);
                        int mouseY = (int) (e.getY() / dotSize);
                        mouseLabel.setText(mouseX + " , " + mouseY);
                    }
                });

    }

    private void mouseEvent(MouseEvent e) {
        try {
            //setUpCanvas(dotSize);
            boolean doFill = fillToggle.isSelected();
            if (userCircleRadius == 0) userCircleRadius = 1;
            if (userPolygonScale == 0.0) userPolygonScale = 1.0;
            if (userOptionsDot == null) userOptionsDot = new Dot(0, 0, false);
            int mouseX = (int) (e.getX() / dotSize);
            int mouseY = (int) (e.getY() / dotSize);
            Dot origin = userOptionsDot;
            Dot mouse = new Dot(mouseX, mouseY, false);
//                        new Line(baseComponentOptions, origin, mouse);
//                        new NewCircle(baseComponentOptions, new Dot(baseComponentOptions, mouseX, mouseY, false), 5);
//                        new Polygon(baseComponentOptions, new Dot[3]);
            //System.out.println(getNameOfSelectedComponent());
            switch (getNameOfSelectedComponent()) {
                case "Dot":
                    new Dot(mouseX, mouseY);
                    break;
                case "Line":
                    new Line(origin, mouse);
                    break;
                case "Circle":
                    new Circle(mouse, userCircleRadius);
                    break;
                case "Star":
                    Dot dot1 = new Dot(
                            (int)(userPolygonScale * 0) + mouseX - 12,
                            (int)(userPolygonScale * 10) + mouseY - 11,
                            false
                    );
                    Dot dot2 = new Dot(
                            (int)(userPolygonScale * 10) + mouseX - 12,
                            (int)(userPolygonScale * 10) + mouseY - 11,
                            false
                    );
                    Dot dot3 = new Dot(
                            (int)(userPolygonScale * 12) + mouseX - 12,
                            (int)(userPolygonScale * 2) + mouseY - 11,
                            false
                    );
                    Dot dot4 = new Dot(
                            (int)(userPolygonScale * 15) + mouseX - 12,
                            (int)(userPolygonScale * 10) + mouseY - 11,
                            false
                    );
                    Dot dot5 = new Dot(
                            (int)(userPolygonScale * 25) + mouseX - 12,
                            (int)(userPolygonScale * 10) + mouseY - 11,
                            false
                    );
                    Dot dot6 = new Dot(
                            (int)(userPolygonScale * 17) + mouseX - 12,
                            (int)(userPolygonScale * 15) + mouseY - 11,
                            false
                    );
                    Dot dot7 = new Dot(
                            (int)(userPolygonScale * 20) + mouseX - 12,
                            (int)(userPolygonScale * 22) + mouseY - 11,
                            false
                    );
                    Dot dot8 = new Dot(
                            (int)(userPolygonScale * 12) + mouseX - 12,
                            (int)(userPolygonScale * 17) + mouseY - 11,
                            false
                    );
                    Dot dot9 = new Dot(
                            (int)(userPolygonScale * 5) + mouseX - 12,
                            (int)(userPolygonScale * 22) + mouseY - 11,
                            false
                    );
                    Dot dot10 = new Dot(
                            (int)(userPolygonScale * 7) + mouseX - 12,
                            (int)(userPolygonScale * 15) + mouseY - 11,
                            false
                    );
                    new Polygon(
                            doFill, // To draw or not to draw.
                            new Dot[] {
                                    dot1, dot2, dot3, dot4, dot5, dot6, dot7, dot8, dot9, dot10
                            }
                    );
                    break;
                case "Oct":
                    dot1 = new Dot(10 + mouseX - 12, 5 + mouseY - 12, false);
                    dot2 = new Dot(20 + mouseX - 12, 5 + mouseY - 12, false);
                    dot3 = new Dot(25 + mouseX - 12, 15 + mouseY - 12, false);
                    dot4 = new Dot(20 + mouseX - 12, 25 + mouseY  - 12, false);
                    dot5 = new Dot(10 + mouseX  - 12, 25 + mouseY - 12, false);
                    dot6 = new Dot(5 + mouseX - 12, 15 + mouseY - 12, false);
                    new Polygon(
                            true,
                            new Dot[] {
                                    dot1, dot2, dot3, dot4, dot5, dot6
                            }
                    );
                    break;
                case "Fish":
                    dot1 = new Dot(12 - 12 + mouseX, 12 - 12 + mouseY, false);
                    dot2 = new Dot(12 - 12 + mouseX, 18 - 12 + mouseY, false);
                    dot3 = new Dot(18 - 12 + mouseX, 22 - 12 + mouseY, false);
                    dot4 = new Dot(30 - 12 + mouseX, 12 - 12 + mouseY, false);
                    dot5 = new Dot(30 - 12 + mouseX, 18 - 12 + mouseY, false);
                    dot6 = new Dot(24 - 12 + mouseX, 12 - 12 + mouseY, false);
                    new Polygon(
                            true,
                            new Dot[] {
                                    dot1, dot2, dot3, dot4, dot5, dot6
                            }
                    );
                    break;
                    // Convex polygon
//                    Dot dot1 = new Dot(10, 10, false);
//                    Dot dot2 = new Dot(10, 16, false);
//                    Dot dot3 = new Dot(16, 20, false);
//                    Dot dot4 = new Dot(28, 10, false);
//                    Dot dot5 = new Dot(28, 16, false);
//                    Dot dot6 = new Dot(22, 10, false);
//                    new Polygon( true,
//                            new Dot[] { dot1, dot2, dot3, dot4, dot5, dot6 }
//                    );
//                    Square
//                      Dot dot1 = new Dot(1, 1, false);
//                    Dot dot2 = new Dot(20, 1, false);
//                    Dot dot3 = new Dot(1, 20, false);
//                    Dot dot4 = new Dot(20, 20, false);
//                    new Polygon( true,
//                            new Dot[] {dot1, dot2, dot4, dot3}
//                    );
                    //Hexagon

//                    Dot dot1 = new Dot(1, 1, false);
//                    Dot dot2 = new Dot(20, 1, false);
//                    Dot dot3 = new Dot(1, 20, false);
//                    Polygon triangle = new Polygon( true,
//                            new Dot[] {dot1, dot2, dot3}
//                    );

            }
            showErrorLabel("");
        } catch (Exception ex) {
            ex.printStackTrace();
            showErrorLabel("Ensure that an object is selected and all option fields are set.");
        }
    }

    /**
     * Configures zoomBox. Adds the zoom options, selects the first option by default, and sets action on change.
     */
    private void setUpZoomBox() {
        zoomBox.getItems().addAll("100%", "200%", "500%");
        zoomBox.getSelectionModel().select(0);
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
                }
            }
        });
    }

    private void configureBaseComponentOptions() {
        ComponentOptions.getInstance().setParentComponent(canvas);
        ComponentOptions.getInstance().setColor(colorPicker.getValue());
        ComponentOptions.getInstance().setScale(dotSize);
    }

    private void setUpCanvas(int dotSize) {
        this.dotSize = dotSize;
        fillCanvasBackground(null);
        drawCanvasGridLines(null);
        configureBaseComponentOptions();
        toggleGroup();
        canvasClick();
        showErrorLabel("");
    }

    /**
     * Handles responsibilities for grouping the <code>ToggleButton</code>'s.
     */
    private void toggleGroup() {
        toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(dotToggle, lineToggle, circleToggle, starToggle, octagonToggle, fishToggle);

        // "Dot" will be the selection by default.
        toggleGroup.selectToggle(dotToggle);
        configureOptions("Dot");

        // Set Action.
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                if (t1 != null) {
                    ToggleButton toggleButton = (ToggleButton) t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                    configureOptions(toggleButton.getText());
                }
            }
        });
    }

    @FXML
    private void colorPickerSelection() {
        ComponentOptions.getInstance().setColor(colorPicker.getValue());
    }

    @FXML
    private void configureOptions(String name) {
        switch(name) {
            case "Dot":
                selectionLabel.setText("Dot");
                label1.setText("N/A.");
                label2.setVisible(false);
                label3.setVisible(false);
                label4.setVisible(false);
                textField1.setVisible(false);
                textField2.setVisible(false);
                textField3.setVisible(false);
                textField4.setVisible(false);
                break;
            case "Line":
                selectionLabel.setText("Line");
                label1.setText("From X:");
                label2.setText("From Y:");
                textField1.setText("0"); // Default
                textField2.setText("0"); // Default
                label1.setVisible(true);
                label2.setVisible(true);
                label3.setVisible(false);
                label4.setVisible(false);
                textField1.setVisible(true);
                textField2.setVisible(true);
                textField3.setVisible(false);
                textField4.setVisible(false);
                System.out.println("Line");
                break;
            case "Circle":
                label1.setText("Radius: ");
                textField1.setText("5"); // Default
                label1.setVisible(true);
                label2.setVisible(false);
                label3.setVisible(false);
                label4.setVisible(false);
                textField1.setVisible(true);
                textField2.setVisible(false);
                textField3.setVisible(false);
                textField4.setVisible(false);
                selectionLabel.setText("Circle");
                break;
            case "Star":
                label1.setText("Coefficient:");
                label2.setVisible(false);
                label3.setVisible(false);
                label4.setVisible(false);
                textField1.setVisible(true);
                textField2.setVisible(false);
                textField3.setVisible(false);
                textField4.setVisible(false);
                selectionLabel.setText("Star");
                textField1.setText("1");
                break;
            case "Oct":
                label1.setText("Coefficient:");
                label2.setVisible(false);
                label3.setVisible(false);
                label4.setVisible(false);
                textField1.setVisible(true);
                textField2.setVisible(false);
                textField3.setVisible(false);
                textField4.setVisible(false);
                selectionLabel.setText("Octagon");
                textField1.setText("1");
                break;
            case "Fish":
                label1.setText("Coefficient:");
                label2.setVisible(false);
                label3.setVisible(false);
                label4.setVisible(false);
                textField1.setVisible(true);
                textField2.setVisible(false);
                textField3.setVisible(false);
                textField4.setVisible(false);
                selectionLabel.setText("Fish");
                textField1.setText("1");
                break;
        }
    }

    /**
     * Fired by <code>optionSetButton</code>.
     */
    @FXML
    private void setOptionsButton() {
        String toggle = getNameOfSelectedComponent();
        switch (toggle) {
            case "Dot":
                // Do nothing
                break;
            case "Line":
                userOptionsDot = new Dot(
                        Integer.parseInt(textField1.getText()),
                        Integer.parseInt(textField2.getText()),
                        false
                );
                break;
            case "Circle":
                userCircleRadius = Integer.parseInt(textField1.getText());
                break;
            default:
                userPolygonScale = Double.parseDouble(textField1.getText());
        }
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

    private String getNameOfSelectedComponent() {
        return ((ToggleButton)toggleGroup.getSelectedToggle()).getText();
    }

    private void showErrorLabel(String text) {
        errorLabel.setVisible(true);
        errorLabel.setText(text);
    }
}
