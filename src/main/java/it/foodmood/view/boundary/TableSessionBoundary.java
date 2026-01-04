package it.foodmood.view.boundary;

import java.util.UUID;

import it.foodmood.bean.TableSessionBean;
import it.foodmood.controller.application.TableSessionController;

public class TableSessionBoundary {
    
    private final TableSessionController tableSessionController;

    public TableSessionBoundary(){
        this.tableSessionController = new TableSessionController();
    }

    public TableSessionBean enterSession(int tableId){
        UUID sessionId = tableSessionController.enterSession(tableId);

        TableSessionBean response = new TableSessionBean();
        response.setTableId(tableId);
        response.setTableSessionId(sessionId);
        return response;
    }
}
