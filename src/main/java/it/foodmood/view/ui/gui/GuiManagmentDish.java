package it.foodmood.view.ui.gui;

import java.util.List;

import it.foodmood.bean.IngredientBean;
import it.foodmood.bean.MacronutrientsBean;
import it.foodmood.domain.value.Unit;
import it.foodmood.view.boundary.DishBoundary;
import it.foodmood.view.boundary.IngredientBoundary;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    @FXML private Button btnAddToTheDish;

    @FXML private Button btnCancel;

    @FXML private Button btnChangeQuantity;

    @FXML private Button btnDeleteDish;

    @FXML private Button btnImport;

    @FXML private Button btnNewDish;

    @FXML private Button btnRemoveImg;

    @FXML private Button btnRemoveIngredient;

    @FXML private Button btnSave;

    @FXML private Button btnUpdateDish;

    @FXML private Button btnUpdateDish1;

    @FXML private ComboBox<?> cbCategory;

    @FXML private ComboBox<?> cbProductType;

    @FXML private ComboBox<?> cbState;

    @FXML private ComboBox<?> cbStateForm;

    @FXML private ComboBox<?> cbType;

    @FXML private TableColumn<IngredientBean, String> colAllergensForm;

    @FXML private TableColumn<IngredientBean, String> colKcalForm;

    @FXML private TableColumn<IngredientBean, String> colMacrosForm;

    @FXML private TableColumn<?, ?> colName;

    @FXML private TableColumn<IngredientBean, String> colNameForm;

    @FXML private TableColumn<?, ?> colPrice;

    @FXML private TableColumn<?, ?> colState;

    @FXML private TableColumn<?, ?> colType;

    @FXML private TableColumn<IngredientBean, String> colUnitForm;

    @FXML private ImageView ivImage;

    @FXML private Label lblAllergens;

    @FXML private Label lblTotalCarbs;

    @FXML private Label lblTotalFats;

    @FXML private Label lblTotalKcal;

    @FXML private Label lblTotalProtein;

    @FXML private ListView<?> listDishIngredients;

    @FXML private AnchorPane paneForm;

    @FXML private AnchorPane paneList;

    @FXML private TextArea taDishDescription;

    @FXML private TableView<?> tableIngredients;

    @FXML private TableView<IngredientBean> tableIngredientsForm;

    @FXML private TextField tfAdvancedResarch;

    @FXML private TextField tfDishName;

    @FXML private TextField tfDishNameForm;

    @FXML private TextField tfPrice;

    @FXML private TextField tfPriceForm;

    @FXML private TextField tfResarchDish;

    private final IngredientBoundary ingredientBoundary = new IngredientBoundary();
    private final DishBoundary dishBoundary = new DishBoundary();

    private final ObservableList<IngredientBean> ingredientItems = FXCollections.observableArrayList();

    private GuiRouter router;

    public void setRouter(GuiRouter router){
        this.router = router;
    }

    @FXML
    void btnChangeQuantity(ActionEvent event) {

    }

    @FXML
    void onAddIngredient(ActionEvent event) {

    }

    @FXML
    void onAddNewDish(ActionEvent event) {
        showFormView();
    }

    @FXML
    void onCancel(ActionEvent event) {
        showListView();
    }

    @FXML
    void onImportImage(ActionEvent event) {

    }

    @FXML
    void onNewIngredient(ActionEvent event) {

    }

    @FXML
    void onRemoveImage(ActionEvent event) {

    }

    @FXML
    void onRemoveIngredient(ActionEvent event) {

    }

    @FXML
    void onSaveDish(ActionEvent event) {
        showListView();
    }

    @FXML
    void onSelectCategory(ActionEvent event) {

    }

    @FXML
    void onSelectSate(ActionEvent event) {

    }

    @FXML
    void onSelectType(ActionEvent event) {

    }

    @FXML
    void onUpdateDish(ActionEvent event) {

    }

    @FXML
    private void initialize(){
        initTable();
        loadIngredients();
        showListView();
    }

    private void showListView(){
        swap(paneList, paneForm);
    }

    private void showFormView(){
        swap(paneForm, paneList);
    }

    private void initTable(){
        colNameForm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        colUnitForm.setCellValueFactory(cellData -> {
            Unit unit = cellData.getValue().getUnit();
            String unitLabel = (unit == Unit.GRAM) ? "g" : "ml";
            return new SimpleStringProperty(unitLabel);
        });

        colKcalForm.setCellValueFactory(cellData -> {
            MacronutrientsBean macro = cellData.getValue().getMacronutrients();
            if(macro == null){
                return new SimpleStringProperty("-");
            }
            double kcal = macro.calculateKcal();
            return new SimpleStringProperty(String.format("%.1f", kcal));
        });

        colMacrosForm.setCellValueFactory(cellData -> {
            MacronutrientsBean m = cellData.getValue().getMacronutrients();
            if(m == null){
                return new SimpleStringProperty("-");
            }
            
            double protein = (m.getProtein() == null) ? 0.0 : m.getProtein();
            double carbohydrate = (m.getCarbohydrates() == null) ? 0.0 : m.getCarbohydrates();
            double fat = (m.getFat() == null) ? 0.0 : m.getFat();

            String text = String.format("%.1f / %.1f / %.1f", protein, carbohydrate, fat);
            
            return new SimpleStringProperty(text);
        });

        colAllergensForm.setCellValueFactory(cellData -> {
            List<String> allergens = cellData.getValue().getAllergens();
            String allergenStr = allergens.isEmpty() ? "ALLERGENI NON PRESENTI" : String.join(", " , allergens);
            return new SimpleStringProperty(allergenStr);
        });

        tableIngredientsForm.setItems(ingredientItems);
    }

    private void loadIngredients(){
        ingredientItems.clear();

        try {
            List<IngredientBean> listIngredient = ingredientBoundary.getAllIngredients();

            ingredientItems.addAll(listIngredient);
        } catch (Exception e) {
            showError("Errore durante il caricamento degli ingredienti: " + e.getMessage());
        }
    }
}
