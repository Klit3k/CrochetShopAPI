package pl.edu.wat.crochetshopapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wat.crochetshopapi.model.Product;
import pl.edu.wat.crochetshopapi.repository.ProductRepository;

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
    public boolean del(long id) {
        if (productRepository.findById(id).isEmpty())
            return false;
        else {
            productRepository.deleteById(id);
            return true;
        }
    }
    public Product update(long id, String name, String description, int price) {
        Product product = productRepository.findById(id).orElseThrow();
        if(product.getName() != name) product.setName(name);
        if(product.getPrice() != price) product.setPrice(price);
        if(product.getDescription() != description) product.setDescription(description);
        productRepository.save(product);
        return product;
    }
    public Product get(long id){
        return productRepository.findById(id).orElseThrow();
    }
}
