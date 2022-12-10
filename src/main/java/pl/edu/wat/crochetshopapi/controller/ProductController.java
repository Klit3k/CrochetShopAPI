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
    public List<ProductDTO> getProduct() {
        return mapper.productListDTO(productService.getAllProducts());
    }

    @PostMapping(value = "/product/photo-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProductPhoto(@RequestParam("file") MultipartFile file,
                                                @RequestParam("id") long id) throws IOException {
        productService.uploadProductPhoto(id, file);
        return new ResponseEntity<>("Image uploaded successfully", HttpStatusCode.valueOf(200));
    }

    @PostMapping(value = "/product/additional-photo-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAdditionalProductPhotos(@RequestParam("file") MultipartFile file,
                                                           @RequestParam("id") long id) throws IOException {
        productService.uploadAdditionalPhotos(id, file);
        return new ResponseEntity<>("Image uploaded successfully", HttpStatusCode.valueOf(200));
    }

//    @ExceptionHandler({IOException.class, FileSizeLimitExceededException.class})
//    public ResponseEntity<Integer> handleIOException() {
//        return new ResponseEntity<>(HttpStatusCode.valueOf(400));
//    }
//
//    @ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class})
//    public ResponseEntity<Integer> handleNoUserFound() {
//        return new ResponseEntity<>(HttpStatusCode.valueOf(404));
//    }
}
