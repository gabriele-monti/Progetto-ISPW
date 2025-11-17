module it.foodmood {
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // Database
    requires java.sql;

    opens it.foodmood.view.ui.gui to javafx.fxml;

    exports it.foodmood;
    exports it.foodmood.bean;
    exports it.foodmood.config;
    exports it.foodmood.domain.model;
    exports it.foodmood.domain.value;
    exports it.foodmood.controller.application;
    exports it.foodmood.view.ui;

}
