package it.foodmood.view.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GuiManagmentDish extends BaseGui {

    private GuiFactory factory;

    public void setFactory(GuiFactory factory){
        this.factory = factory;
    }

    @FXML
    private Button btnAddNewIngredient;

    @FXML
    private Button btnAddToTheDish;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnChangeQuantity;

    @FXML
    private Button btnDeleteDish;

    @FXML
    private Button btnImport;

    @FXML
    private Button btnNewDish;

    @FXML
    private Button btnRimuovi;

    @FXML
    private Button btnRimuoviImg;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdateDish;

    @FXML
    private Button btnUpdateDish1;

    @FXML
    private ComboBox<?> cbCategory;

    @FXML
    private ComboBox<?> cbProductType;

    @FXML
    private ComboBox<?> cbState;

    @FXML
    private ComboBox<?> cbType;

    @FXML
    private TableColumn<?, ?> colAllergens;

    @FXML
    private TableColumn<?, ?> colKcal;

    @FXML
    private TableColumn<?, ?> colMacros;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colName1;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colState;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colUnita;

    @FXML
    private ImageView ivImage;

    @FXML
    private Label lblAllergeni;

    @FXML
    private Label lblCarbTotali;

    @FXML
    private Label lblGrassiTotali;

    @FXML
    private Label lblKcalTotali;

    @FXML
    private Label lblProtTotali;

    @FXML
    private ListView<?> listDishIngredients;

    @FXML
    private AnchorPane paneForm;

    @FXML
    private AnchorPane paneList;

    @FXML
    private TextArea taDishDescription;

    @FXML
    private TableView<?> tableIngredients;

    @FXML
    private TableView<?> tableIngredients1;

    @FXML
    private TextField tfAdvancedResarch;

    @FXML
    private TextField tfDishName;

    @FXML
    private TextField tfDishName1;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextField tfPrice1;

    @FXML
    private TextField tfResarchDish;

    @FXML
    void btnChangeQuantity(ActionEvent event) {
        // commento
    }

    @FXML
    void onAddNewDish(ActionEvent event) {
        showFormView();
    }

    @FXML
    void onAddIngredient(ActionEvent event) {
        // commento
    }

    @FXML
    void onCancel(ActionEvent event) {
        // commento
    }

    @FXML
    void onImportImage(ActionEvent event) {
        // commento
    }

    @FXML
    void onNewIngredient(ActionEvent event) {
        showListView();
    }

    @FXML
    void onRemoveImage(ActionEvent event) {
        // commento
    }

    @FXML
    void onRemoveIngredient(ActionEvent event) {
        // commento
    }

    @FXML
    void onSaveDish(ActionEvent event) {
        showListView();
    }

    @FXML
    void onUpdateDish(ActionEvent event) {
        // commento
    }

    @FXML
    private void initialize(){
        showListView();
    }

    private void showListView(){
        swap(paneList, paneForm);
    }

    private void showFormView(){
        swap(paneForm, paneList);
    }
}
