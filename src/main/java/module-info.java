module org.ludo {
    requires com.google.gson;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports org.ludo.gamelogic;
    opens org.ludo to javafx.fxml;
    exports org.ludo;
    opens org.ludo.controllers to javafx.fxml;
    exports org.ludo.controllers;
    exports org.ludo.filesaving;
    exports org.ludo.gamerendering;
}