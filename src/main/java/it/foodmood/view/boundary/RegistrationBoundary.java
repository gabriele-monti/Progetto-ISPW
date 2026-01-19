package it.foodmood.view.boundary;

import it.foodmood.bean.RegistrationBean;
import it.foodmood.controller.CustomerRegistrationController;
import it.foodmood.exception.RegistrationException;

public class RegistrationBoundary {
    private final CustomerRegistrationController customerRegistrationController;

    public RegistrationBoundary(){
        this.customerRegistrationController = new CustomerRegistrationController();
    }

    public void registration(RegistrationBean registrationBean) throws RegistrationException{
        customerRegistrationController.registration(registrationBean);
    }
}
