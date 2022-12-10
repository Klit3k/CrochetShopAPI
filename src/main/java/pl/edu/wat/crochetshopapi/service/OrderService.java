package pl.edu.wat.crochetshopapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.crochetshopapi.exception.EmptyCartException;
import pl.edu.wat.crochetshopapi.exception.OrderNotFoundException;
import pl.edu.wat.crochetshopapi.model.*;
import pl.edu.wat.crochetshopapi.repository.CartRepository;
import pl.edu.wat.crochetshopapi.repository.ClientRepository;
import pl.edu.wat.crochetshopapi.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductService productService;
    public Order addOrder(long clientId) {
        Client client = clientService.get(clientId);

        if (client.getCart().getProducts().isEmpty())
            throw new EmptyCartException("Cart is empty. Cannot create order.");


        //Cart value
        List<Long> ids = new ArrayList<>();
        long value = 0;
        for (Product product : client.getCart().getProducts()) {
            value = value + product.getPrice();
            ids.add(product.getId());
        }

        Cart cart = client.getCart();

        //Emptying cart
        cartService.emptyCart(clientId);

        clientRepository.save(client);

        List<Product> pr = new ArrayList<>();

        for (long el:
             ids) {
            pr.add(productService.get(el));
        }
        Order order = orderRepository.save(
                Order.builder()
                        .status(OrderStatus.ORDER_TO_BE_PROCESED)
                        .products(pr)
                        .value(value)
                        .build());

        if(client.getOrder().size() == 0) {
            client.setOrder(new ArrayList<>());
        }

        client.getOrder().add(order);
        clientRepository.save(client);
        return client.getOrder()
                .get(client.getOrder().size()-1);
    }

    public OrderStatus getStatus(long orderId) {
        return get(orderId).getStatus();
    }

    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        orderRepository.findAll().forEach(orderList::add);
        return orderList;
    }

    public List<Order> getAllClientOrders(long clientId) {
        return clientService.get(clientId).getOrder();
    }

    public Order get(long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found."));
    }
}
