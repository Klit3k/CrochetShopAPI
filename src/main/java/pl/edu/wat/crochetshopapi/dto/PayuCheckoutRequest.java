package pl.edu.wat.crochetshopapi.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayuCheckoutRequest implements Serializable {
    String customerIp;
    String merchantPosId;
    String description;
    String currencyCode;
    String totalAmount;
    PayuBuyer buyer;
    List<PayuProduct> products;
}
