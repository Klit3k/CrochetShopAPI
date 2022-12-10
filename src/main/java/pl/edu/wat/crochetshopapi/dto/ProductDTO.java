package pl.edu.wat.crochetshopapi.dto;


import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductDTO {
    private long id;
    private String name;
    private String description;
    private int price;
}
