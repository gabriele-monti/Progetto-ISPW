package it.foodmood.view.ui.gui;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class GuiCustomerOrder extends BaseGui {

    @FXML private Button btnNextFirst;

    @FXML private Button btnNextSecond;

    @FXML private Button btnGenerate;

    @FXML private ToggleButton tbBudgetEconomic;

    @FXML private ToggleButton tbBudgetBalanced;

    @FXML private ToggleButton tbBudgetPremium;

    @FXML private ToggleButton tbBudgetNoLimit;

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

    @FXML private BorderPane firstQuestionPane;

    @FXML private BorderPane secondQuestionPane;

    @FXML private BorderPane thirdQuestionPane;

    @FXML private BorderPane fourthQuestionPane;

    @FXML private BorderPane fifthQuestionPane;

    @FXML private ToggleButton tbGlutenFree;

    @FXML private ToggleButton tbLactoseFree;

    @FXML private ToggleButton tbTraditional;

    @FXML private ToggleButton tbVegan;

    @FXML private ToggleButton tbVegetarian;

    private GuiRouter router;

    private ToggleGroup firstGroup;

    public void setRouter(GuiRouter router){
        this.router = router;
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
    private void initialize(){
        showFirstQuestion();

        firstGroup = singleChoice(List.of(tbTraditional, tbVegan, tbVegetarian));

        multipleChoice(List.of(tbGlutenFree, tbLactoseFree));

        multipleChoice(List.of(tbAppetizer, tbFirstCourse, tbMainCourse, tbSideDish, tbFruit, tbDessert, tbBeverage));

        btnNextFirst.setOnAction(this::onNextFirst);
        // btnBackFirst.setOnAction(this::onBackToHome);
        
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

        List<ToggleButton> courseButtons = List.of(tbKcalNoLimit, tbKcalLight, tbKcalModerate, tbKcalComplete);

        long selectedCount = courseButtons.stream().filter(ToggleButton::isSelected).count();

        if(selectedCount < 1){
            showError("Seleziona almeno un opzione per continuare");
            return;
        }

        showFifthQuestion();
    }

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
}
