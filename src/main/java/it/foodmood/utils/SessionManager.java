package it.foodmood.utils;

import java.util.HashMap;

import it.foodmood.domain.model.User;

public final class SessionManager {

    private static SessionManager instance;
    private final HashMap<String, Session> sessions;

    private SessionManager(){
        this.sessions = new HashMap <>();
    }

    public static synchronized SessionManager getInstance(){
        if(instance == null){
            instance = new SessionManager();
        }
        return instance;
    }

    public Session createSession(User user){
        Session session = new Session(user.getId().toString() ,user.getRole());
        sessions.put(session.getToken(), session);
        return session;
    }

    public Session getSessionByToken(String token) {
        Session session = sessions.get(token);
        if(session == null){
            return null;
        }
        if(session.isExpired()){
            sessions.remove(token);
            return null;
        }
        session.refresh();
        return session;
    }

    public boolean validToken(String token) {
        return getSessionByToken(token) != null;
    }

    public void terminateSession(String token) {
        sessions.remove(token);
    }

    public void cleanupExpiredSessions() {
        sessions.values().removeIf(Session::isExpired);
    }
}
