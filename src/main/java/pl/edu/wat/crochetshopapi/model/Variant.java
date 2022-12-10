package pl.edu.wat.crochetshopapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * List of product's variants
 */
@Entity
public class Variant {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @OneToMany(targetEntity = Product.class)
    List<Product> variants;
    private String material;
    private String color;

    /**
     * If author doesn't want to expose the same products as invidual
     * then he can group them by changing this variable on true
     */
    private boolean isSame;
}
