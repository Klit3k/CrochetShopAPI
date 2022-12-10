package pl.edu.wat.crochetshopapi.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.wat.crochetshopapi.Configuration;
import pl.edu.wat.crochetshopapi.exception.InvalidTypeOfFileException;
import pl.edu.wat.crochetshopapi.exception.ProductNotFoundException;
import pl.edu.wat.crochetshopapi.model.Product;
import pl.edu.wat.crochetshopapi.repository.ProductRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    public Product add(String name, String description, int price) {
        return productRepository.save(
                Product.builder()
                        .name(name)
                        .description(description)
                        //.productPhoto()
                        .price(price)
                        .build());
    }

    public void del(long id) {
        productRepository.delete(get(id));
    }

    public Product update(long id, String name, String description, int price) {
        Product product = get(id);
        if (!product.getName().equals(name)) product.setName(name);
        if (product.getPrice() != price) product.setPrice(price);
        if (!product.getDescription().equals(description)) product.setDescription(description);
        productRepository.save(product);
        return product;
    }

    public Product get(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found."));
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    //TODO: Reformat needed
    public void uploadProductPhoto(long id, MultipartFile file) throws IOException {
        File convertFile = new File(Configuration.IMAGES_PATH + file.getOriginalFilename());
        if (file.getContentType() == null || !file.getContentType().equals("image/png"))
            throw new InvalidTypeOfFileException("Invalid type of file.");
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        Product p = get(id);
        p.setProductPhoto(file.getBytes());
        fout.close();
        productRepository.save(p);
    }


    public void uploadAdditionalPhotos(long id, MultipartFile file) throws IOException {
        File convertFile = new File(Configuration.IMAGES_PATH + file.getOriginalFilename());
        if (file.getContentType() == null || !file.getContentType().equals("image/png"))
            throw new InvalidTypeOfFileException("Invalid type of file.");
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        Product p = get(id);
        p.getAdditionalProductPhotos().add(file.getBytes());
        p.setProductPhoto(file.getBytes());
        fout.close();
        productRepository.save(p);
    }
}
