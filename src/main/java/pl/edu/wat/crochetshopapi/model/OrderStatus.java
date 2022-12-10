package pl.edu.wat.crochetshopapi.model;

public enum OrderStatus {
    WAITING_FOR_PAYMENT(0),
    ORDER_TO_BE_PROCESED(1),
    PREPARED_FOR_SHIPPING(2),
    ORDERS_SHIPPED(3);

    private int status;
    OrderStatus(int status){
        this.status = status;
    }
}
