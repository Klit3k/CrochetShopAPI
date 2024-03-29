package pl.edu.wat.crochetshopapi.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.edu.wat.crochetshopapi.model.Image;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductDTO {
    private long id;
    private String name;
    private String description;
    private double price;
    private List<CommentDTO> comments;
    private Image image;
    private List<Image> additionalImages;
}
