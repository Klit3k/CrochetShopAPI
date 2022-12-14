package pl.edu.wat.crochetshopapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Address {
    @Id
    private long id;
    private String city;
    private String street;
    private String postalCode;
    private String houseNumber;

    @OneToOne
    @MapsId
    @JsonBackReference
    private Client client;
}
