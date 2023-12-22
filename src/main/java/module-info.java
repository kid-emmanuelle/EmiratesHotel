module fr.emirashotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires static java.naming;
    requires org.jboss.logging;
    requires jakarta.transaction;


    exports fr.emirashotel;
}