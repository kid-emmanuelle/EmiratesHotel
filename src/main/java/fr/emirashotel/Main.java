package fr.emirashotel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main  extends Application{
    public static void main(String [] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage)  {
        Pane root = new Pane();
        root.setPrefSize(800, 600);
        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

}
