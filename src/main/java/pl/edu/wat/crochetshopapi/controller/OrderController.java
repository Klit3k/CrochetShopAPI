package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.crochetshopapi.service.OrderService;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/order")
    public ResponseEntity<?> addOrder(@RequestParam("clientId") long clientId) {
        return new ResponseEntity<>( orderService.addOrder(clientId), HttpStatusCode.valueOf(200));
    }
    @GetMapping(value = "/orders-client")
    public ResponseEntity<?> getAll(@RequestParam("clientId") long clientId) {
        return new ResponseEntity<>(orderService.getAllClientOrders(clientId), HttpStatusCode.valueOf(200));
    }

    @GetMapping(value = "/orders")
    public ResponseEntity<?> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatusCode.valueOf(200));
    }

    @GetMapping(value = "/order-status")
    public ResponseEntity<?> getOrderStatus(@RequestParam("orderId") long orderId) {
        return new ResponseEntity<>(orderService.getStatus(orderId), HttpStatusCode.valueOf(200));
    }
}
