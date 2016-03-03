
package com.plp.signalingsystem;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

public class GUIController implements Initializable {

    @FXML
    private ToggleButton toggleSwitch;

    @FXML
    private Slider carNumSlider;

    @FXML
    private TextField carNumText;

    @FXML
    private Slider timeScaleSlider;

    @FXML
    private TextField timeScaleText;

    @FXML
    private Slider lightIntervalSlider;

    @FXML
    private TextField lightIntervalText;

    @FXML
    private SplitPane splittedPane;

    @FXML
    private GridPane grid;


    TrafficLightController trafficLightController = new TrafficLightController();

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)  {

        splittedPane.setStyle("-fx-background-color: #009933;");
        grid.setStyle("-fx-background-color: #FFFFFD;");

        carNumText.textProperty().bindBidirectional(carNumSlider.valueProperty(), getStringConverter(carNumSlider,carNumText));
        timeScaleText.textProperty().bindBidirectional(timeScaleSlider.valueProperty(), getStringConverter(timeScaleSlider, timeScaleText));
        lightIntervalText.textProperty().bindBidirectional(lightIntervalSlider.valueProperty(), getStringConverter(lightIntervalSlider, lightIntervalText));

        toggleSwitch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                boolean selected = toggleSwitch.isSelected();
                if(selected) {
                    toggleSwitch.setText("Stop Simulation");
                    trafficLightController.startSimulation();
                }


                else
                {
                    trafficLightController.stopSimulation();
                    toggleSwitch.setText("Start Simulation");
                }

            }
        });
    }


    public Boolean isNumber(String input) {
        return new Scanner(input).hasNextInt();
    }

    public StringConverter<Number> getStringConverter(final Slider slider, final TextField textValue) {
        return new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return Integer.toString(object.intValue());
            }
            @Override
            public Number fromString(String string) {
                double val;

                if(isNumber(string)) {
                    val = Double.parseDouble(string);

                    if(val > slider.getMax())
                        val = slider.getMax();
                    if(val <  slider.getMin())
                        val = slider.getMin();
                }
                else
                    val = slider.getValue();

                textValue.setText(Integer.toString((int)val));
                return (int)val;
            }
        };
    }
}