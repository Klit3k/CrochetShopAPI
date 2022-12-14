package pl.edu.wat.crochetshopapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Cart {
    @Id
    private long id;

    //Nie dziala
    @OneToMany
    private List<Product> products;

    @OneToOne
    @MapsId
    @JsonBackReference
    private Client client;
}
