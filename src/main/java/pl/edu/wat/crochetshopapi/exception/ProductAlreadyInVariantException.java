package pl.edu.wat.crochetshopapi.exception;

public class ProductAlreadyInVariantException extends RuntimeException {
    public ProductAlreadyInVariantException(String msg) {
        super(msg);
    }
}
