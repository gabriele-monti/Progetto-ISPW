package it.foodmood.controller.application;

import it.foodmood.domain.model.Guest;
import it.foodmood.utils.SessionManager;

public class GuestAccessController {
    public Guest enterAsGuest(){
        Guest guest = new Guest();
        SessionManager.getInstance().createGuestSession(guest);
        System.out.println(guest.getId());
        return guest;
    }
}
