package it.foodmood.view.ui.gui;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import it.foodmood.bean.DishBean;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GuiCard {
    
    @FXML private AnchorPane card;

    @FXML private Button productAddBtn;

    @FXML private ImageView productImageView;

    @FXML private Label productName;

    @FXML private Label productPrice;

    @FXML private Spinner<?> productSpinner;

    private DishBean dishBean;
    
    public void setData(DishBean dishBean){
        this.dishBean = dishBean;

        productName.setText(dishBean.getName());

        var price = dishBean.getPrice().setScale(2, RoundingMode.HALF_UP);
        NumberFormat number = NumberFormat.getCurrencyInstance(Locale.ROOT);
        productPrice.setText(number.format(price) + "€");

        if(dishBean.getImageUri() != null){
            try {
                Image image = new Image(dishBean.getImageUri(), true);
                productImageView.setImage(image);
            } catch (Exception e) {
                productImageView.setImage(null);
            }
        } else {
            productImageView.setImage(null);
        }

        productSpinner.setEditable(false);
        productAddBtn.setDisable(false);
    } 

    public DishBean getDish(){
        return dishBean;
    }
}
