module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.ludo to javafx.fxml;
    exports org.ludo;
}