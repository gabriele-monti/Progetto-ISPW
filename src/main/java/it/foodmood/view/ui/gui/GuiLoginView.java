package it.foodmood.view.ui.gui;

import it.foodmood.bean.LoginBean;
import it.foodmood.exception.AuthenticationException;
import it.foodmood.view.boundary.LoginBoundary;
import it.foodmood.view.ui.LoginView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class GuiLoginView implements LoginView {

    @FXML
    private Button btnCreateAccount;

    @FXML
    private Button btnLogin;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private ImageView ivToggle;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfPasswordVisible;

    private LoginBoundary boundary;

    public GuiLoginView(){
        // costruttore vuoto richiesto da fxmlloader
    }

    public void setBoundary(LoginBoundary boundary){
        this.boundary = boundary;
    }

    @FXML
    private void onLoginClicked(){
        errorMessageLabel.setText("");

        String email = tfEmail.getText();
        String password = pfPassword.getText();

        if(email.isEmpty() || password.isEmpty()){
            errorMessageLabel.setText("L'indirizzo e-mail e la password sono obbligatori");
            return;
        }

        try {
            LoginBean loginBean = new LoginBean();

            loginBean.setEmail(email);
            loginBean.setPassword(password.toCharArray());

            boundary.login(loginBean);
            onLoginSuccess();

        } catch (IllegalArgumentException e){
            errorMessageLabel.setText(e.getMessage());
        } catch (AuthenticationException e) {
            errorMessageLabel.setText(e.getMessage());
        }
    }

    @Override
    public void show(){
    }

    @Override
    public void onLoginSuccess(){
        // fai qualcosa
        errorMessageLabel.setText("LOGIN RIUSCITO");
    }
}
