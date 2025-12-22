package it.foodmood.view.ui.gui;

import it.foodmood.domain.model.User;
import it.foodmood.domain.value.Allergen;
import it.foodmood.view.boundary.LoginBoundary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class GuiCustomerAccount extends BaseGui {

    @FXML private BorderPane accountPane;

    @FXML private BorderPane preferencesPane;

    @FXML private BorderPane cardPane;

    @FXML private BorderPane passwordPane;

    @FXML private Button btnAccount;

    @FXML private Button btnCart;

    @FXML private Button btnBack;

    @FXML private Button btnBackToHome;

    @FXML private Button btnLogout;

    @FXML private Button btnChangePassword;

    @FXML private Button btnFoodPreferences;

    @FXML private Button btnFidelityCard;

    @FXML private Button btnPersonalDetails;

    @FXML private Button btnSavePersonalDetails;

    @FXML private Button btnSavePreferences;

    @FXML private Label lblFullNameCard;

    @FXML private Label lblFullNameAccount;

    @FXML private Label lblUserInitials;

    @FXML private BorderPane personalDetailsPane;

    @FXML private TextField tfName;

    @FXML private TextField tfSurname;

    @FXML private Label errorMessageLabel;

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

    @FXML private ComboBox<Allergen> cbType;

    private LoginBoundary loginBoundary;

    public void setBoundary(LoginBoundary boundary){
        this.loginBoundary = boundary;
    }

    @FXML
    void onLogoutClicked(ActionEvent event) {
        loginBoundary.logout();
        router.showLoginView();
    }

    @FXML
    void onBackToHome(ActionEvent event) {
        router.showHomeCustumerView();
    }

    @FXML
    void onBackToHomePage(MouseEvent event) {
        router.showHomeCustumerView();
    }

    @FXML
    void onBackToAccount(ActionEvent event) {
        showAccountPage();
    }

    @FXML
    private void initialize(){
        showAccountPage();

        btnPersonalDetails.setOnAction( e -> showPersonalDetailsPage());

        btnChangePassword.setOnAction( e -> showChangePasswordPage());

        btnFidelityCard.setOnAction( e -> showFidelityCardPage());

        btnFoodPreferences.setOnAction( e -> showFoodPreferencesPage());

    }


    private GuiRouter router;

    public void setRouter(GuiRouter router){
        this.router = router;
    }
    private User customer;

    public void setUser(User customer){
        this.customer = customer;
        updateLabel();
    }

    private void updateLabel(){
        if(customer == null) return;

        String fullName = getUserFullName(customer);
        String initials = getUserInitials(customer);


        if(lblFullNameCard != null){
            lblFullNameCard.setText(fullName);
        }

        if(lblFullNameAccount != null){
            lblFullNameAccount.setText(fullName);
        }

        if(lblUserInitials != null && customer != null){
            lblUserInitials.setText(initials);
        }
    }

    private void showOnly(BorderPane paneToShow){
        accountPane.setVisible(false);
        accountPane.setManaged(false);

        personalDetailsPane.setVisible(false);
        personalDetailsPane.setManaged(false);

        passwordPane.setVisible(false);
        passwordPane.setManaged(false);

        cardPane.setVisible(false);
        cardPane.setManaged(false);

        preferencesPane.setVisible(false);
        preferencesPane.setManaged(false);

        paneToShow.setVisible(true);
        paneToShow.setManaged(true);
    }

    private void showAccountPage(){
        showOnly(accountPane);
    }

    private void showPersonalDetailsPage(){
        showOnly(personalDetailsPane);
    }

    private void showChangePasswordPage(){
        showOnly(passwordPane);
    }

    private void showFidelityCardPage(){
        showOnly(cardPane);
    }

    private void showFoodPreferencesPage(){
        showOnly(preferencesPane);
    }

    @FXML
    void onCartClicked(ActionEvent event) {
        showInfo("Funzionalità non ancora implementata");
    }

    @FXML
    void onAccountClicked(ActionEvent event) {
        showAccountPage();
    }
}
