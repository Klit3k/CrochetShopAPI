package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.crochetshopapi.model.Product;
import pl.edu.wat.crochetshopapi.service.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @ResponseBody
    @PostMapping(value = "/product", params = {"name", "description", "price"})
    public ResponseEntity<HttpStatusCode> addNewProduct(@RequestParam String name,
                                                        @RequestParam String description,
                                                        @RequestParam int price) {
        productService.add(name, description, price);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @PutMapping(value = "/product", params = {"id", "name", "description", "price"})
    public ResponseEntity<HttpStatusCode> updateProduct(@RequestParam long id,
                                                        @RequestParam String name,
                                                        @RequestParam String description,
                                                        @RequestParam int price) {
        productService.update(id, name, description, price);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @DeleteMapping(value = "/product", params = "id")
    public ResponseEntity<HttpStatusCode> delProduct(@RequestParam long id) {
        return productService.del(id);
    }

    @ResponseBody
    @GetMapping(value = "/product", params = "id")
    public Product getProduct(@RequestParam long id) {
        return productService.get(id);
    }

    @ResponseBody
    @GetMapping(value = "/product")
    public List<Product> getProduct() {
        return productService.getAllProducts();
    }

    @PostMapping(value = "/product/photo-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProductPhoto(@RequestParam("file") MultipartFile file,
                                                @RequestParam("id") long id) throws IOException {
        return productService.uploadProductPhoto(id, file);
    }

    @PostMapping(value = "/product/additional-photo-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAdditionalProductPhotos(@RequestParam("file") MultipartFile file,
                                                           @RequestParam("id") long id) throws IOException {
        return productService.uploadAdditionalPhotos(id, file);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Integer> handleIOException() {
        return new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class})
    public ResponseEntity<Integer> handleNoUserFound() {
        return new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }
}
