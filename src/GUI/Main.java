package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<ActionEvent> {

    Button close;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        primaryStage.setTitle("Hello World");

        close = new Button();
        close.setText("Close");
        close.setOnAction(this);

        StackPane layout = new StackPane();
        layout.getChildren().add(close);

        primaryStage.setScene(new Scene(layout, 300, 275));

        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent evt) {
        if(evt.getSource() == close) {
            System.exit(0);
        }
    }
}
