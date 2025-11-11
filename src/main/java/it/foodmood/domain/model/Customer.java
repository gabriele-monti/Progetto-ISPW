package it.foodmood.domain.model;

import java.util.UUID;

import it.foodmood.domain.value.Email;
import it.foodmood.domain.value.Person;
import it.foodmood.domain.value.Role;

public final class Customer extends User{

    public Customer(Person person, Email email){
        super(person, email, Role.CUSTOMER);
    }

    public Customer(UUID id, Person person, Email email){
        super(id, person, email, Role.CUSTOMER);
    }
}
