package pl.edu.wat.crochetshopapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.crochetshopapi.model.Product;
import pl.edu.wat.crochetshopapi.service.ProductService;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
public class ProductController {
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

    @ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class})
    public ResponseEntity<Integer> handleNoUserFound() {
        return new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }
}
