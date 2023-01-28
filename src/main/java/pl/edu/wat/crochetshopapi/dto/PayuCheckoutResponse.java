package pl.edu.wat.crochetshopapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
