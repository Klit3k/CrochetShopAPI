package pl.edu.wat.crochetshopapi.exception;

public class ImageAlreadyExists extends RuntimeException {
    public ImageAlreadyExists(String msg) {
        super(msg);
    }
}
