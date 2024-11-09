package com.app.order.util;

public class OrderCancelException extends RuntimeException{
    public OrderCancelException(String errorMessage){
        super(errorMessage);
    }
}
