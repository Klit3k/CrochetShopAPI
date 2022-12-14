package pl.edu.wat.crochetshopapi.exception;

public class ImageAlreadyExistsException extends RuntimeException {
    public ImageAlreadyExistsException(String msg) {
        super(msg);
    }
}
