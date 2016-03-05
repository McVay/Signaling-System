
package com.plp.signalingsystem;

import java.awt.*;
import java.net.URL;
import java.util.*;

import com.plp.signalingsystem.model.LightStatus;
import com.plp.signalingsystem.model.StoplightUIElement;
import com.plp.signalingsystem.model.TrafficLight;
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

    TrafficLightController trafficLightController = new TrafficLightController();

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)  {

        splitPane.setStyle("-fx-background-color: #009933;");
        grid.setStyle("-fx-background-color: #FFFFFD;");

        initializeGUILights();

        timeScaleText.textProperty().bindBidirectional(timeScaleSlider.valueProperty(), getStringConverter(timeScaleSlider, timeScaleText));

        toggleSwitch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                boolean selected = toggleSwitch.isSelected();
                if(selected) {
                    toggleSwitch.setText("Stop Simulation");
                    trafficLightController.startSimulation(GUIController.this);
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

    public  void initializeGUILights() {
        Map<String,StoplightUIElement> valuesByName = new HashMap<String, StoplightUIElement>();

        StoplightUIElement StoplightA = new StoplightUIElement(StoplightA1, StoplightA2);
        StoplightUIElement StoplightB = new StoplightUIElement(StoplightB1, StoplightB2);
        StoplightUIElement StoplightC = new StoplightUIElement(StoplightC1, StoplightC2);
        StoplightUIElement StoplightD = new StoplightUIElement(StoplightD1, StoplightD2);
        StoplightUIElement StoplightE = new StoplightUIElement(StoplightE1, StoplightE2);
        StoplightUIElement StoplightF = new StoplightUIElement(StoplightF1, StoplightF2);
        StoplightUIElement StoplightG = new StoplightUIElement(StoplightG1, StoplightG2);
        StoplightUIElement StoplightH = new StoplightUIElement(StoplightH1, StoplightH2);
        StoplightUIElement StoplightI = new StoplightUIElement(StoplightI1, StoplightI2);

        valuesByName.put("StoplightA", StoplightA);
        valuesByName.put("StoplightB", StoplightB);
        valuesByName.put("StoplightC", StoplightC);
        valuesByName.put("StoplightD", StoplightD);
        valuesByName.put("StoplightE", StoplightE);
        valuesByName.put("StoplightF", StoplightF);
        valuesByName.put("StoplightG", StoplightG);
        valuesByName.put("StoplightH", StoplightH);
        valuesByName.put("StoplightI", StoplightI);

        VALUES_BY_NAME = valuesByName;
    }
    public void changeLightColor(TrafficLight light) throws ClassNotFoundException {
        StoplightUIElement thisLight = VALUES_BY_NAME.get(light.Name);
        Image image = new Image("com/plp/signalingsystem/pictures/" + light.Status + ".png");

        if (thisLight.GridLight != null) {
            thisLight.GridLight.setImage(image);
        }
        if(thisLight.RoadLight != null) {
            thisLight.RoadLight.setImage(image);
        }
    }
}