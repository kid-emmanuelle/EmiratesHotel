package fr.emirashotel;

import java.io.IOException;
import java.sql.SQLException;

import fr.emirashotel.model.Booking;
import fr.emirashotel.model.Employee;
import fr.emirashotel.model.Person;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main  extends Application{

    public static DatabaseManager databaseManager;
    public static void main(String [] args) {
        try {
            databaseManager = new DatabaseManager();
            databaseManager.create();

            databaseManager.getEmployees();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Erreur : le pilote JDBC n'a pas pu être chargé.");
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur : la connexion à la base de données a échoué.");
            return;
        }
        launch(args);
    }

    @Override
    public void start(Stage window)  {
        AnchorPane root = new AnchorPane();
        // Size 
        window.setMaximized(true);
        double width = Screen.getPrimary().getVisualBounds().getWidth();
        double height = Screen.getPrimary().getVisualBounds().getHeight();
        window.setWidth(width);
        window.setHeight(height);

        // Scene
        ImageView backgroundImage = new ImageView(new Image(getClass().getResourceAsStream("/img/fond.jpg")));
        backgroundImage.setFitWidth(width);
        backgroundImage.setFitHeight(height);
        root.getChildren().add(backgroundImage);
        Scene scene = new Scene(root, width*0.95, height*0.95);
        window.setScene(scene);

        // Logo
        Image image = new Image(String.valueOf(this.getClass().getResource("/img/Emirates_Hotel.png")));
        ImageView logo = new ImageView(image);
        logo.setFitWidth(image.getHeight()*1.35);
        logo.setFitHeight(image.getHeight()*1.35);
        logo.setLayoutX(0.39*width);
        logo.setLayoutY(0.05*height);

        // Buttons
        Button load = new Button("Connexion");
        load.setMinSize(width*0.2, height*0.1);
        load.setLayoutX(width*0.39);
        load.setLayoutY(height*0.55);
        // Button click event
        load.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/OverviewPane.fxml"));
                Parent newroot = loader.load();
                Scene newscene = new Scene(newroot);
                window.setScene(newscene);
                window.setResizable(true);
                window.show();
            } catch ( IOException e) {
                e.printStackTrace();
            }
        });
        root.getChildren().add(load);
        root.getChildren().add(logo);
        window.setTitle("App");
        window.sizeToScene();
        window.show();
    }

    
}