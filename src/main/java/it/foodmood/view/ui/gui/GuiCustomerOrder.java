package it.foodmood.view.ui.gui;

import java.util.List;

import it.foodmood.bean.DishBean;
import it.foodmood.domain.model.User;
import it.foodmood.view.boundary.DishBoundary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class GuiCustomerOrder extends BaseGui {
        
    @FXML private GridPane menuGridPane;

    @FXML private Button btnOrderSummary;

    @FXML private Button btnNextFirst;

    @FXML private Button btnBackSixth;    

    @FXML private Button btnNextSecond;

    @FXML private Button btnGenerate;

    @FXML private ToggleButton tbBudgetEconomic;

    @FXML private Label lblBudgetEconomic;

    @FXML private Label lblBudgetBalanced;

    @FXML private Label lblBudgetPremium;

    @FXML private Label lblUserInitials;

    @FXML private ToggleButton tbBudgetBalanced;

    @FXML private ToggleButton tbBudgetPremium;

    @FXML private ToggleButton tbBudgetNoLimit;

    @FXML private Label lblKcalLight;

    @FXML private Label lblKcalModerate;

    @FXML private Label lblKcalComplete;

    @FXML private ToggleButton tbKcalNoLimit;

    @FXML private ToggleButton tbKcalLight;

    @FXML private ToggleButton tbKcalModerate;

    @FXML private ToggleButton tbKcalComplete;

    @FXML private ToggleButton tbBeverage;

    @FXML private ToggleButton tbDessert;

    @FXML private ToggleButton tbFruit;

    @FXML private ToggleButton tbSideDish;

    @FXML private ToggleButton tbCelery;

    @FXML private ToggleButton tbCrustaceans;

    @FXML private ToggleButton tbEggs;

    @FXML private ToggleButton tbFish;

    @FXML private ToggleButton tbGluten;

    @FXML private ToggleButton tbLupin;

    @FXML private ToggleButton tbMilk;

    @FXML private ToggleButton tbMolluscs;

    @FXML private ToggleButton tbMustard;

    @FXML private ToggleButton tbNuts;

    @FXML private ToggleButton tbPeanuts;

    @FXML private ToggleButton tbSesame;

    @FXML private ToggleButton tbSoy;

    @FXML private ToggleButton tbSulphites;

    @FXML private ToggleButton tbAppetizer;

    @FXML private ToggleButton tbFirstCourse;

    @FXML private ToggleButton tbMainCourse;

    @FXML private Button btnBackFirst;

    @FXML private Button btnBackSecond;

    @FXML private Button btnBackThird;

    @FXML private Button btnBackFourth;

    @FXML private Button btnBackFifth;

    @FXML private Button btnNextThird;

    @FXML private Button btnNextFourth;

    @FXML private Button btnAccount;

    @FXML private Button btnCart;

    @FXML private BorderPane firstQuestionPane;

    @FXML private BorderPane secondQuestionPane;

    @FXML private BorderPane thirdQuestionPane;

    @FXML private BorderPane fourthQuestionPane;

    @FXML private BorderPane fifthQuestionPane;

    @FXML private BorderPane proposePane;

    @FXML private ToggleButton tbGlutenFree;

    @FXML private ToggleButton tbLactoseFree;

    @FXML private ToggleButton tbTraditional;

    @FXML private ToggleButton tbVegan;

    @FXML private ToggleButton tbVegetarian;

    private GuiRouter router;
    private ToggleGroup firstGroup;    
    private ToggleGroup kcalGroup;
    private ToggleGroup budgetGroup;
    private final DishBoundary dishBoundary = new DishBoundary();

    public void setRouter(GuiRouter router){
        this.router = router;
    }

    public void setUser(User customer){
        this.customer = customer;
        updateLabel();
    }

    @FXML
    void onBackToFirstQuestion(ActionEvent event) {
        showFirstQuestion();
    }

    @FXML
    void onBackToHome(ActionEvent event) {
        router.showHomeCustumerView();
    }

    @FXML
    void onBackToHomePage(MouseEvent event) {
        router.showHomeCustumerView();
    }

    private User customer;

    private void updateLabel(){
        if(lblUserInitials != null && customer != null){
            lblUserInitials.setText(getUserInitials(customer));
        }
    }

    private void showOnly(BorderPane paneToShow){
        firstQuestionPane.setVisible(false);
        firstQuestionPane.setManaged(false);

        secondQuestionPane.setVisible(false);
        secondQuestionPane.setManaged(false);

        thirdQuestionPane.setVisible(false);
        thirdQuestionPane.setManaged(false);

        fourthQuestionPane.setVisible(false);
        fourthQuestionPane.setManaged(false);

        fifthQuestionPane.setVisible(false);
        fifthQuestionPane.setManaged(false);

        proposePane.setVisible(false);
        proposePane.setManaged(false);

        paneToShow.setVisible(true);
        paneToShow.setManaged(true);
    }

    private void showFirstQuestion(){
        showOnly(firstQuestionPane);
    }

    private void showSecondQuestion(){
        showOnly(secondQuestionPane);
    }

    private void showThirdQuestion(){
        showOnly(thirdQuestionPane);
    }

    private void showFourthQuestion(){
        showOnly(fourthQuestionPane);
    }

    private void showFifthQuestion(){
        showOnly(fifthQuestionPane);
    }

    private void showProposePane(){
        showOnly(proposePane);
    }

    @FXML
    void onBackToSecondQuestion(ActionEvent event){
        showSecondQuestion();
    }

    @FXML
    void onBackToThirdQuestion(ActionEvent event){
        showThirdQuestion();
    }

    @FXML
    void onBackToFourthQuestion(ActionEvent event){
        showFourthQuestion();
    }

    @FXML
    void onAccountClicked(ActionEvent event) {
        if(customer != null){
            router.showCustomerAccountView();
        } else {
            showInfo("Devi effettura l'accesso per vedere la sezione Account");
        }
    }

    @FXML
    private void initialize(){
        updateLabel();
        showFirstQuestion();

        firstGroup = singleChoice(List.of(tbTraditional, tbVegan, tbVegetarian));

        multipleChoice(List.of(tbGlutenFree, tbLactoseFree));

        multipleChoice(List.of(tbAppetizer, tbFirstCourse, tbMainCourse, tbSideDish, tbFruit, tbDessert, tbBeverage));

        kcalGroup = singleChoice(List.of(tbKcalNoLimit, tbKcalLight, tbKcalModerate, tbKcalComplete));

        budgetGroup = singleChoice(List.of(tbBudgetNoLimit, tbBudgetEconomic, tbBudgetBalanced, tbBudgetPremium));

        btnNextFirst.setOnAction(this::onNextFirst);        
        btnBackSecond.setOnAction(e -> showFirstQuestion());
        btnNextSecond.setOnAction(this::onNextSecond);
        btnNextThird.setOnAction(this::onNextThird);
        btnNextFourth.setOnAction(this::onNextFourth);
    }

    @FXML
    private void onNextFirst(ActionEvent e){
        int count = 0;

        Toggle selectedDiet = firstGroup.getSelectedToggle();
        if(selectedDiet != null) count ++;

        if(tbGlutenFree.isSelected()) count ++;
        if(tbLactoseFree.isSelected()) count ++;

        if(count < 1){
            showError("Seleziona almeno un opzione per continuare");
            return;
        }
        showSecondQuestion();
    }

    @FXML
    private void onNextSecond(ActionEvent e){

        List<ToggleButton> courseButtons = List.of(tbAppetizer, tbFirstCourse, tbMainCourse, tbSideDish, tbFruit, tbDessert, tbBeverage);

        long selectedCount = courseButtons.stream().filter(ToggleButton::isSelected).count();

        if(selectedCount < 1){
            showError("Seleziona almeno un opzione per continuare");
            return;
        }
        showThirdQuestion();
    }

    @FXML
    private void onNextThird(ActionEvent e){
        showFourthQuestion();
    }

    @FXML
    private void onNextFourth(ActionEvent e){
        if(kcalGroup.getSelectedToggle() == null){
            showError("Seleziona un opzione per continuare");
            return;
        }
        showFifthQuestion();
    }

    @FXML
    private void onGenerate(ActionEvent e){
        if(budgetGroup.getSelectedToggle() == null){
            showError("Seleziona un opzione per continuare");
            return;
        }
        showProposePane();
        loadMenuDishes();
    }

    private final ObservableList<DishBean> allDishes = FXCollections.observableArrayList();


    private ToggleGroup singleChoice(List<ToggleButton> buttons){
        ToggleGroup group = new ToggleGroup();
        for(ToggleButton b : buttons){
            b.setToggleGroup(group);
        }
        return group;
    }

    private void multipleChoice(List<ToggleButton> buttons){
        for(ToggleButton b : buttons){
            b.setToggleGroup(null);
        }
    }

    @FXML
    void onCartClicked(ActionEvent event) {
        router.showCustomerRecapOrder();
    }

    @FXML
    void onSummary(ActionEvent event) {
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

        final int columns = 2;
        int col = 0;
        int row = 0;

        for(DishBean dish: allDishes){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/card.fxml"));
                AnchorPane cardNode = loader.load();

                GuiCard cardController = loader.getController();
                cardController.setData(dish);

                menuGridPane.add(cardNode, col, row);
                GridPane.setMargin(cardNode, new Insets(5));

                col ++;
                if(col == columns){
                    col = 0;
                    row++;
                }
            } catch (Exception e) {
                showError("Errore nell'inserimento dei prodotti nel menù: " + e.getMessage());
            }
        }
    }
}
