module fr.emirashotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires java.sql;


    opens fr.emirashotel to lombok;
    exports fr.emirashotel;
    opens fr.emirashotel.controller;
    
}