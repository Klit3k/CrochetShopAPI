package pl.edu.wat.crochetshopapi.exception;

public class VariantAlreadyExistsException extends RuntimeException {
    public VariantAlreadyExistsException(String msg) {
        super(msg);
    }
}
