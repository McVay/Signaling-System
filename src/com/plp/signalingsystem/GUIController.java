
package com.plp.signalingsystem;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
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

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        carNumText.textProperty().bindBidirectional(carNumSlider.valueProperty(), getStringConverter(carNumSlider,carNumText));
        timeScaleText.textProperty().bindBidirectional(timeScaleSlider.valueProperty(), getStringConverter(timeScaleSlider, timeScaleText));
        lightIntervalText.textProperty().bindBidirectional(lightIntervalSlider.valueProperty(), getStringConverter(lightIntervalSlider, lightIntervalText));

        toggleSwitch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean selected = toggleSwitch.isSelected();
                if(selected)
                    toggleSwitch.setText("Stop Simulation");
                else
                    toggleSwitch.setText("Start Simulation");

                System.out.println("Simulation Status: " + toggleSwitch.isSelected());
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