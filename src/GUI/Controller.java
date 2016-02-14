
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;

public class Controller implements Initializable {

    @FXML
    private ToggleButton toggleSwitch;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
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
}