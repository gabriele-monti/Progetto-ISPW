package it.foodmood.view.ui.gui;

import java.io.IOException;
import java.util.Objects;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiNavigator {
    private final Stage stage;

    public GuiNavigator(Stage stage){
        this.stage = stage;
    }

    public <T> T goTo(GuiPages page){
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(page.getPath())));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1440, 810);
            stage.setScene(scene);
            stage.show();
            return loader.getController(); // Restiuisco il controller della pagina
        } catch (IOException e) {
            throw new IllegalStateException("Errore nel caricamento della pagina: " + page, e);
        }
    }
}
