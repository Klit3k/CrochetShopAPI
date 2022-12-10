package pl.edu.wat.crochetshopapi.exception;

public class ClientAlreadyExists extends RuntimeException {
    public ClientAlreadyExists(String msg) {
        super(msg);
    }
}


