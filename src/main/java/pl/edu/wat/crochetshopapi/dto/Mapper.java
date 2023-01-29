package pl.edu.wat.crochetshopapi.dto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.wat.crochetshopapi.model.Client;
import pl.edu.wat.crochetshopapi.model.Comment;
import pl.edu.wat.crochetshopapi.model.Order;
import pl.edu.wat.crochetshopapi.model.Product;
import pl.edu.wat.crochetshopapi.service.ProductService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

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

    public PayuBuyer clientToPayu(Client client) {
        return PayuBuyer.builder()
                .firstName(client.getName())
                .lastName(client.getSurname())
                .phone(client.getPhone())
                .language("pl")
                .delivery(
                        PayuDelivery.builder()
                                .city(client.getAddress().getCity())
                                .street(client.getAddress().getStreet())
                                .postalCode(client.getAddress().getPostalCode())
                                .build()
                )
                .build();
    }
    public ProductDTO productDTO(Product product) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment el : product.getComments()) {
            commentDTOList.add(commentDTO(el));
        }
        commentDTOList.sort(Comparator.comparing(CommentDTO::getCreationTime).reversed());

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
    public ClientIdDTO clientIdDTO(Client client){
        return ClientIdDTO.builder()
                .clientId(client.getId())
                .build();
    }
    public CommentDTO commentDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .author(authorDTO(comment.getAuthor()))
                .content(comment.getContent())
                .creationTime(comment.getCreationTime().format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm", new Locale("pl"))))
                .build();
    }

    public ClientDTO clientDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .email(client.getEmail())
                .phone(client.getPhone())
                .name(client.getName())
                .surname(client.getSurname())
                .role(client.getRole())
                .address(client.getAddress())
                .orders(client.getOrder())
                .cart(client.getCart())
                .build();
    }

    public List<ProductDTO> productListDTO(List<Product> allProducts) {
        return allProducts.stream()
                .filter(e -> !e.isReserved())
                .map(this::productDTO)
                .sorted(Comparator.comparing(ProductDTO::getId))
                .toList();
    }

    public OrderDTO orderDTO(Order order) {
        return OrderDTO.builder()
                .redirectUri(order.getRedirectUri())
                .id(order.getId())
                .status(order.getStatus())
                .payuOrderId(order.getPayuOrderId())
                .clientId(order.getClient().getId())
                .products(order.getProducts())
                .value(order.getValue())
                .build();
    }
    public List<OrderDTO> ordersListDTO(List<Order> orders) {
        return orders.stream()
                .map(this::orderDTO)
                .toList();
    }
}