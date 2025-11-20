package it.foodmood.utils;

import it.foodmood.domain.model.User;

public final class SessionManager {

    private static SessionManager instance;
    private Session currentSession;
    private User currentUser;

    private SessionManager(){
        // costruttore vuoto
    }

    public static synchronized SessionManager getInstance(){
        if(instance == null){
            instance = new SessionManager();
        }
        return instance;
    }

    public Session createSession(User user){
        this.currentUser = user;
        this.currentSession = new Session(user.getId() ,user.getRole());
        return currentSession;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public Session getCurrentSession(){
        if(currentSession == null){
            return null;
        }
        if(currentSession.isExpired()){
            currentSession = null;
            return null;
        }
        currentSession.refresh();
        return currentSession;
    }

    public boolean isUserLoggedIn(){
        return getCurrentSession() != null;
    }

    public void terminateCurrentSession() {
        currentSession = null;
        currentUser = null;
    }
}
