package pl.edu.wat.crochetshopapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import pl.edu.wat.crochetshopapi.Configuration;

import java.io.ObjectInputFilter;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@Embeddable
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private double price;
    private String category;
    @OneToOne
    private Image productPhoto;
    @ManyToMany(cascade=CascadeType.ALL)
    private List<Image> additionalProductPhotos;
    @OneToMany
    @JsonManagedReference
    private List<Comment> comments;
}
