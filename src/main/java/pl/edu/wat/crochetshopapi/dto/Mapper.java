package pl.edu.wat.crochetshopapi.dto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.wat.crochetshopapi.model.Product;
import pl.edu.wat.crochetshopapi.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class Mapper {
    private ProductService productService;

    public ProductDTO productDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
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