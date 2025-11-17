package it.foodmood.view.ui.gui;

import it.foodmood.bean.RegistrationBean;
import it.foodmood.exception.RegistrationException;
import it.foodmood.view.boundary.RegistrationBoundary;
import it.foodmood.view.ui.RegistrationView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GuiRegistrationView implements RegistrationView {

    @FXML
    private Button btnBackToLogin;

    @FXML
    private Button btnCreateAccount;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private ImageView ivTogglePassword;

    @FXML
    private ImageView ivToggleConfirmPassword;

    @FXML
    private PasswordField pfConfirmPassword;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPasswordVisible;

    @FXML
    private TextField tfConfirmPasswordVisible;

    @FXML
    private TextField tfSurname;

    private RegistrationBoundary boundary;
    private GuiNavigator navigator;

    private boolean passwordVisible = false;
    private boolean confirmPasswordVisible = false;

    public GuiRegistrationView(){
        // costruttore vuooto
    }

    public void setBoundary(RegistrationBoundary boundary){
        this.boundary = boundary;
    }

    public void setNavigator(GuiNavigator navigator){
        this.navigator = navigator;
    }

    @FXML
    private void initialize(){
        // password
        tfPasswordVisible.setVisible(false);
        tfPasswordVisible.setManaged(false);
        tfPasswordVisible.managedProperty().bind(tfPasswordVisible.visibleProperty());
        pfPassword.managedProperty().bind(pfPassword.visibleProperty());
        tfPasswordVisible.textProperty().bindBidirectional(pfPassword.textProperty());

        // conferma password
        tfConfirmPasswordVisible.setVisible(false);
        tfConfirmPasswordVisible.setManaged(false);
        tfConfirmPasswordVisible.managedProperty().bind(tfConfirmPasswordVisible.visibleProperty());
        pfConfirmPassword.managedProperty().bind(pfConfirmPassword.visibleProperty());
        tfConfirmPasswordVisible.textProperty().bindBidirectional(pfConfirmPassword.textProperty());
        updateToggleIcon();
    }

    private void updateToggleIcon(){
        String iconPass = passwordVisible ? "eye.png" : "eye_hidden.png";
        String iconConf = confirmPasswordVisible ? "eye.png" : "eye_hidden.png";

        var urlPass = getClass().getResource("/icons/" + iconPass);
        var urlConf = getClass().getResource("/icons/" + iconConf);

        if(urlPass != null){
            ivTogglePassword.setImage(new Image(urlPass.toExternalForm()));
        }
        if(urlConf != null){
            ivToggleConfirmPassword.setImage(new Image(urlConf.toExternalForm()));
        }
    }

    @FXML
    private void onTogglePasswordClicked(){
        setPasswordVisible(!passwordVisible);
    }

    @FXML
    private void onToggleConfirmPasswordClicked(){
        setConfirmPasswordVisible(!confirmPasswordVisible);
    }

    private void setPasswordVisible(boolean visible){
        passwordVisible = visible;
        tfPasswordVisible.setVisible(visible);
        pfPassword.setVisible(!visible);
        updateToggleIcon();
    }

    private void setConfirmPasswordVisible(boolean visible){
        confirmPasswordVisible = visible;
        tfConfirmPasswordVisible.setVisible(visible);
        pfConfirmPassword.setVisible(!visible);
        updateToggleIcon();
    }

    @FXML
    private void onCreateAccountClicked(){
        errorMessageLabel.setText("");

        String name = tfName.getText();
        String surname = tfSurname.getText();
        String email = tfEmail.getText();
        String password = pfPassword.getText();
        String confirmPassword = pfConfirmPassword.getText();

        try {
            RegistrationBean registrationBean = new RegistrationBean();

            registrationBean.setName(name);
            registrationBean.setSurname(surname);
            registrationBean.setEmail(email);
            registrationBean.setPassword(password.toCharArray());
            registrationBean.setConfirmPassword(confirmPassword.toCharArray());

            boundary.registration(registrationBean);
            onRegistrationSuccess();

        } catch (IllegalArgumentException e){
            errorMessageLabel.setText(e.getMessage());
        } catch (RegistrationException e) {
            errorMessageLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onBackToLoginClicked(){
        if(navigator != null){
            navigator.goTo(GuiPages.LOGIN);
        }
    }


    @Override
    public void show(){
        throw new UnsupportedOperationException("GUI non ancora implementata");
    }

    @Override
    public void displayError(String message){
        System.err.println("GUI ERROR: " + message);
    }

    @Override
    public void displaySuccess(String message){
        throw new UnsupportedOperationException("GUI non ancora implementata");
    }


    @Override
    public void onRegistrationSuccess(){
        navigator.goTo(GuiPages.LOGIN);
    }
}
