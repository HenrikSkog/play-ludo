module org.ludo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens org.ludo to javafx.fxml;
    exports org.ludo;
    opens org.ludo.controllers to javafx.fxml;
    exports org.ludo.controllers;
    opens org.ludo.utils to com.google.gson;
    exports org.ludo.utils;
}