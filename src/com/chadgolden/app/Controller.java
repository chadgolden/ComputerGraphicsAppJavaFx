package com.chadgolden.app;

import com.chadgolden.drawing.*;
import com.chadgolden.util.ComponentOptions;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
    private ToggleButton complexToggle;

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
    private ToggleButton playButton;

    @FXML
    private Label mouseLabel;

    @FXML
    private Button stopButton;

    /** Determines the scale and resolution of the canvas. */
    private int dotSize;

    /* Some options for shapes drawing derived from user input. */
    private Dot userOptionsDot;
    private int userCircleRadius;
    private double userPolygonScale;

    private String nameOfSelectedComponent;

    private boolean SHOULD_PLAY_ANIMATION = true;

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
                        ComponentOptions.getInstance().setOffsetX(mouseX);
                        ComponentOptions.getInstance().setOffsetY(mouseY);
                        mouseLabel.setText(mouseX + " , " + mouseY);
                    }
                });

    }

    private void mouseEvent(MouseEvent e) {
        try {
            //setUpCanvas(dotSize)
            boolean doFill = fillToggle.isSelected();
            if (userCircleRadius == 0) userCircleRadius = 1;
            if (userPolygonScale == 0.0) userPolygonScale = 1.0;
            if (userOptionsDot == null) userOptionsDot = new Dot(0, 0, false);
            int mouseX = (int) (e.getX() / dotSize);
            int mouseY = (int) (e.getY() / dotSize);
            ComponentOptions.getInstance().setOffsetX(mouseX);
            ComponentOptions.getInstance().setOffsetY(mouseY);
            mouseLabel.setText(mouseX + " , " + mouseY);
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
                    new Circle(mouse, userCircleRadius, doFill);
                    break;
                case "Star":
                    if (playButton.isSelected()) {
                        animateLineScan("Star");
                        return;
                    }
                    Dot dot1 = new Dot(
                            (int)(userPolygonScale * 0),
                            (int)(userPolygonScale * 10),
                            false
                    );
                    Dot dot2 = new Dot(
                            (int)(userPolygonScale * 10),
                            (int)(userPolygonScale * 10),
                            false
                    );
                    Dot dot3 = new Dot(
                            (int)(userPolygonScale * 12),
                            (int)(userPolygonScale * 2),
                            false
                    );
                    Dot dot4 = new Dot(
                            (int)(userPolygonScale * 15),
                            (int)(userPolygonScale * 10),
                            false
                    );
                    Dot dot5 = new Dot(
                            (int)(userPolygonScale * 25),
                            (int)(userPolygonScale * 10),
                            false
                    );
                    Dot dot6 = new Dot(
                            (int)(userPolygonScale * 17),
                            (int)(userPolygonScale * 15),
                            false
                    );
                    Dot dot7 = new Dot(
                            (int)(userPolygonScale * 20),
                            (int)(userPolygonScale * 22),
                            false
                    );
                    Dot dot8 = new Dot(
                            (int)(userPolygonScale * 12),
                            (int)(userPolygonScale * 17),
                            false
                    );
                    Dot dot9 = new Dot(
                            (int)(userPolygonScale * 5),
                            (int)(userPolygonScale * 22),
                            false
                    );
                    Dot dot10 = new Dot(
                            (int)(userPolygonScale * 7),
                            (int)(userPolygonScale * 15),
                            false
                    );
                    new Polygon(
                            doFill, // To fill or not to fill.
                            new Dot[] {
                                    dot1, dot2, dot3, dot4, dot5, dot6, dot7, dot8, dot9, dot10
                            }
                    );
                    break;
                case "Oct":
                    if (playButton.isSelected()) {
                        animateLineScan("Oct");
                        return;
                    }
//                    dot1 = new Dot(10 + mouseX - 12, 5 + mouseY - 12, false);
//                    dot2 = new Dot(20 + mouseX - 12, 5 + mouseY - 12, false);
//                    dot3 = new Dot(25 + mouseX - 12, 15 + mouseY - 12, false);
//                    dot4 = new Dot(20 + mouseX - 12, 25 + mouseY  - 12, false);
//                    dot5 = new Dot(10 + mouseX  - 12, 25 + mouseY - 12, false);
//                    dot6 = new Dot(5 + mouseX - 12, 15 + mouseY - 12, false);

                    dot1 = new Dot(
                            43,
                            17,
                            false
                    );
                    dot2 = new Dot(
                            33,
                            7,
                            false
                    );
                    dot3 = new Dot(
                            17,
                            7,
                            false
                    );
                    dot4 = new Dot(
                            7,
                            17,
                            false
                    );
                    dot5 = new Dot(
                            7,
                            33,
                            false
                    );
                    dot6 = new Dot(
                            17,
                            43,
                            false
                    );
                    dot7 = new Dot(
                            33,
                            43,
                            false
                    );
                    dot8 = new Dot(
                            43,
                            33,
                            false
                    );
                    new Polygon(
                            doFill,
                            userPolygonScale,
                            new Dot[] {
                                    dot1, dot2, dot3, dot4, dot5, dot6, dot7, dot8
                            }
                    );
                    break;
                case "Fish":
                    if (playButton.isSelected()) {
                        animateLineScan("Fish");
                        return;
                    }
                    dot1 = new Dot(0, 0, false);
                    dot2 = new Dot(0, 6, false);
                    dot3 = new Dot(6, 10, false);
                    dot4 = new Dot(18, 0, false);
                    dot5 = new Dot(18, 6, false);
                    dot6 = new Dot(12, 0, false);
                    new Polygon(
                            doFill,
                            userPolygonScale,
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
                case "?":
                    if (playButton.isSelected()) {
                        animateLineScan("?");
                        return;
                    }
                    dot1 = new Dot(0, 0, false);
                    dot2 = new Dot(10, 5, false);
                    dot3 = new Dot(15, 0, false);
                    dot4 = new Dot(20, 5, false);
                    dot5 = new Dot(15, 10, false);
                    dot6 = new Dot(20, 15, false);
                    dot7 = new Dot(15, 20, false);
                    dot8 = new Dot(15, 10, false);
                    dot9 = new Dot(10, 5, false);
                    dot10 = new Dot(10, 10, false);
                    Dot dot11 = new Dot(5, 10, false);
                    new Polygon(doFill, userPolygonScale,
                            dot1, dot2, dot3, dot4, dot5, dot6, dot7, dot8, dot9, dot10, dot11);

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
        ComponentOptions.getInstance().initializeDotMatrix();
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
        toggleGroup.getToggles().addAll(dotToggle, lineToggle, circleToggle, starToggle, octagonToggle, fishToggle,
                complexToggle);

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
            case "?":
                label1.setText("Coefficient:");
                label2.setVisible(false);
                label3.setVisible(false);
                label4.setVisible(false);
                textField1.setVisible(true);
                textField2.setVisible(false);
                textField3.setVisible(false);
                textField4.setVisible(false);
                selectionLabel.setText("Complex");
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

    private void animateLineScan() {
        Timeline tl = new Timeline();
        tl.setCycleCount(2500);
        KeyFrame lineScan = new KeyFrame(Duration.seconds(0.005),
                new EventHandler<ActionEvent>() {
                    int numberOfPixels = 1;
                    int startMouseX = ComponentOptions.getInstance().getOffsetX();
                    int startMouseY = ComponentOptions.getInstance().getOffsetY();
                    public void handle(ActionEvent event) {
                        if (!SHOULD_PLAY_ANIMATION) {
                            return;
                        }
                        ComponentOptions.getInstance().setOffsetX(startMouseX);
                        ComponentOptions.getInstance().setOffsetY(startMouseY);
//                        Dot dot1 = new Dot(0, 0, false);
//                        Dot dot2 = new Dot(10, 5, false);
//                        Dot dot3 = new Dot(15, 0, false);
//                        Dot dot4 = new Dot(20, 5, false);
//                        Dot dot5 = new Dot(15, 10, false);
//                        Dot dot6 = new Dot(20, 15, false);
//                        Dot dot7 = new Dot(15, 20, false);
//                        Dot dot8 = new Dot(15, 10, false);
//                        Dot dot9 = new Dot(10, 5, false);
//                        Dot dot10 = new Dot(10, 10, false);
//                        Dot dot11 = new Dot(5, 10, false);
//                        new Polygon(
//                                userPolygonScale,
//                                numberOfPixels,
//                                dot1, dot2, dot3, dot4, dot5, dot6, dot7, dot8, dot9, dot10, dot11
//                        );
                        Dot dot1 = new Dot(
                                (int)(userPolygonScale * 0),
                                (int)(userPolygonScale * 10),
                                false
                        );
                        Dot dot2 = new Dot(
                                (int)(userPolygonScale * 10),
                                (int)(userPolygonScale * 10),
                                false
                        );
                        Dot dot3 = new Dot(
                                (int)(userPolygonScale * 12),
                                (int)(userPolygonScale * 2),
                                false
                        );
                        Dot dot4 = new Dot(
                                (int)(userPolygonScale * 15),
                                (int)(userPolygonScale * 10),
                                false
                        );
                        Dot dot5 = new Dot(
                                (int)(userPolygonScale * 25),
                                (int)(userPolygonScale * 10),
                                false
                        );
                        Dot dot6 = new Dot(
                                (int)(userPolygonScale * 17),
                                (int)(userPolygonScale * 15),
                                false
                        );
                        Dot dot7 = new Dot(
                                (int)(userPolygonScale * 20),
                                (int)(userPolygonScale * 22),
                                false
                        );
                        Dot dot8 = new Dot(
                                (int)(userPolygonScale * 12),
                                (int)(userPolygonScale * 17),
                                false
                        );
                        Dot dot9 = new Dot(
                                (int)(userPolygonScale * 5),
                                (int)(userPolygonScale * 22),
                                false
                        );
                        Dot dot10 = new Dot(
                                (int)(userPolygonScale * 7),
                                (int)(userPolygonScale * 15),
                                false
                        );
                        new Polygon(
                                userPolygonScale,
                                numberOfPixels,
                                new Dot[] {
                                        dot1, dot2, dot3, dot4, dot5, dot6, dot7, dot8, dot9, dot10
                                }
                        );
                        numberOfPixels++;
                    }
                });
        tl.getKeyFrames().add(lineScan);
        tl.play();
    }

    private void animateLineScan(final String name) {
        if (!SHOULD_PLAY_ANIMATION) {
            return;
        }
        Timeline tl = new Timeline();
        tl.setCycleCount(1500);
        KeyFrame lineScan = new KeyFrame(Duration.seconds(0.005),
                new EventHandler<ActionEvent>() {

                    int numberOfPixels = 1;
                    int startMouseX = ComponentOptions.getInstance().getOffsetX();
                    int startMouseY = ComponentOptions.getInstance().getOffsetY();
                    public void handle(ActionEvent event) {
                        ComponentOptions.getInstance().setOffsetX(startMouseX);
                        ComponentOptions.getInstance().setOffsetY(startMouseY);
                        switch (name) {
                            case "Star":
                                Dot dot1 = new Dot(
                                        (int)(userPolygonScale * 0),
                                        (int)(userPolygonScale * 10),
                                        false
                                );
                                Dot dot2 = new Dot(
                                        (int)(userPolygonScale * 10),
                                        (int)(userPolygonScale * 10),
                                        false
                                );
                                Dot dot3 = new Dot(
                                        (int)(userPolygonScale * 12),
                                        (int)(userPolygonScale * 2),
                                        false
                                );
                                Dot dot4 = new Dot(
                                        (int)(userPolygonScale * 15),
                                        (int)(userPolygonScale * 10),
                                        false
                                );
                                Dot dot5 = new Dot(
                                        (int)(userPolygonScale * 25),
                                        (int)(userPolygonScale * 10),
                                        false
                                );
                                Dot dot6 = new Dot(
                                        (int)(userPolygonScale * 17),
                                        (int)(userPolygonScale * 15),
                                        false
                                );
                                Dot dot7 = new Dot(
                                        (int)(userPolygonScale * 20),
                                        (int)(userPolygonScale * 22),
                                        false
                                );
                                Dot dot8 = new Dot(
                                        (int)(userPolygonScale * 12),
                                        (int)(userPolygonScale * 17),
                                        false
                                );
                                Dot dot9 = new Dot(
                                        (int)(userPolygonScale * 5),
                                        (int)(userPolygonScale * 22),
                                        false
                                );
                                Dot dot10 = new Dot(
                                        (int)(userPolygonScale * 7),
                                        (int)(userPolygonScale * 15),
                                        false
                                );
                                new Polygon(
                                        userPolygonScale,
                                        numberOfPixels,
                                        new Dot[] {
                                                dot1, dot2, dot3, dot4, dot5, dot6, dot7, dot8, dot9, dot10
                                        }
                                );
                                break;
                            case "Oct":
                                dot1 = new Dot(
                                        43,
                                        17,
                                        false
                                );
                                dot2 = new Dot(
                                        33,
                                        7,
                                        false
                                );
                                dot3 = new Dot(
                                        17,
                                        7,
                                        false
                                );
                                dot4 = new Dot(
                                        7,
                                        17,
                                        false
                                );
                                dot5 = new Dot(
                                        7,
                                        33,
                                        false
                                );
                                dot6 = new Dot(
                                        17,
                                        43,
                                        false
                                );
                                dot7 = new Dot(
                                        33,
                                        43,
                                        false
                                );
                                dot8 = new Dot(
                                        43,
                                        33,
                                        false
                                );
                                new Polygon(
                                        userPolygonScale,
                                        numberOfPixels,
                                        new Dot[] {
                                                dot1, dot2, dot3, dot4, dot5, dot6, dot7, dot8
                                        }
                                );
                                break;
                            case "Fish":
                                dot1 = new Dot(0, 0, false);
                                dot2 = new Dot(0, 6, false);
                                dot3 = new Dot(6, 10, false);
                                dot4 = new Dot(18, 0, false);
                                dot5 = new Dot(18, 6, false);
                                dot6 = new Dot(12, 0, false);
                                new Polygon(
                                        userPolygonScale,
                                        numberOfPixels,
                                        new Dot[] {
                                                dot1, dot2, dot3, dot4, dot5, dot6
                                        }
                                );
                                break;
                            case "?":
                                dot1 = new Dot(0, 0, false);
                                dot2 = new Dot(10, 5, false);
                                dot3 = new Dot(15, 0, false);
                                dot4 = new Dot(20, 5, false);
                                dot5 = new Dot(15, 10, false);
                                dot6 = new Dot(20, 15, false);
                                dot7 = new Dot(15, 20, false);
                                dot8 = new Dot(15, 10, false);
                                dot9 = new Dot(10, 5, false);
                                dot10 = new Dot(10, 10, false);
                                Dot dot11 = new Dot(5, 10, false);
                                new Polygon(
                                        userPolygonScale,
                                        numberOfPixels,
                                        dot1, dot2, dot3, dot4, dot5, dot6, dot7, dot8, dot9, dot10, dot11
                                );
                                break;
                        }
                        numberOfPixels++;
                    }
                });
        tl.getKeyFrames().add(lineScan);
        tl.play();
    }

    @FXML
    private void stopAnimation() {
        SHOULD_PLAY_ANIMATION = false;
    }

}
