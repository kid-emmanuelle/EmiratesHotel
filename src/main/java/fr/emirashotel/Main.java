package fr.emirashotel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.emirashotel.model.Employee;
import fr.emirashotel.model.SQLConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main  extends Application{

    public static void main(String [] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = Main.class.getResourceAsStream("/sql/config.json");

            SQLConfig sqlConfig = SQLConfig.fromJson(objectMapper.readTree(inputStream));
            DatabaseManager.create(sqlConfig);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Erreur : le pilote JDBC n'a pas pu être chargé.");
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur : la connexion à la base de données a échoué.");
            return;
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("Erreur : dans l'ouverture du fichier de config.");
            return;
        }
        launch(args);
    }

    @Override
    public void start(Stage window)  {

        AnchorPane root = new AnchorPane();
        // Size 
        window.setMaximized(true);
        double width = 1333;
        double height = 701;
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
        AnchorPane.setLeftAnchor(logo, (width - logo.getFitWidth()) / 2);
        logo.setLayoutY(0.05*height);

        // Buttons
        Button load = new Button("Connexion");
        load.setMinSize(width*0.2, height*0.1);
        AnchorPane.setLeftAnchor(load, (width - load.getMinWidth()) / 2);
        load.setLayoutY(height*0.55);
        load.setStyle(
                "-fx-background-radius: 10px; " +
                        "-fx-border-width: 4px; " +
                        "-fx-cursor: hand; " +
                        "-fx-base: #ee2211; " +
                        "-fx-padding: 8 15 15 15; " +
                        "-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0; " +
                        "-fx-background-color: " +
                        "linear-gradient(from 0% 93% to 0% 100%, #6dc9bb 0%, #44b6a5 100%), " +
                        "#8ae3d5, " +
                        "#a7e3da, " +
                        "radial-gradient(center 50% 50%, radius 100%, #afece2, #8deedf); " +
                        "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 ); " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 1.1em; " +
                        "-fx-cursor: hand; " +
                        "-fx-text-fill: #ffffff; "
        );
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
        window.setTitle("Emirates Hotel");
        window.sizeToScene();
        window.centerOnScreen();
        window.show();
    }

}