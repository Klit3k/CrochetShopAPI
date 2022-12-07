package pl.edu.wat.crochetshopapi.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.edu.wat.crochetshopapi.model.Product;
import pl.edu.wat.crochetshopapi.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    ProductRepository productRepository;
    public Product add(String name, String description, int price) {
        return productRepository.save(
                Product.builder()
                        .name(name)
                        .description(description)
                        //.productPhoto()
                        .price(price)
                        .build());
    }
    public ResponseEntity<HttpStatusCode> del(long id) {
        productRepository.delete(productRepository.findById(id).orElseThrow());
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
    public Product update(long id, String name, String description, int price) {
        Product product = productRepository.findById(id).orElseThrow();
        if (product.getName() != name) product.setName(name);
        if (product.getPrice() != price) product.setPrice(price);
        if (product.getDescription() != description) product.setDescription(description);
        productRepository.save(product);
        return product;
    }
    public Product get(long id) {
        return productRepository.findById(id).orElseThrow();
    }
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

}
