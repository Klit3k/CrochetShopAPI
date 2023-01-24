package pl.edu.wat.crochetshopapi.model;

public enum OrderStatus {
    PENDING(0),
    WAITING_FOR_CONFIRMATION(1),
    COMPLETED(2),
    CANCELED(3);

    private int status;
    OrderStatus(int status){
        this.status = status;
    }
}
