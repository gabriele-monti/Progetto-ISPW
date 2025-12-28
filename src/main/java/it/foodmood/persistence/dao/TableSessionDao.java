package it.foodmood.persistence.dao;

import java.util.UUID;

import it.foodmood.domain.model.TableSession;

public interface TableSessionDao {
    UUID enterSession(TableSession tableSession);
    void closeSession(int tableId);
}
