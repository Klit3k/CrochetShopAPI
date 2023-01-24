package pl.edu.wat.crochetshopapi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayuBuyer {
    String email;
    String phone;
    String firstName;
    String lastName;
    String language;
    PayuDelivery delivery;
}
