package pl.edu.wat.crochetshopapi.exception;

public class ClientAlreadyExistsException extends RuntimeException {
    public ClientAlreadyExistsException(String msg) {
        super(msg);
    }
}


