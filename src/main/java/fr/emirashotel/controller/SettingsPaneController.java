package fr.emirashotel.controller;

import java.io.IOException;

import fr.emirashotel.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingsPaneController implements Initializable {

    @FXML
    private Button buttonCustomer;

    @FXML
    private Button buttonEmployee;

    @FXML
    private Button buttonHotelOrder;

    @FXML
    private Button buttonOverview;

    @FXML
    private Button buttonRestaurantOrder;

    @FXML
    private Button buttonSettings;

    @FXML
    private TextField url_field;

    @FXML
    private TextField user_field;

    @FXML
    private TextField password_field;

    @FXML
    private Button save;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonSettings.setStyle("-fx-background-color: #c1f3e1;");
        url_field.setText(DatabaseManager.getSqlConfig().getUrl());
        user_field.setText(DatabaseManager.getSqlConfig().getUsername());
        password_field.setText(DatabaseManager.getSqlConfig().getPassword());
    }

    @FXML
    void exit(MouseEvent event) {

    }

    @FXML
    void redirect(MouseEvent event) {
        Node source = (Node) event.getSource();
        // Récupérer l'ID de l'élément cliqué
        String id = source.getId();
        String name = "";
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        switch (id){
            case "buttonCustomer":
                name = "CustomerPane.fxml";
                break;  
            case "buttonEmployee":
                name = "EmployeePane.fxml";
                break;
            case "buttonHotelOrder":
                name = "HotelOrderPane.fxml";
                break;
            case "buttonOverview":
                name = "OverviewPane.fxml";
                break;
            case "buttonRestaurantOrder":
                name = "RestaurantOrderPane.fxml";
                break;
            case "buttonSettings":
                name = "SettingsPane.fxml";
                break;
        }
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"+name));
                Parent newroot=loader.load();
                Scene newscene = new Scene(newroot);
                window.setScene(newscene);
                window.setResizable(true);
                window.show();
            } catch ( IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    void select(MouseEvent event) {

    }

    @FXML
    void onClick(ActionEvent event) {
        DatabaseManager.getSqlConfig().setUrl(url_field.getText());
        DatabaseManager.getSqlConfig().setUsername(user_field.getText());
        DatabaseManager.getSqlConfig().setPassword(password_field.getText());
        try {
            DatabaseManager.getSqlConfig().save("/sql/config.json");
            DatabaseManager.destroy();
            DatabaseManager.create(DatabaseManager.getSqlConfig());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Erreur : le pilote JDBC n'a pas pu être chargé.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur : la connexion à la base de données a échoué.");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

