package it.foodmood.controller.application;

import it.foodmood.bean.ActorBean;
import it.foodmood.domain.model.Guest;
import it.foodmood.utils.SessionManager;

public class GuestAccessController {

    public ActorBean enterAsGuest(){
        Guest guest = new Guest();
        SessionManager.getInstance().createGuestSession(guest);
        ActorBean actor = new ActorBean();
        actor.setName("Ospite");
        actor.setSurname("");
        actor.setGuest(true);
        actor.setLogged(false);
        return actor;
    }
}
