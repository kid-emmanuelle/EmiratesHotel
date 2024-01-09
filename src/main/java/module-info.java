module fr.emirashotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;


    exports fr.emirashotel;
    opens fr.emirashotel.controller;
    
}