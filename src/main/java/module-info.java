module org.example.mibocatafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;


    opens org.example.mibocatafx to javafx.fxml;
    exports org.example.mibocatafx;
    exports org.example.mibocatafx.controllers;
    opens org.example.mibocatafx.controllers to javafx.fxml;
}