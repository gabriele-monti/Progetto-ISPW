package it.foodmood.view.boundary;

import it.foodmood.bean.RestaurantRoomBean;
import it.foodmood.bean.TableBean;
import it.foodmood.controller.application.RestaurantRoomController;
import it.foodmood.exception.RestaurantRoomException;

public class RestaurantRoomBoundary {
    private final RestaurantRoomController controller;

    public RestaurantRoomBoundary(){
        this.controller = new RestaurantRoomController();
    }

    public void createRestaurantRoom(RestaurantRoomBean restaurantRoomBean) throws RestaurantRoomException{
        controller.createRestaurantRoom(restaurantRoomBean);
    }

    public RestaurantRoomBean loadRestaurantRoom() throws RestaurantRoomException{
        return controller.loadRestaurantRoom();
    }

    public void addTable(TableBean tableBean) throws RestaurantRoomException{
        controller.addTable(tableBean);
    }

    public void moveTable(int tableId, int newRow, int newCol) throws RestaurantRoomException{
        controller.moveTable(tableId, newRow, newCol);
    }

    public void removeTable(int tableId) throws RestaurantRoomException{
        controller.removeTable(tableId);
    }

    public void removeAllTables() throws RestaurantRoomException{
        controller.removeAllTables();
    }
}
