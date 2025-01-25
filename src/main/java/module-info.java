module org.example.mibocatafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.mibocatafx to javafx.fxml;
    exports org.example.mibocatafx;
}