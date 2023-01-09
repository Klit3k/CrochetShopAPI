package pl.edu.wat.crochetshopapi.exception;

public class PromoCodeNotFoundException extends RuntimeException {
    public PromoCodeNotFoundException(String msg) {
        super(msg);
    }
}
