package pl.edu.wat.crochetshopapi.model;

public enum PromoCodeStatus {
    ACTIVE(0),
    EXPIRED(1),
    USED(2),
    NOT_FOUND(3);

    PromoCodeStatus(int status) {
    }
}
