package it.foodmood.bean;

import java.util.UUID;

public class TableSessionBean {
    private UUID tableSessionId;

    public TableSessionBean(){
        // Costruttore vuoto
    }

    public UUID getTableSessionId(){
        return tableSessionId;
    }

    public void setTableSessionId(UUID tableSessionId){
        if(tableSessionId == null){
            throw new IllegalArgumentException("L'ID della sessione non può essere nullo");
        }
        this.tableSessionId = tableSessionId;
    }
}