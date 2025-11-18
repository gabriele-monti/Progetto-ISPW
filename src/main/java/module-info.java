module it.foodmood {
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // Database
    requires java.sql;

    opens it.foodmood.view.ui.gui to javafx.fxml;
}
