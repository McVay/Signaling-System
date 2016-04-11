
package com.plp.signalingsystem;

import java.awt.*;
import java.net.URL;
import java.util.*;

import com.google.gson.Gson;
import com.plp.signalingsystem.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

public class GUIController implements Initializable {

    @FXML
    private ToggleButton toggleSwitch;

    @FXML
    private Slider timeScaleSlider;

    @FXML
    private TextField timeScaleText;

    @FXML
    private SplitPane splitPane;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView StoplightA1, StoplightB1, StoplightC1, StoplightD1, StoplightE1, StoplightF1, StoplightG1, StoplightH1, StoplightI1;

    @FXML
    private ImageView StoplightA2, StoplightB2, StoplightC2, StoplightD2, StoplightE2, StoplightF2, StoplightG2, StoplightH2, StoplightI2;


    Map<String, StoplightUIElement> VALUES_BY_NAME;

    TrafficLightController trafficLightController = new TrafficLightController(GUIController.this);

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)  {

        grid.setStyle("-fx-background-color: #FFFFFD;");

        initializeGUILights();

        timeScaleText.textProperty().bindBidirectional(timeScaleSlider.valueProperty(), getStringConverter(timeScaleSlider, timeScaleText));
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
                trafficLightController.setTimeScale(object.intValue());
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
                trafficLightController.setTimeScale((int)val);
                textValue.setText(Integer.toString((int)val));
                return (int)val;
            }
        };
    }

    public void initializeGUILights() {
        Map<String,StoplightUIElement> valuesByName = new HashMap<String, StoplightUIElement>();

        StoplightUIElement StoplightA = new StoplightUIElement(StoplightA1, StoplightA2);
        StoplightUIElement StoplightB = new StoplightUIElement(StoplightB1, StoplightB2);
        StoplightUIElement StoplightC = new StoplightUIElement(StoplightC1, StoplightC2);
        StoplightUIElement StoplightD = new StoplightUIElement(StoplightD1, StoplightD2);

        StoplightUIElement StoplightE = new StoplightUIElement(StoplightE1, StoplightE2);
        StoplightUIElement StoplightF = new StoplightUIElement(StoplightF1, StoplightF2);
        StoplightUIElement StoplightG = new StoplightUIElement(StoplightG1, StoplightG2);
        StoplightUIElement StoplightH = new StoplightUIElement(StoplightH1, StoplightH2);

        valuesByName.put("StoplightA", StoplightA);
        valuesByName.put("StoplightB", StoplightB);
        valuesByName.put("StoplightC", StoplightC);
        valuesByName.put("StoplightD", StoplightD);

        valuesByName.put("StoplightE", StoplightE);
        valuesByName.put("StoplightF", StoplightF);
        valuesByName.put("StoplightG", StoplightG);
        valuesByName.put("StoplightH", StoplightH);

        VALUES_BY_NAME = valuesByName;
    }
    public void changeLightColor(Intersection intersection, String lightSequence) throws ClassNotFoundException {

        ArrayList<TrafficLight> lights = intersection.getLights();
        for(int i=0; i < lights.size(); i++)
        {
            StoplightUIElement thisLight = VALUES_BY_NAME.get(lights.get(i).getName());

            String status;

            char lightStatus = lightSequence.charAt(i);
            switch (lightStatus) {
                case 'R':
                    status = "Red";
                    break;
                case 'G':
                    status = "Green";
                    break;
                case 'Y':
                    status = "Yellow";
                    break;
                default:
                    status = "Off";
                    break;
            }

            Image img = new Image("com/plp/signalingsystem/pictures/" + status + ".png");

            if (thisLight.getGridLight() != null) {
                thisLight.getGridLight().setImage(img);
            }
            if (thisLight.getRoadLight() != null) {
                thisLight.getRoadLight().setImage(img);
            }

        }
    }
    public void turnAllLightsOff() {
        Image image = new Image("com/plp/signalingsystem/pictures/Off.png");

        for(StoplightUIElement e: VALUES_BY_NAME.values()) {
            if(e.getGridLight() != null) {
                e.getGridLight().setImage(image);
            }
            if(e.getRoadLight() != null) {
                e.getRoadLight().setImage(image);
            }
        }
    }
}