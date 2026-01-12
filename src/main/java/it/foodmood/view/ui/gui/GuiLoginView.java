package it.foodmood.view.ui.gui;

import it.foodmood.bean.ActorBean;
import it.foodmood.bean.LoginBean;
import it.foodmood.config.UserMode;
import it.foodmood.exception.AuthenticationException;
import it.foodmood.view.boundary.GuestAccessBoundary;
import it.foodmood.view.boundary.LoginBoundary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GuiLoginView {

    @FXML private VBox accessPage;

    @FXML private VBox loginPage;

    @FXML private Button btnCreateAccount;

    @FXML private Button btnLogin;

    @FXML private Button btnBack;

    @FXML private Button btnAccess;

    @FXML private Button btnGuest;

    @FXML private Label errorMessageLabel;

    @FXML private ImageView ivToggle;

    @FXML private PasswordField pfPassword;

    @FXML private TextField tfEmail;

    @FXML private TextField tfPasswordVisible;

    @FXML private VBox registrationBox;

    private UserMode userMode;

    private LoginBoundary boundary;

    private GuestAccessBoundary guestAccessBoundary = new GuestAccessBoundary();
    
    private GuiRouter router;

    private PasswordToggleController toggleController;

    private boolean startOnLogin = false;

    private ActorBean actor;

    public void setUser(ActorBean actor){
        this.actor = actor;
    }

    public GuiLoginView(){
        // costruttore vuoto richiesto da fxmlloader
    }

    public void setBoundary(LoginBoundary boundary){
        this.boundary = boundary;
    }

    public void setRouter(GuiRouter router){
        this.router = router;
    }

    public void setUserMode(UserMode userMode){
        this.userMode = userMode;
        configureVisibility();
    }

    public void setStartOnLogin(boolean start){
        this.startOnLogin = start;
        configureVisibility();
    }

    private void configureVisibility(){
        if(accessPage == null || loginPage == null) return;

        boolean requireAuthentication = (userMode != UserMode.CUSTOMER);

        if(!requireAuthentication){
            if(!btnBack.visibleProperty().isBound()){
                btnBack.visibleProperty().bind(loginPage.visibleProperty());
            }
        } else {
            if(btnBack.visibleProperty().isBound()){
                btnBack.visibleProperty().unbind();
            }
            btnBack.setVisible(false);
        }
        
        registrationBox.setVisible(!requireAuthentication);
        registrationBox.setManaged(!requireAuthentication); 

        accessPage.setVisible(!requireAuthentication);

        boolean goLogin = startOnLogin || requireAuthentication;
        showOnly(goLogin ? loginPage : accessPage);
    }

    @FXML
    private void initialize(){

        accessPage.managedProperty().bind(accessPage.visibleProperty());
        loginPage.managedProperty().bind(loginPage.visibleProperty());

        btnBack.managedProperty().bind(btnBack.visibleProperty());

        toggleController = new PasswordToggleController(pfPassword, tfPasswordVisible, ivToggle);

        configureVisibility();
    }

    @FXML
    private void onTogglePasswordClicked(){
        toggleController.toggle();
    }

    @FXML
    private void onLoginClicked(){
        errorMessageLabel.setText("");

        String email = tfEmail.getText();
        String password = toggleController.getPassword();

        if(email.isEmpty() || password.isEmpty()){
            errorMessageLabel.setText("L'indirizzo e-mail e la password sono obbligatori");
            return;
        }

        try {
            LoginBean loginBean = new LoginBean();

            loginBean.setEmail(email);
            loginBean.setPassword(password.toCharArray());

            boundary.login(loginBean);
            
            if(userMode != UserMode.CUSTOMER){
                router.showHomeView();
            } else {
                actor.setGuest(false);
                router.showSessionTableView();
            }

        } catch (IllegalArgumentException e){
            errorMessageLabel.setText(e.getMessage());
        } catch (AuthenticationException e) {
            errorMessageLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onCreateAccountClicked(){
        router.showRegistrationView();
    }

    @FXML
    void onLoginPage(ActionEvent event) {
        showOnly(loginPage);
    }

    @FXML
    void onRequireTableNumber(ActionEvent event) {
        guestAccessBoundary.enterAsGuest();
        actor.setGuest(true);
        router.showSessionTableView();
    }

    @FXML
    void onBackToAccessPage(ActionEvent event) {
        if(userMode == UserMode.CUSTOMER){
            showOnly(accessPage);
        }
    }

    private void showOnly(VBox paneToShow){
        if(userMode != UserMode.CUSTOMER){
            paneToShow = loginPage;
        }
        accessPage.setVisible(paneToShow == accessPage);
        loginPage.setVisible(paneToShow == loginPage);
    }

}
