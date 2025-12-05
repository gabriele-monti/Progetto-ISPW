package it.foodmood.view.ui.gui;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.foodmood.bean.DishBean;
import it.foodmood.bean.IngredientBean;
import it.foodmood.bean.IngredientPortionBean;
import it.foodmood.bean.MacronutrientsBean;
import it.foodmood.domain.value.CourseType;
import it.foodmood.domain.value.DietCategory;
import it.foodmood.domain.value.DishState;
import it.foodmood.domain.value.Unit;
import it.foodmood.exception.DishException;
import it.foodmood.view.boundary.DishBoundary;
import it.foodmood.view.boundary.IngredientBoundary;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

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

    @FXML private ComboBox<DietCategory> cbCategory;

    @FXML private ComboBox<CourseType> cbProductType;

    @FXML private ComboBox<DishState> cbState;

    @FXML private ComboBox<DishState> cbStateForm;

    @FXML private ComboBox<CourseType> cbType;

    @FXML private TableColumn<IngredientBean, String> colAllergensForm;

    @FXML private TableColumn<IngredientBean, String> colKcalForm;

    @FXML private TableColumn<IngredientBean, String> colMacrosForm;

    @FXML private TableColumn<IngredientBean, String> colNameForm;

    @FXML private TableColumn<DishBean, String> colName;

    @FXML private TableColumn<DishBean, String> colPrice;

    @FXML private TableColumn<DishBean, String> colState;

    @FXML private TableColumn<DishBean, String> colType;

    @FXML private TableColumn<IngredientBean, String> colUnitForm;

    @FXML private ImageView ivImage;

    @FXML private Label lblAllergens;

    @FXML private Label lblTotalCarbs;

    @FXML private Label lblTotalFats;

    @FXML private Label lblTotalKcal;

    @FXML private Label lblTotalProtein;

    @FXML private ListView<IngredientPortionBean> listDishIngredients;

    @FXML private AnchorPane paneForm;

    @FXML private AnchorPane paneList;

    @FXML private TextArea taDishDescriptionForm;

    @FXML private TableView<DishBean> tableDishes;

    @FXML private TableView<IngredientBean> tableIngredientsForm;

    @FXML private TextField tfAdvancedResarch;

    @FXML private TextField tfDishName;

    @FXML private TextField tfDishNameForm;

    @FXML private TextField tfPrice;

    @FXML private TextField tfPriceForm;

    @FXML private TextField tfResarchDish;

    private final IngredientBoundary ingredientBoundary = new IngredientBoundary();
    private final DishBoundary dishBoundary = new DishBoundary();

    private final ObservableList<DishBean> allDishes = FXCollections.observableArrayList();
    private final ObservableList<IngredientBean> ingredientItems = FXCollections.observableArrayList();
    private final ObservableList<IngredientPortionBean> dishIngredients = FXCollections.observableArrayList();

    private GuiRouter router;

    private String currentImageUri;


    public void setRouter(GuiRouter router){
        this.router = router;
    }

    @FXML
    void btnChangeQuantity(ActionEvent event) {
        IngredientPortionBean selected = listDishIngredients.getSelectionModel().getSelectedItem();

        if(selected == null){
            showError("Seleziona un ingrediente del piatto dalla lista per modificare la quantità");
        }

        Double newQuantity = askQuantity(selected.getIngredient(), selected.getQuantity());

        if(newQuantity == null) return;

        try {
            selected.setQuantity(newQuantity);
            listDishIngredients.refresh();
            // updateNutritionalSummary();
            showInfo("Quantità modificata correttamente");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private void initTableDish(){
        colName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));

        colPrice.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPrice() != null ? cell.getValue().getPrice().toString() : ""));

        colState.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getState() != null ? cell.getValue().getState().description() : ""));

        colType.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCourseType() != null ? cell.getValue().getCourseType().description() : ""));

    }

    private Double askQuantity(IngredientBean ingredient, Double defaultValue) {
        Unit unit = ingredient.getUnit();
        String unitLabel = unit == Unit.GRAM ? "g" : "ml";
        
        TextInputDialog dialog = new TextInputDialog(
            defaultValue != null ? String.format("%.2f", defaultValue) : ""
        );

        dialog.setTitle("Quantità Ingrediente");
        dialog.setHeaderText("Specifica la quantità per: " + ingredient.getName());
        dialog.setContentText("Quantità in " + unitLabel + " :");

        Optional<String> result = dialog.showAndWait();
        if(result.isEmpty()){
            return null;
        }

        try {
            double quantity = Double.parseDouble(result.get().replace(",", "."));
            if(quantity <= 0){
                showError("La quantità deve essere maggiore di 0");
                return null;
            }
            return quantity;
        } catch (NumberFormatException e) {
            showError(e.getMessage());
            return null;
        }
    }

    @FXML
    void onAddIngredient(ActionEvent event) {
        ObservableList<IngredientBean> selectedIngredients = tableIngredientsForm.getSelectionModel().getSelectedItems();

        if(selectedIngredients == null || selectedIngredients.isEmpty()){
            showError("Seleziona almeno un ingrediente dalla tabella");
            return;
        }

        for(IngredientBean ingredient: selectedIngredients){
            boolean alredyAdded = dishIngredients.stream().anyMatch(i -> 
                i.getIngredient().getName().equals(ingredient.getName()) &&
                i.getUnit().equals(ingredient.getUnit().name()));

            if(alredyAdded){
                showError("L'ingrediente " + ingredient.getName() + " è già presente nel piatto");
                continue;
            }

            Double quantity = askQuantity(ingredient, 0.0);
            if(quantity == null){
                continue;
            }

            try {
                IngredientPortionBean portionBean = new IngredientPortionBean();
                portionBean.setIngredient(ingredient);
                portionBean.setQuantity(quantity);
                portionBean.setUnit(ingredient.getUnit().name());

                dishIngredients.add(portionBean);
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        }

        listDishIngredients.refresh();
        // updateNutritionalSummary();

        tableIngredientsForm.getSelectionModel().clearSelection();
    }

    @FXML
    void onAddNewDish(ActionEvent event) {
        clearForm();
        showFormView();
    }

    @FXML
    void onCancel(ActionEvent event) {
        clearForm();
        showListView();
    }

    @FXML
    void onImportImage(ActionEvent event) {
        Window window = ivImage.getScene() != null ? ivImage.getScene().getWindow() : null;
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Seleziona l'immagine");

        File selectedFile = fileChooser.showOpenDialog(window);

        if(selectedFile != null){
            try {
                String uri = selectedFile.toURI().toString();
                currentImageUri = uri;

                Image fxImage = new Image(uri, true); 
                ivImage.setImage(fxImage);
                
                
            } catch (Exception e) {
                showError("Errore durante il caricamento dell'immagine: " + e.getMessage());
                currentImageUri = null;
                ivImage.setImage(null);
            }
        }

    }

    @FXML
    void onDeleteDish(ActionEvent event) {
        DishBean selected = tableDishes.getSelectionModel().getSelectedItem();

        if(selected == null){
            showError("Seleziona un piatto da eliminare");
        }

        if(!showConfirmation("Conferma eliminazione", "Vuoi eliminare il piatto:\n" + selected.getName() + " ?")) {
            return;
        }

        try {
            dishBoundary.deleteDish(selected.getName());

            loadDishes();
            showInfo("Ingrediente eliminato correttamente.");
        } catch (Exception e) {
            showError("Errore durante l'eliminazione: " + e.getMessage());
        }
    }

    @FXML
    void onRemoveImage(ActionEvent event) {
        currentImageUri = null;
        ivImage.setImage(null);
    }

    @FXML
    void onRemoveIngredient(ActionEvent event) {
        IngredientPortionBean selected = listDishIngredients.getSelectionModel().getSelectedItem();

        if(selected == null){
            showError("Seleziona un ingrediente dal piatto da rimuovere");
        }

        dishIngredients.remove(selected);
        listDishIngredients.refresh();
        // updateNutritionalSummary();

    }

    @FXML
    void onSaveDish(ActionEvent event) {
        if(!ensureAuthenticated(router)) return;

        DishBean dishBean = new DishBean();

        try {
            String name = tfDishNameForm.getText();
            dishBean.setName(name);

            // null
            String description = taDishDescriptionForm.getText();
            dishBean.setDescription(description);

            CourseType courseType = cbProductType.getValue(); 
            dishBean.setCourseType(courseType);

            DietCategory dietCategory = cbCategory.getValue();
            dishBean.setDietCategory(dietCategory);

            DishState dishState = cbStateForm.getValue();
            dishBean.setState(dishState);

            String priceText = tfPriceForm.getText();
            BigDecimal price;
            priceText = priceText.replace(",", ".");
            price = new BigDecimal(priceText);
            dishBean.setPrice(price); 

            dishBean.setImageUri(currentImageUri);

            List<IngredientPortionBean> ingredientsList = new ArrayList<>(dishIngredients);
            dishBean.setIngredients(ingredientsList);

            dishBoundary.createDish(dishBean);
            loadDishes();
            showInfo("Piatto creato correttamente");
            showListView();

        } catch (IllegalArgumentException | DishException e) {
            showError(e.getMessage());
        } 
    }

    @FXML
    void onUpdateDish(ActionEvent event) {
        if(!ensureAuthenticated(router)) return;
        showInfo("Funzionalità non ancora implementata");
    }

    @FXML
    private void initialize(){
        initTableDish();
        initUnitComboBox();
        initTable();
        initDishIngredientsList();
        loadIngredients();
        loadDishes();
        showListView();
    }

    private void loadDishes(){
        try {
            List<DishBean> dishes = dishBoundary.getAllDishes();
            allDishes.setAll(dishes);
            tableDishes.setItems(allDishes);
        } catch (Exception e) {
            showError("Errore durante il caricamento degli ingredienti: " + e.getMessage());
        }
    }

    private void initUnitComboBox(){
        cbCategory.getItems().clear();
        cbCategory.getItems().addAll(DietCategory.values());

        cbProductType.getItems().clear();
        cbProductType.getItems().addAll(CourseType.values());

        cbStateForm.getItems().clear();
        cbStateForm.getItems().addAll(DishState.values());

        cbState.getItems().clear();
        cbState.getItems().addAll(DishState.values());

        cbState.setCellFactory(combo -> new ListCell<>(){
            @Override
            protected void updateItem(DishState item, boolean empty){
                super.updateItem(item, empty);
                setText((item == null || empty) ? null : item.description());
            }
        });

        cbType.getItems().clear();
        cbType.getItems().addAll(CourseType.values());

        cbType.setCellFactory(combo -> new ListCell<>(){
            @Override
            protected void updateItem(CourseType item, boolean empty){
                super.updateItem(item, empty);
                setText((item == null || empty) ? null : item.description());
            }
        });

        cbCategory.setCellFactory(combo -> new ListCell<>(){
            @Override
            protected void updateItem(DietCategory item, boolean empty){
                super.updateItem(item, empty);
                setText((item == null || empty) ? null : item.description());
            }
        });

        cbProductType.setCellFactory(combo -> new ListCell<>(){
            @Override
            protected void updateItem(CourseType item, boolean empty){
                super.updateItem(item, empty);
                setText((item == null || empty) ? null : item.description());
            }
        });

        cbStateForm.setCellFactory(combo -> new ListCell<>(){
            @Override
            protected void updateItem(DishState item, boolean empty){
                super.updateItem(item, empty);
                setText((item == null || empty) ? null : item.description());
            }
        });
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

    private void initDishIngredientsList(){
        listDishIngredients.setItems(dishIngredients);

        listDishIngredients.setCellFactory(listView -> new ListCell<>(){

            @Override
            protected void updateItem(IngredientPortionBean item, boolean empty){
                super.updateItem(item, empty);
                if(item == null || empty){
                    setText(null);
                } else {
                    String unitLabel = "GRAM".equals(item.getUnit()) ? "g" : "ml";
                    setText(item.getIngredient().getName() + " - " +
                    String.format("%.2f %s", item.getQuantity(), unitLabel));
                }
            }
        });
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

    private void clearForm(){
        tfDishNameForm.clear();
        tfPriceForm.clear();
        taDishDescriptionForm.clear();

        cbProductType.getSelectionModel().clearSelection();
        cbCategory.getSelectionModel().clearSelection();
        cbStateForm.getSelectionModel().clearSelection();
        listDishIngredients.getSelectionModel().clearSelection();
        tableIngredientsForm.getSelectionModel().clearSelection();

        currentImageUri = null;
        ivImage.setImage(null);

        dishIngredients.clear();

        lblTotalCarbs.setText("0");
        lblTotalFats.setText("0");
        lblTotalProtein.setText("0");
        lblTotalKcal.setText("0");
        lblAllergens.setText("-");

        tfAdvancedResarch.clear();
        tfDishName.clear();
        tfPrice.clear();
        tfResarchDish.clear();
    }
}
