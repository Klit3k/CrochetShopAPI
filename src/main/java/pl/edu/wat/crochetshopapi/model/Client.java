package pl.edu.wat.crochetshopapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String name;
    private String surname;

    @OneToOne(targetEntity = Address.class)
    @JsonManagedReference
    private Address address;

    @OneToOne(targetEntity = Cart.class, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Cart cart;

    @OneToMany(targetEntity = Order.class)
    private List<Order> order;
}
