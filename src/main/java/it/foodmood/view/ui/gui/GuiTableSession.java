package it.foodmood.view.ui.gui;

import it.foodmood.bean.EnterTableSessionBean;
import it.foodmood.bean.TableSessionBean;
import it.foodmood.exception.TableException;
import it.foodmood.exception.TableSessionException;
import it.foodmood.view.boundary.TableSessionBoundary;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GuiTableSession extends BaseGui{
    
    @FXML private Button btnNext;

    @FXML private Label errorMessageLabel;

    @FXML private TextField tfTableNumber;

    private GuiRouter router;

    private TableSessionBoundary boundary = new TableSessionBoundary();

    public void setRouter(GuiRouter router){
        this.router = router;
    }

    @FXML
    private void onHomePage(){
        errorMessageLabel.setText("");

        EnterTableSessionBean enterTableSessionBean = new EnterTableSessionBean();

        enterTableSessionBean.setTableNumber(tfTableNumber.getText().trim());

        try {

            int tableId = enterTableSessionBean.getTableId();

            TableSessionBean tableSessionBean = boundary.enterSession(tableId);
            
            router.showHomeCustumerView(tableSessionBean);

        } catch (IllegalArgumentException e){
            errorMessageLabel.setText(e.getMessage());
        } catch (TableException e){
            errorMessageLabel.setText(e.getMessage());
        } catch (TableSessionException e){
            errorMessageLabel.setText(e.getMessage());
        }
    }
}
