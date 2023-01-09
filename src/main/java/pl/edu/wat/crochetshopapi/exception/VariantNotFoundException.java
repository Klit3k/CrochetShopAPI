package pl.edu.wat.crochetshopapi.exception;

public class VariantNotFoundException extends RuntimeException{
    public VariantNotFoundException(String msg) {
        super(msg);
    }
}
