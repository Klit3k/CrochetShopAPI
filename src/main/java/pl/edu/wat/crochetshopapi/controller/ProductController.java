package pl.edu.wat.crochetshopapi.controller;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.crochetshopapi.dto.Mapper;
import pl.edu.wat.crochetshopapi.dto.ProductDTO;
import pl.edu.wat.crochetshopapi.exception.ProductNotFoundException;
import pl.edu.wat.crochetshopapi.model.Product;
import pl.edu.wat.crochetshopapi.service.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

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
                                           @RequestParam int price) {
        return new ResponseEntity<>(productService.add(name, description, price), HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @PutMapping(value = "/product", params = {"id", "name", "description", "price"})
    public ResponseEntity<?> updateProduct(@RequestParam long id,
                                           @RequestParam String name,
                                           @RequestParam String description,
                                           @RequestParam int price) {
        return new ResponseEntity<>(productService.update(id, name, description, price), HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @DeleteMapping(value = "/product", params = "id")
    public ResponseEntity<HttpStatusCode> delProduct(@RequestParam long id) {
        productService.del(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @GetMapping(value = "/product", params = "id")
    public ProductDTO getProduct(@RequestParam long id) {
        return mapper.productDTO(productService.get(id));
    }

    @ResponseBody
    @GetMapping(value = "/product")
    public List<ProductDTO> getProducts() {
        return mapper.productListDTO(productService.getAllProducts());
    }

    @PostMapping(value = "/product/photo-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProductPhoto(@RequestParam("productId") long productId,
                                                @RequestParam("imageId") long imageId) throws IOException {
        productService.addImage(productId, imageId);
        return new ResponseEntity<>("Image uploaded successfully", HttpStatusCode.valueOf(200));
    }
    //TODO: Not working yet
    @PostMapping(value = "/product/photo-main", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
