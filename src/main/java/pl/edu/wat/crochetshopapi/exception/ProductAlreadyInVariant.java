package pl.edu.wat.crochetshopapi.exception;

public class ProductAlreadyInVariant extends RuntimeException {
    public ProductAlreadyInVariant(String msg) {
        super(msg);
    }
}
