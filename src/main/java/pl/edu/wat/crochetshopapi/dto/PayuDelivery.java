package pl.edu.wat.crochetshopapi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayuDelivery {
    private String city;
    private String street;
    private String postalCode;

}
