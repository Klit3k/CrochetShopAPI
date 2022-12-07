package pl.edu.wat.crochetshopapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.crochetshopapi.repository.ProductRepository;
import pl.edu.wat.crochetshopapi.service.ProductService;

@AllArgsConstructor
@RestController
public class ItemController {
    ProductService productService;
    private final ProductRepository productRepository;

    @ResponseBody @PostMapping(value = "/item", params = {"name", "description", "price"})
    public HttpStatus addNewItem(@RequestParam String name,
                    @RequestParam String description,
                    @RequestParam int price) {
        productService.add(name, description, price);
        return HttpStatus.OK;
    }
    @ResponseBody @DeleteMapping(value = "/item", params = "id")
    public HttpStatus delItem(@RequestParam long id){
        return productService.del(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }
}
