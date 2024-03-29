package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import pl.edu.wat.crochetshopapi.dto.Mapper;
import pl.edu.wat.crochetshopapi.service.OrderService;
import pl.edu.wat.crochetshopapi.service.PayuService;

@RestController
public class OrderController {
    @Autowired
    private  OrderService orderService;
    @Autowired
    private Mapper mapper;
    @Autowired
    private PayuService payuService;
    @PostMapping(value = "/order")
    public ResponseEntity<?> addOrder(@RequestParam("clientId") long clientId) {
        return new ResponseEntity<>( mapper.orderDTO(orderService.addOrder(clientId)), HttpStatusCode.valueOf(200));
    }
    @GetMapping(value = "/orders-client")
    public ResponseEntity<?> getAll(@RequestParam("clientId") long clientId) {
        return new ResponseEntity<>(mapper.ordersListDTO(orderService.getAllClientOrders(clientId)), HttpStatusCode.valueOf(200));
    }

    @GetMapping(value = "/orders")
    public ResponseEntity<?> getAllOrders() {
        return new ResponseEntity<>(mapper.ordersListDTO(orderService.getAllOrders()), HttpStatusCode.valueOf(200));
    }

    @GetMapping(value = "/order-status")
    public ResponseEntity<?> getOrderStatus(@RequestParam("orderId") long orderId) {
        return new ResponseEntity<>(orderService.getStatus(orderId), HttpStatusCode.valueOf(200));
    }

    @GetMapping(value = "/order/update")
    public RedirectView updateStatus(@RequestParam("orderId") long orderId ,
                                     @RequestParam(value = "error", required = false, defaultValue = "") String error) {
        if(error.isBlank())
            orderService.updateStatus(orderId, true);
        else
            orderService.updateStatus(orderId, false);

        return new RedirectView("http://localhost:3000/home/thank-you");
    }
}
