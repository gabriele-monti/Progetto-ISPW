package it.foodmood.view.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class GuiManagmentIngredients {


    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDeleteIngredient;

    @FXML
    private Button btnModifyIngredient;

    @FXML
    private Button btnNewIngredient;

    @FXML
    private Button btnSave;

    @FXML
    private CheckBox cbCelery;

    @FXML
    private CheckBox cbCrustaceans;

    @FXML
    private CheckBox cbEggs;

    @FXML
    private CheckBox cbFish;

    @FXML
    private CheckBox cbGluten;

    @FXML
    private CheckBox cbLupin;

    @FXML
    private CheckBox cbMilk;

    @FXML
    private CheckBox cbMolluscs;

    @FXML
    private CheckBox cbMustard;

    @FXML
    private CheckBox cbNuts;

    @FXML
    private CheckBox cbPeanuts;

    @FXML
    private CheckBox cbSesame;

    @FXML
    private CheckBox cbSoy;

    @FXML
    private CheckBox cbSulphites;

    @FXML
    private ComboBox<?> cbUnit;

    @FXML
    private TableColumn<?, ?> colAllergens;

    @FXML
    private TableColumn<?, ?> colKcal;

    @FXML
    private TableColumn<?, ?> colMacros;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colUnit;

    @FXML
    private Label lblAllergen;

    @FXML
    private Label lblTotalCarbohydrates;

    @FXML
    private Label lblTotalFats;

    @FXML
    private Label lblTotalKcal;

    @FXML
    private Label lblTotalProteins;

    @FXML
    private AnchorPane paneForm;

    @FXML
    private AnchorPane paneList;

    @FXML
    private TableView<?> tableIngredients;

    @FXML
    private TextField tfCarbhoydrates;

    @FXML
    private TextField tfFats;

    @FXML
    private TextField tfIngredientName;

    @FXML
    private TextField tfProteins;

    @FXML
    private TextField tfResarchIngredient;

    @FXML
    void onAddNewIngredient(ActionEvent event) {
        showFormView();
    }

    @FXML
    void onCancelIngredient(ActionEvent event) {
        showListView();
    }

    @FXML
    void onSaveIngredient(ActionEvent event) {
        showListView();
    }

    public GuiFactory factory;

    public void setFactory(GuiFactory factory){
        this.factory = factory;
    }

    @FXML
    private void initialize(){
        showListView();
    }

    private void showListView(){
        paneList.setVisible(true);
        paneList.setManaged(true);

        paneForm.setVisible(false);
        paneForm.setManaged(false);

        // opzionale potrei aggiungere il clearForm...
    }

    private void showFormView(){
        paneList.setVisible(false);
        paneList.setManaged(false);

        paneForm.setVisible(true);
        paneForm.setManaged(true);
    }


}
