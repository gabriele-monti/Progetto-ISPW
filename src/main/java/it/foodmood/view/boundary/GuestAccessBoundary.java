package it.foodmood.view.boundary;

import it.foodmood.bean.ActorBean;
import it.foodmood.controller.GuestAccessController;

public class GuestAccessBoundary {
    private final GuestAccessController guestAccessController;

    public GuestAccessBoundary(){
        this.guestAccessController = new GuestAccessController();
    }

    public ActorBean enterAsGuest(){
        return guestAccessController.enterAsGuest();
    }
}
