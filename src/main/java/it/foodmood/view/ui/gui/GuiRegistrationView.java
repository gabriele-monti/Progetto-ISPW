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
import javafx.scene.image.ImageView;

public class GuiRegistrationView extends BaseGui implements RegistrationView {

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
    private GuiFactory factory;

    private PasswordToggleController passwordToggleController;
    private PasswordToggleController confirmPasswordToggleController;

    public GuiRegistrationView(){
        // costruttore vuooto
    }

    public void setBoundary(RegistrationBoundary boundary){
        this.boundary = boundary;
    }

    public void setFactory(GuiFactory factory){
        this.factory = factory;
    }

    @FXML
    private void initialize(){
        passwordToggleController = new PasswordToggleController(pfPassword, pfConfirmPassword, ivToggleConfirmPassword);
        confirmPasswordToggleController = new PasswordToggleController(pfPassword, pfConfirmPassword, ivToggleConfirmPassword);
    }

    @FXML
    private void onTogglePasswordClicked(){
        passwordToggleController.toggle();
    }

    @FXML
    private void onToggleConfirmPasswordClicked(){
        confirmPasswordToggleController.toggle();
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
        factory.showLoginView();
    }


    @Override
    public void show(){
        // comment
    }

    @Override
    public void onRegistrationSuccess(){
        showInfo("Account creato con successo!");
        factory.showLoginView();
    }
}
