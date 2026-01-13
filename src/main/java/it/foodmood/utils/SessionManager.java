package it.foodmood.utils;

import java.util.UUID;

import it.foodmood.domain.model.Guest;
import it.foodmood.domain.model.User;
import it.foodmood.exception.SessionExpiredException;

public final class SessionManager {

    private static SessionManager instance;
    private Session currentSession;

    private User currentUser;
    private Guest currentGuest;

    private SessionManager(){
        // costruttore vuoto
    }

    public static synchronized SessionManager getInstance(){
        if(instance == null){
            instance = new SessionManager();
        }
        return instance;
    }

    public Session createUserSession(User user){
        if(user == null){
            throw new IllegalArgumentException("L'utente non può essere nullo");
        }
        terminateCurrentSession();
        this.currentUser = user;
        this.currentSession = new Session(user.getId());
        return currentSession;
    }

    public Session createGuestSession(Guest guest){
        if(guest == null){
            throw new IllegalArgumentException("L'ospite non può essere nullo");
        }
        terminateCurrentSession();
        this.currentGuest = guest;
        this.currentSession = new Session(guest.getId());
        return currentSession;
    }

    public User getCurrentUser(){
        Session session = getCurrentSession();
        if(session == null){
            return null;
        }
        return currentUser;
    }

    public UUID getCurrentActor(){
        Session session = getCurrentSession();
        if(session == null){
            return null;
        }
        if(currentUser != null){
            return currentUser.getId();
        } else {
            return currentGuest.getId();
        }
    }

    public Guest getCurrentGuest(){
        Session session = getCurrentSession();
        if(session == null){
            return null;
        }
        return currentGuest;
    }

    public Session getCurrentSession(){
        if(currentSession == null){
            return null;
        }

        if(currentSession.isExpired()){
            terminateCurrentSession();
            return null;
        }
        
        currentSession.refresh();
        return currentSession;
    }

    public boolean hasActiveSession(){
        return getCurrentSession() != null;
    }

    public void terminateCurrentSession() {
        currentSession = null;
        clear();
    }

    public Session requireActiveSession(){
        Session session = getCurrentSession();
        if(session == null){
            throw new SessionExpiredException();
        }
        return session;
    }

    public boolean isGuest(){
        return getCurrentSession() != null && currentGuest != null;
    }

    private void clear(){
        currentUser = null;
        currentGuest = null;
    }
}
