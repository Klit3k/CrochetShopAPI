package pl.edu.wat.crochetshopapi.exception;

public class EmptyCartException extends RuntimeException {
    public EmptyCartException(String msg) {
        super(msg);
    }
}
