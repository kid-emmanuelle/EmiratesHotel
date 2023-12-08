package fr.emirashotel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main  extends Application{
    public static void main(String [] args) {
        launch(args);


    }

    @Override
    public void start(Stage window)  {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 200);
        window.setScene(scene);

        window.setMaximized(true);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        double widthPercentage = 0.8;
        double heightPercentage = 0.6;

        window.setWidth(screenWidth * widthPercentage);
        window.setHeight(screenHeight * heightPercentage);

        String currentPath = System.getProperty("user.dir");
        System.out.println("Chemin actuel : " + currentPath);
        Image image = new Image(String.valueOf(this.getClass().getResource("/img/img.jpg")));
        ImageView imageView = new ImageView(image);

        root.getChildren().add(imageView);
        System.out.println(this.getClass().getResource(""));

        window.setTitle("JavaFX Full Screen Example");
        window.show();
    }

}