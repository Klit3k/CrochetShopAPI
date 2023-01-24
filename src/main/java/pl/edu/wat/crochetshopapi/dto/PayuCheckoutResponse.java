package pl.edu.wat.crochetshopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class PayuCheckoutResponse implements Serializable {

    Map<String, String> status;
    String redirectUri;
    String orderId;
}
