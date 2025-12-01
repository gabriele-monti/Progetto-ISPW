package it.foodmood.view.ui.gui;

import it.foodmood.bean.LoginBean;
import it.foodmood.exception.AuthenticationException;
import it.foodmood.view.boundary.LoginBoundary;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class GuiLoginView {

    @FXML private Button btnCreateAccount;

    @FXML private Button btnLogin;

    @FXML private Label errorMessageLabel;

    @FXML private ImageView ivToggle;

    @FXML private PasswordField pfPassword;

    @FXML private TextField tfEmail;

    @FXML private TextField tfPasswordVisible;

    private LoginBoundary boundary;
    
    private GuiRouter router;

    private PasswordToggleController toggleController;

    public GuiLoginView(){
        // costruttore vuoto richiesto da fxmlloader
    }

    public void setBoundary(LoginBoundary boundary){
        this.boundary = boundary;
    }

    public void setRouter(GuiRouter router){
        this.router = router;
    }

    @FXML
    private void initialize(){
        toggleController = new PasswordToggleController(pfPassword, tfPasswordVisible, ivToggle);
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
            
            router.showHomeView();

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
}
