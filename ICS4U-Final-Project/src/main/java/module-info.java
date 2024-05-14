module com.example.ics4ufinalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens com.example.ics4ufinalproject to javafx.fxml;
    exports com.example.ics4ufinalproject;
}