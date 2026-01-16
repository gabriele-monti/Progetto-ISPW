package it.foodmood.view.ui.cli;

import java.util.List;
import java.util.stream.IntStream;

import it.foodmood.bean.OrderLineBean;

public final class TableOrder {
    
    private TableOrder(){
        // costruttore vuoto
    }

    public static List<String> orderTableHeaders(){
        return List.of("N°", "Nome", "Prezzo", "Quantità", "Subtotale");
    }

    public static List<Integer> orderColumnWidths(){
        return List.of(3, 30, 7, 9, 9);
    }

    public static List<List<String>> orderRows(List<OrderLineBean> orderLines){
        return IntStream.range(0, orderLines.size()).mapToObj(i -> {
            OrderLineBean orderLine = orderLines.get(i);
            String index = String.valueOf(i + 1);
            String name = orderLine.getProductName();
            String price = orderLine.getUnitPrice().toString();
            int quantity = orderLine.getQuantity();
            String subtotale = orderLine.getSubTotal().toString();

            String quantityStr = String.valueOf(quantity);

            return List.of(index, name, price, quantityStr, subtotale);
        }).toList();
    }
}
