package pl.edu.wat.crochetshopapi.exception;

public class CommentSpamException extends RuntimeException {
    public CommentSpamException(String msg) {
        super(msg);
    }
}
