package it.foodmood.view.ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class GuiManagmentRestaurantRoom {
    @FXML
    private Button btnAddTable2;

    @FXML
    private Button btnAddTable4;

    @FXML
    private Button btnAddTable6;

    @FXML
    private Button btnAddTable8;

    @FXML
    private Button btnRemoveAllTables;

    @FXML
    private Button btnRemoveSelected;

    @FXML
    private Button btnSaveRoom;

    @FXML
    private Button btnToggleEditMode;

    @FXML
    private Pane floorPane;

    @FXML
    private Label lblSelectedTable;

    private GuiFactory factory;

    public void setFactory(GuiFactory factory){
        this.factory = factory;
    }
}
