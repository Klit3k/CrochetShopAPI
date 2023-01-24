package pl.edu.wat.crochetshopapi.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayuProduct implements Serializable {
    String name;
    long unitPrice;
    int quantity;
}
