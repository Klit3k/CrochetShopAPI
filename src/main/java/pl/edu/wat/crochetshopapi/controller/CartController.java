package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.crochetshopapi.service.CartService;

@RestController
public class CartController {
    @Autowired
    CartService cartService;

    @PostMapping(value = "/cart")
    public ResponseEntity<?> addProductToCart(@RequestParam("clientId") long clientId,
                                              @RequestParam("productId") long productId) {
        return new ResponseEntity<>(cartService.addProduct(clientId, productId), HttpStatusCode.valueOf(200));
    }
}
