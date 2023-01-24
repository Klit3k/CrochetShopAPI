package pl.edu.wat.crochetshopapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String payuOrderId;

    @Column(columnDefinition="TEXT")

    private String redirectUri;
    @OneToMany
    private List<Product> products;

    private double value;

    @OneToOne
    @JsonBackReference
    private Client client;
}
