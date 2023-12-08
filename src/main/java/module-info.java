module fr.emirashotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens fr.emirashotel to lombok;
    exports fr.emirashotel;
}