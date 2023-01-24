package pl.edu.wat.crochetshopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.edu.wat.crochetshopapi.model.OrderStatus;
import pl.edu.wat.crochetshopapi.model.Product;

import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrderDTO  {
    private String redirectUri;
    private long id;
    private OrderStatus status;
    private String payuOrderId;
    private List<Product> products;
    private double value;
    private long clientId;
}
