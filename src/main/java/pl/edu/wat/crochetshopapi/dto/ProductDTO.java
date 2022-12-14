package pl.edu.wat.crochetshopapi.dto;


import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.edu.wat.crochetshopapi.model.Comment;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductDTO {
    private long id;
    private String name;
    private String description;
    private int price;
    private List<CommentDTO> comments;
}
