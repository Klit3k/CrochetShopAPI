package pl.edu.wat.crochetshopapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "store_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private OrderStatus status;

    @OneToMany
    private List<Product> products;

    private double value;

    @OneToOne
    @JsonBackReference
    private Client client;
}
