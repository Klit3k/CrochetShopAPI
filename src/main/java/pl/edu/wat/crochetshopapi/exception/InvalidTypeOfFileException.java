package pl.edu.wat.crochetshopapi.exception;

public class InvalidTypeOfFileException extends RuntimeException{
    public InvalidTypeOfFileException(String msg){
        super(msg);
    }
}
