module org.ludo {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.ludo to javafx.fxml;
    exports org.ludo;
}