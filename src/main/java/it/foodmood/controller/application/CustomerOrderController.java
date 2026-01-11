package it.foodmood.controller.application;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import it.foodmood.bean.OrderBean;
import it.foodmood.bean.OrderLineBean;
import it.foodmood.domain.model.Dish;
import it.foodmood.domain.model.Order;
import it.foodmood.domain.model.OrderLine;
import it.foodmood.exception.OrderException;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.persistence.dao.DishDao;
import it.foodmood.persistence.dao.OrderDao;
import it.foodmood.persistence.dao.TableSessionDao;
import it.foodmood.utils.SessionManager;

public class CustomerOrderController {
    
    private final OrderDao orderDao;
    private final DishDao dishDao;
    private final TableSessionDao tableSessionDao;
    private final SessionManager sessionManager;

    public CustomerOrderController(){
        DaoFactory factory = DaoFactory.getInstance();
        this.sessionManager = SessionManager.getInstance();
        this.orderDao = factory.getOrderDao();
        this.dishDao = factory.getDishDao();
        this.tableSessionDao = factory.getTableSessionDao();
    }

    public String createOrder(OrderBean orderBean) throws OrderException{
        ensureActiveSession();

        if(orderBean == null) {
            throw new OrderException("L'ordine non può essere nullo.");
        }

        try {
            UUID actorId = sessionManager.getCurrentActor();
            UUID tableSessionId = UUID.fromString(orderBean.getTableSessionId());

            tableSessionDao.findById(tableSessionId).orElseThrow(() -> new OrderException("Sessione tavolo non valida"));

            List<OrderLineBean> orderLineBeans = orderBean.getOrderLines();

            List<OrderLine> orderLines = new ArrayList<>();

            for(OrderLineBean lineBean: orderLineBeans){
                if(lineBean == null){
                    throw new OrderException("Riga ordine nulla");
                }

                UUID dishId = UUID.fromString(lineBean.getDishId());
                int quantity = lineBean.getQuantity();
                
                Dish dish = dishDao.findById(dishId).orElseThrow(() -> new OrderException("Piatto non trovato"));

                orderLines.add(new OrderLine(dish.getId(), dish.getName(), dish.getPrice(), quantity));
            }

            Order order = new Order(actorId, tableSessionId, orderLines);

            orderDao.insert(order);

            return order.getId().toString();

        } catch (IllegalArgumentException | NullPointerException e){
            throw new OrderException("Errore durante l'inserimento dell'ordine: " + e.getMessage());
        }
    }

    public void ensureActiveSession(){
        sessionManager.requireActiveSession();
    }
}
