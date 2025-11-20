package it.foodmood.view.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GuiIngredients extends BaseGui {
    @FXML private Button btnCancel;

    @FXML private Button btnSave;

    @FXML private CheckBox cbCelery;

    @FXML private CheckBox cbCrustaceans;

    @FXML private CheckBox cbEggs;

    @FXML private CheckBox cbFish;

    @FXML private CheckBox cbGluten;

    @FXML private CheckBox cbLupin;

    @FXML private CheckBox cbMilk;

    @FXML private CheckBox cbMolluscs;

    @FXML private CheckBox cbMustard;

    @FXML private CheckBox cbNuts;

    @FXML private CheckBox cbPeanuts;

    @FXML private CheckBox cbSesame;

    @FXML private CheckBox cbSoy;

    @FXML private CheckBox cbSulphites;

    @FXML private ComboBox<?> cbUnit;

    @FXML private Label lblAllergen;

    @FXML private Label lblTotalCarbohydrates;

    @FXML private Label lblTotalFats;

    @FXML private Label lblTotalKcal;

    @FXML private Label lblTotalProteins;

    @FXML private TextField tfCarbhoydrates;

    @FXML private TextField tfFats;

    @FXML private TextField tfIngredientName;

    @FXML private TextField tfProteins;

    public GuiFactory factory;

    @FXML void onCancelIngredient(ActionEvent event) {

    }

    @FXML
    void onSaveIngredient(ActionEvent event) {

    }

    public void setFactory(GuiFactory factory){
        this.factory = factory;
    }

}
