package it.foodmood.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.foodmood.bean.LoginBean;
import it.foodmood.config.PersistenceMode;
import it.foodmood.config.UserMode;
import it.foodmood.exception.AuthenticationException;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.utils.SessionManager;

class LoginControllerTest {

    @BeforeAll
    static void initDao(){
        DaoFactory.init(PersistenceMode.FILESYSTEM);
    }

    @BeforeEach
    void resetSession() {
        SessionManager.getInstance().terminateCurrentSession();
    }

    @Test
    void testLoginSuccessfully() {
        LoginController controller = new LoginController();

        LoginBean bean = new LoginBean();
        bean.setEmail("mariorossi@email.com");
        char[] password = "Password123".toCharArray();
        bean.setPassword(password);

        UserMode role = UserMode.CUSTOMER;

        Assertions.assertDoesNotThrow(() -> controller.login(bean, role));

        Assertions.assertNotNull(SessionManager.getInstance().getCurrentSession());

        Assertions.assertEquals(
                "mariorossi@email.com",
                SessionManager.getInstance().getCurrentUser().getEmail().toString()
        );
    }

    @Test
    void testLoginEmailNotFound() {
        LoginController controller = new LoginController();

        LoginBean bean = new LoginBean();
        bean.setEmail("emailnonvalida@email.com");
        char[] password = "Password123".toCharArray();
        bean.setPassword(password);

        UserMode role = UserMode.CUSTOMER;

        Assertions.assertThrows(AuthenticationException.class, () -> controller.login(bean, role));

        Assertions.assertNull(SessionManager.getInstance().getCurrentSession());
    }

    @Test
    void testLoginInvalidPassword() {
        LoginController controller = new LoginController();

        LoginBean bean = new LoginBean();
        bean.setEmail("mariorossi@email.com");
        char[] password = "password".toCharArray();
        bean.setPassword(password);

        UserMode role = UserMode.CUSTOMER;

        Assertions.assertThrows(AuthenticationException.class, () -> controller.login(bean, role));

        Assertions.assertNull(SessionManager.getInstance().getCurrentSession());
    }
}
