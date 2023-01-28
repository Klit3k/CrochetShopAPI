package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.crochetshopapi.service.CartService;

@RestController
public class CartController {
    @Autowired
    CartService cartService;

    @PostMapping(value = "/cart")
    public ResponseEntity<?> addProductToCart(@RequestParam("clientId") long clientId,
                                              @RequestParam("productId") long productId) {
        return new ResponseEntity<>(cartService.addProductToCart(clientId, productId), HttpStatusCode.valueOf(200));
    }

    @DeleteMapping(value = "/cart")
    public ResponseEntity<?> delProductFromCart(@RequestParam("clientId") long clientId,
                                                @RequestParam("productId") long productId) {
        if(!cartService.isProductInCart(clientId, productId))
            return new ResponseEntity<>("Product not found in client's cart.", HttpStatusCode.valueOf(200));
        cartService.delProductFromCart(clientId, productId);
        return new ResponseEntity<>("Product " + productId + " has been removed from cart",
                HttpStatusCode.valueOf(200));
    }

    @GetMapping(value = "/cart")
    public ResponseEntity<?> getProductsFromCart(@RequestParam("clientId") long clientId) {
        return new ResponseEntity<>(cartService.getProductsFromCart(clientId), HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @DeleteMapping(value = "/empty-cart", params = "clientId")
    public ResponseEntity<?> emptyClientCart(@RequestParam long clientId) {
        if (cartService.isCartEmpty(clientId))
            return new ResponseEntity<>("Client cart is empty", HttpStatusCode.valueOf(200));
        cartService.emptyCart(clientId);
        return new ResponseEntity<>("Client id: " + clientId + " cart has been emptied.", HttpStatusCode.valueOf(200));
    }

}
