package pl.edu.wat.crochetshopapi.exception;

public class PromoCodeAlreadyExistsException extends RuntimeException {
    public PromoCodeAlreadyExistsException(String msg) {
        super(msg);
    }
}
