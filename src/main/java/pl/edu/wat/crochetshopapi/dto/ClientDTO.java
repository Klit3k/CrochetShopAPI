package pl.edu.wat.crochetshopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.edu.wat.crochetshopapi.model.Address;
import pl.edu.wat.crochetshopapi.model.Cart;
import pl.edu.wat.crochetshopapi.model.Order;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ClientDTO {
    private long id;
    private String email;
    private String name;
    private String surname;
    private String role;
    private Address address;
    private Cart cart;
    private String phone;
    private List<Order> orders;

}
