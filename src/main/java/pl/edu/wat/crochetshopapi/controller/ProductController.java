package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.crochetshopapi.dto.Mapper;
import pl.edu.wat.crochetshopapi.dto.ProductDTO;
import pl.edu.wat.crochetshopapi.model.Product;
import pl.edu.wat.crochetshopapi.service.ProductService;

import java.io.IOException;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private Mapper mapper;
    @ResponseBody
    @PostMapping(value = "/product", params = {"name", "description", "price"})
    public ResponseEntity<?> addNewProduct(@RequestParam String name,
                                           @RequestParam String description,
                                           @RequestParam double price) {
        return new ResponseEntity<>(productService.add(name, description, price), HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @PutMapping(value = "/product", params = {"productId", "name", "description", "price"})
    public ResponseEntity<?> updateProduct(@RequestParam long productId,
                                           @RequestParam String name,
                                           @RequestParam String description,
                                           @RequestParam float price) {
        if(name.isBlank() || description.isBlank() )
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(
                productService.update(productId, Product.builder()
                        .name(name)
                        .description(description)
                        .price(Math.round(price * 100.0) / 100.0)
                        .build()
        ), HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @DeleteMapping(value = "/product", params = "productId")
    public ResponseEntity<HttpStatusCode> delProduct(@RequestParam long productId) {
        productService.del(productId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @GetMapping(value = "/product", params = "productId")
    public ResponseEntity<?> getProduct(@RequestParam long productId) {
        return new ResponseEntity<>(mapper.productDTO(productService.get(productId)), HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @GetMapping(value = "/products")
    public List<ProductDTO> getProducts() {
        return mapper.productListDTO(productService.getAllProducts());
    }

    @PostMapping(value = "/product/photo-upload")
    public ResponseEntity<?> uploadProductPhoto(@RequestParam("productId") long productId,
                                                @RequestParam("imageId") long imageId) throws IOException {
        productService.addImage(productId, imageId);
        return new ResponseEntity<>("Image uploaded successfully", HttpStatusCode.valueOf(200));
    }
    //TODO: Not working yet
    @PostMapping(value = "/product/photo-main")
    public ResponseEntity<?> chooseMainPhoto(@RequestParam("productId") long productId,
                                                @RequestParam("imageId") long imageId) throws IOException {
        productService.chooseMainImage(productId, imageId);
        return new ResponseEntity<>("Main photo has been chosen successfully.", HttpStatusCode.valueOf(200));
    }
    //TODO: Not working yet
    @DeleteMapping(value = "/product/photo-remove")
    public ResponseEntity<?> delPhoto(@RequestParam("productId") long productId,
                                             @RequestParam("imageId") long imageId) throws IOException {
        productService.removeImage(productId, imageId);
        return new ResponseEntity<>("Photo has been deleted.", HttpStatusCode.valueOf(200));
    }

}
