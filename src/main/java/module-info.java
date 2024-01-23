module fr.emirashotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires mysql.connector.java;
    requires com.fasterxml.jackson.databind;


    exports fr.emirashotel;
    exports fr.emirashotel.model;
    opens fr.emirashotel.controller;
    opens fr.emirashotel.model to javafx.base;
    
}