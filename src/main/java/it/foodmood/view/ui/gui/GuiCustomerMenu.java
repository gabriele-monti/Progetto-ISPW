package it.foodmood.view.ui.gui;

import java.util.List;

import it.foodmood.bean.DishBean;
import it.foodmood.domain.model.User;
import it.foodmood.domain.value.CourseType;
import it.foodmood.view.boundary.DishBoundary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class GuiCustomerMenu extends BaseGui{
    
    @FXML private Button btnAccount;

    @FXML private ToggleButton btnAppetizer;

    @FXML private ToggleButton btnBeverage;

    @FXML private Button btnCart;

    @FXML private ToggleButton btnDessert;

    @FXML private ToggleButton btnFirstCourse;

    @FXML private ToggleButton btnFruit;

    @FXML private ToggleButton btnMainCourse;

    @FXML private ToggleButton btnSideDish;

    @FXML private Label lblUserInitials;

    @FXML private GridPane menuGridPane;

    private final ObservableList<DishBean> allDishes = FXCollections.observableArrayList();
    private final DishBoundary dishBoundary = new DishBoundary();
    private CourseType selectedType = null;

    private final ToggleGroup courseGroup = new ToggleGroup();
    private GuiRouter router;
    private User customer;

    @FXML
    private void initialize(){
        btnAppetizer.setToggleGroup(courseGroup);
        btnFirstCourse.setToggleGroup(courseGroup);
        btnMainCourse.setToggleGroup(courseGroup);
        btnSideDish.setToggleGroup(courseGroup);
        btnFruit.setToggleGroup(courseGroup);
        btnDessert.setToggleGroup(courseGroup);
        btnBeverage.setToggleGroup(courseGroup);

        btnAppetizer.setSelected(true);
        selectedType = CourseType.APPETIZER;

        courseGroup.selectedToggleProperty().addListener((obs, old, newT) -> {
            if(newT == null) courseGroup.selectToggle(old);
        });

        loadMenuDishes();
    }

    public void setUser(User customer){
        this.customer = customer;
        updateLabel();
    }

    private void setCourseFilter(CourseType type){
        this.selectedType = type;
        refreshMenuGrid();
    }

    @FXML 
    void onAppetizerClicked(ActionEvent action){
        setCourseFilter(CourseType.APPETIZER);
    }

    @FXML 
    void onFirstCourseClicked(ActionEvent action){
        setCourseFilter(CourseType.FIRST_COURSE);
    }

    @FXML 
    void onMainCourseClicked(ActionEvent action){
        setCourseFilter(CourseType.MAIN_COURSE);
    }

    @FXML
    void onSideDishClicked(ActionEvent action){
        setCourseFilter(CourseType.SIDE_DISH);
    }

    @FXML 
    void onFruitClicked(ActionEvent action){
        setCourseFilter(CourseType.FRUIT);
    }

    @FXML 
    void onDessertClicked(ActionEvent action){
        setCourseFilter(CourseType.DESSERT);
    }

    @FXML 
    void onBeverageClicked(ActionEvent action){
        setCourseFilter(CourseType.BEVERAGE);
    }

    private void updateLabel(){
        if(customer == null) return;

        String initials = getUserInitials(customer);

        if(lblUserInitials != null){
            lblUserInitials.setText(initials);
        }
    }

    public void setRouter(GuiRouter router){
        this.router = router;
    }

    @FXML
    void onAccountClicked(ActionEvent event) {
        router.showCustomerAccountView();

    }

    @FXML
    void onCartClicked(ActionEvent event) {
        showInfo("Funzionalità non ancora implementata");
    }

    @FXML
    void onBackToHomePage(MouseEvent event) {
        router.showHomeCustumerView();
    }

    private void loadMenuDishes(){
        try {
            List<DishBean> dishes = dishBoundary.getAllDishes();
            allDishes.setAll(dishes);
            refreshMenuGrid();
        } catch (Exception e) {
            showError("Errore durante il caricamente dei piatti: " + e.getMessage());
        }
    }

    private void refreshMenuGrid(){
        menuGridPane.getChildren().clear();

        List<DishBean> filtered = allDishes.stream().filter(d -> selectedType == null || d.getCourseType() == selectedType).toList();
        
        int card = 4;
        int col = 0;
        int row = 0;

        for(DishBean dish: filtered){
            try {
                FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/card.fxml")
                );

                AnchorPane cardNode = loader.load();
                double scale = 0.92;
                cardNode.setScaleX(scale);
                cardNode.setScaleY(scale);

                GuiCard cardController = loader.getController();
                cardController.setData(dish);

                menuGridPane.add(cardNode, col, row);
                GridPane.setMargin(cardNode, new Insets(5));

                col ++;
                if(col == card){
                    col = 0;
                    row++;
                }
            } catch (Exception e) {
                showError("Errore nell'inserimento dei prodotti nel menù: " + e.getMessage());
            }
        }
    }
}
