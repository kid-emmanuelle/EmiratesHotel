module fr.emirashotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires mysql.connector.java;


    exports fr.emirashotel;
    opens fr.emirashotel.controller;
    
}