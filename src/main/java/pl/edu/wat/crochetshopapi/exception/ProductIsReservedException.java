package pl.edu.wat.crochetshopapi.exception;

public class ProductIsReservedException extends RuntimeException{
    public ProductIsReservedException(String msg) {
        super(msg);
    }
}
