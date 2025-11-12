package it.foodmood.persistence.dao;

import java.util.Optional;

import it.foodmood.domain.model.User;
import it.foodmood.domain.value.Email;

public interface AccountDao <T extends User>{
    Optional<T> findByEmail(Email email);
}
