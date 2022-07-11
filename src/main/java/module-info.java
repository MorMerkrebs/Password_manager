module com.example.cyberp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires validatorfx;
    requires java.sql;
    requires org.apache.commons.codec;
    requires java.desktop;

    opens com.example.cyberp to javafx.fxml;
    exports com.example.cyberp;
}