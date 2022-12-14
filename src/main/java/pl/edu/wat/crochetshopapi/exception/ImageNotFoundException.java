package pl.edu.wat.crochetshopapi.exception;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(String msg) {
        super(msg);
    }
}
