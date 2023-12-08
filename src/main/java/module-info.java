module fr.emirashotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.hibernate.orm.core;


    opens fr.emirashotel to lombok;
    exports fr.emirashotel;
}