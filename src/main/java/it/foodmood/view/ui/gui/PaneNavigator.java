package it.foodmood.view.ui.gui;

import java.io.IOException;
import java.util.Objects;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class PaneNavigator {
    
    private final AnchorPane container;

    public PaneNavigator(AnchorPane container){
        this.container = container;
    }

    public <T> T show(GuiPages page){
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(page.getPath())));
            
            Node content = loader.load();
            T controller = loader.getController();

            container.getChildren().setAll(content);
            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);

            return controller;

        } catch (IOException _) {
            throw new IllegalStateException("Errore nel caricamento della pagina: " + page);
        }
    }
}
