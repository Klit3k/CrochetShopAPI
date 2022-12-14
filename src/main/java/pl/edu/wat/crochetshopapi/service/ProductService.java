package pl.edu.wat.crochetshopapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.crochetshopapi.Configuration;
import pl.edu.wat.crochetshopapi.exception.ImageNotFoundException;
import pl.edu.wat.crochetshopapi.exception.ProductNotFoundException;
import pl.edu.wat.crochetshopapi.model.Image;
import pl.edu.wat.crochetshopapi.model.Product;
import pl.edu.wat.crochetshopapi.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageService imageService;

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

    public void chooseMainImage(long productId, long imageId) {
        Product product = get(productId);
        Image oldImage = new Image();
        boolean hasOld = false;
        Image newImage = imageService.get(imageId);
        if (product.getProductPhoto() != null)
            oldImage = product.getProductPhoto();
        if (!product.getAdditionalProductPhotos().contains(newImage))
            throw new ImageNotFoundException("Not found any image with this id.");
        product.getAdditionalProductPhotos().remove(newImage);
        product.setProductPhoto(newImage);
        if (product.getAdditionalProductPhotos().isEmpty())
            product.setAdditionalProductPhotos(new ArrayList<>());
        if (!hasOld)
            product.getAdditionalProductPhotos().add(oldImage);
        productRepository.save(product);
    }

    public void addImage(long productId, long imageId) {
        Product product = get(productId);

        if (product.getAdditionalProductPhotos().isEmpty())
            product.setAdditionalProductPhotos(new ArrayList<>());
        product.getAdditionalProductPhotos()
                .add(imageService.get(imageId));
        productRepository.save(product);
    }

    public void removeImage(long productId, long imageId) {
        Product product = get(productId);
        Image image = imageService.get(imageId);
        product.getAdditionalProductPhotos().remove(image);
        if(product.getProductPhoto() != null && product.getProductPhoto().equals(image))
            product.setProductPhoto(Configuration.DEFAULT_IMAGE);
        productRepository.save(product);
    }
}
