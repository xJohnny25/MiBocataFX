module org.example.mibocatafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;


    opens org.example.mibocatafx to javafx.fxml, org.hibernate.orm.core;
    exports org.example.mibocatafx;
    exports org.example.mibocatafx.dao;
    opens org.example.mibocatafx.dao to javafx.fxml, org.hibernate.orm.core;
    exports org.example.mibocatafx.controllers;
    opens org.example.mibocatafx.controllers to javafx.fxml, org.hibernate.orm.core;
    exports org.example.mibocatafx.util;
    opens org.example.mibocatafx.util to javafx.fxml, org.hibernate.orm.core;
    exports org.example.mibocatafx.models;
    opens org.example.mibocatafx.models to javafx.fxml, org.hibernate.orm.core;
    exports org.example.mibocatafx.service;
    opens org.example.mibocatafx.service to javafx.fxml, org.hibernate.orm.core;

}