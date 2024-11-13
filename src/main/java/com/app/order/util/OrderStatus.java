package com.app.order.util;

public enum OrderStatus {
    CREATED{
        @Override
        public void checkState() {}
    },
    SHIPPING,
    COMPLETED,
    CANCELED;

    public void checkState(){
        throw new OrderCancelException("이미 취소되었거나 취소할 수 없는 상품입니다.");
    }
}
