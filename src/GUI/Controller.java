
package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.control.ListView;

public class Controller implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private TextField myTextField;

    @FXML
    private ListView<String> myListView;
    private ObservableList<String> listViewData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                listViewData.add(myTextField.getText());
                myListView.setItems(listViewData);
            }
        });
    }
}