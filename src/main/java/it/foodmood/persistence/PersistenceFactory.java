package it.foodmood.persistence;

import it.foodmood.persistence.dao.DishDAO;

public interface PersistenceFactory {
    DishDAO getDishDAO();
}
