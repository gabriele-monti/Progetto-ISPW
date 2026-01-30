package it.foodmood.bean;

import java.util.List;

import it.foodmood.domain.value.OrderStatus;

public class OrderBean {
    private String id;
    private String tableSessionId;
    private OrderStatus status;
    private List<OrderLineBean> orderLines;

    public OrderBean(){
        // Costruttore vuoto
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableSessionId() {
        return tableSessionId;
    }

    public void setTableSessionId(String tableSessionId) {
        this.tableSessionId = tableSessionId;
    }

    public OrderStatus getStatus(){
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderLineBean> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineBean> orderLines){
        this.orderLines = orderLines;
    }
}

