package pl.edu.wat.crochetshopapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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
    private int price;
    private String category;
    @OneToOne
    private Image productPhoto;
    @OneToMany
    private List<Image> additionalProductPhotos;
    @OneToMany
    @JsonManagedReference
    private List<Comment> comments;
}
