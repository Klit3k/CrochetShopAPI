package pl.edu.wat.crochetshopapi.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayuAuthorizeResponse implements Serializable {

    String access_token;
    String token_type;
    long expires_in;
    String grant_type;
}
