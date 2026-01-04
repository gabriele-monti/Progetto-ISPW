package it.foodmood.view.boundary;

import it.foodmood.controller.application.GuestAccessController;
import it.foodmood.domain.model.Guest;

public class GuestAccessBoundary {
    private final GuestAccessController guestAccessController;

    public GuestAccessBoundary(){
        this.guestAccessController = new GuestAccessController();
    }

    public Guest enterAsGuest(){
        return guestAccessController.enterAsGuest();
    }
}
