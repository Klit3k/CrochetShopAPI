package pl.edu.wat.crochetshopapi.model;

public class PromoCodeNotFoundException extends RuntimeException {
    public PromoCodeNotFoundException(String msg) {
        super(msg);
    }
}
