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
import javafx.scene.image.Image;

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

    private GuiFactory factory;

    private boolean passwordVisible = false;

    public GuiLoginView(){
        // costruttore vuoto richiesto da fxmlloader
    }

    public void setBoundary(LoginBoundary boundary){
        this.boundary = boundary;
    }

    public void setFactory(GuiFactory factory){
        this.factory = factory;
    }

    @FXML
    private void initialize(){
        tfPasswordVisible.setVisible(false);
        tfPasswordVisible.setManaged(false);

        tfPasswordVisible.managedProperty().bind(tfPasswordVisible.visibleProperty());
        pfPassword.managedProperty().bind(pfPassword.visibleProperty());

        tfPasswordVisible.textProperty().bindBidirectional(pfPassword.textProperty());

        updateToggleIcon();
    }

    @FXML
    private void onTogglePasswordClicked(){
        setPasswordVisible(!passwordVisible);
    }

    private void setPasswordVisible(boolean visible){
        this.passwordVisible = visible;

        tfPasswordVisible.setVisible(visible);
        pfPassword.setVisible(!visible);

        updateToggleIcon();

        if(visible){
            tfPasswordVisible.requestFocus();;
            tfPasswordVisible.positionCaret(tfPasswordVisible.getText().length());
        } else {
            pfPassword.requestFocus();
            pfPassword.positionCaret(pfPassword.getText().length());
        }
    }

    private void updateToggleIcon(){
        String iconName = passwordVisible ? "eye.png" : "eye_hidden.png";
        String path = "/icons/" + iconName;
        var url = getClass().getResource(path);
        if(url == null){
            System.err.print("Icona non trovata: " + path);
            return;
        }
        Image img = new Image(url.toExternalForm());
        ivToggle.setImage(img);
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

    @FXML
    private void onCreateAccountClicked(){
        factory.showRegistrationView();
    }
}
