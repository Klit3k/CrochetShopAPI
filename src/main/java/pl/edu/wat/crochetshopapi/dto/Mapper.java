package pl.edu.wat.crochetshopapi.dto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.wat.crochetshopapi.model.Client;
import pl.edu.wat.crochetshopapi.model.Comment;
import pl.edu.wat.crochetshopapi.model.Order;
import pl.edu.wat.crochetshopapi.model.Product;
import pl.edu.wat.crochetshopapi.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class Mapper {
    private ProductService productService;

    public PayuProduct productToPayu(Product product){
        return PayuProduct.builder()
                .quantity(1)
                .unitPrice((long) (product.getPrice()*100))
                .name(product.getName())
                .build();
    }
    public ProductDTO productDTO(Product product) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment el : product.getComments()) {
            commentDTOList.add(commentDTO(el));
        }
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .comments(commentDTOList)
                .image(product.getProductPhoto())
                .additionalImages(product.getAdditionalProductPhotos())
                .build();
    }

    public AuthorDTO authorDTO(Client client) {
        return AuthorDTO.builder()
                .name(client.getName())
                .surname(client.getSurname())
                .build();
    }

    public CommentDTO commentDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .author(authorDTO(comment.getAuthor()))
                .content(comment.getContent())
                .creationTime(comment.getCreationTime())
                .build();
    }

    public ClientDTO clientDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .email(client.getEmail())
                .name(client.getName())
                .surname(client.getSurname())
                .role(client.getRole())
                .address(client.getAddress())
                .orders(client.getOrder())
                .cart(client.getCart())
                .build();
    }

    public List<ProductDTO> productListDTO(List<Product> allProducts) {
        List<ProductDTO> dtoList = new ArrayList<>();
        for (Product el : productService.getAllProducts()) {
            dtoList.add(productDTO(el));
        }
        return dtoList;
    }

}