package com.app.order.util;

public class OrderStateComparator {
    public static OrderStatus comparator(String state){
        if (state.startsWith("\"") && state.endsWith("\"")) {
            state = state.substring(1, state.length() - 1);
        }

        if(state.equals(OrderStatus.CREATED.toString())) return OrderStatus.CREATED;
        else if(state.equals(OrderStatus.SHIPPING.toString())) return OrderStatus.SHIPPING;
        else if(state.equals(OrderStatus.COMPLETED.toString())) return OrderStatus.COMPLETED;
        else if(state.equals(OrderStatus.CANCELED.toString())) return OrderStatus.CANCELED;
        else throw new OrderException("상태 변경 오류");
    }
}
