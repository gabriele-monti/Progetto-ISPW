package it.foodmood.domain.model;

public enum UserTypes {
    USER,
    GUEST,
    WAITER,
    MANAGER;

    public static UserTypes getFromString(String type){
        if(type == null) return null;
        switch (type.toUpperCase()) {
            case "USER":
                return USER;
            case "WAITER":
                return WAITER;
            case "MANAGER":
                return MANAGER;
            case "GUEST":
                return GUEST;
            default:
                throw new IllegalArgumentException("Tipo account non valido: " + type);
        }
    }
}
