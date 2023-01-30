package pl.edu.wat.crochetshopapi.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.crochetshopapi.dto.PayuCheckoutResponse;
import pl.edu.wat.crochetshopapi.exception.EmptyCartException;
import pl.edu.wat.crochetshopapi.exception.OrderNotFoundException;
import pl.edu.wat.crochetshopapi.model.*;
import pl.edu.wat.crochetshopapi.repository.ClientRepository;
import pl.edu.wat.crochetshopapi.repository.OrderRepository;

import java.net.MalformedURLException;
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
    private ProductService productService;

    @Autowired
    private PayuService payuService;
    @Autowired
    private ContactService contactService;
    @Transactional
    public Order addOrder(long clientId) {
        Client client = clientService.get(clientId);

        if (client.getCart().getProducts().isEmpty())
            throw new EmptyCartException("Cart is empty. Cannot create order.");


        //Cart value
        List<Long> ids = new ArrayList<>();
        double value = 0;
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

        Order order = Order.builder()
                .status(OrderStatus.PENDING)
                .products(pr)
                .value(value)
                .client(client)
                .build();
        orderRepository.save(order);

        try {
            PayuCheckoutResponse payuResponse = payuService.checkout(order);

            order.setPayuOrderId(payuResponse.getOrderId());
            order.setRedirectUri(payuResponse.getRedirectUri());
            orderRepository.save(order);
        }catch(MalformedURLException ex) {
            System.out.println(ex);
        }
        if(client.getOrder().size() == 0) {
            client.setOrder(new ArrayList<>());
        }

        client.getOrder().add(order);
        clientRepository.save(client);

        StringBuilder str = new StringBuilder();

        order.getProducts()
                        .forEach(product -> {str.append(String.format(product.getName()+" koszt: "+product.getPrice()+" zł\n"));});
        contactService.submitContactRequest("Zakupy w sklepie CrochetShop. Zamówienie #"+order.getId(), "Dziękujemy za zakupy w sklepie internetowym CrochetShop i zapraszamy do zakupów :) \nLista zakupów:\n"+str.toString());
        return client.getOrder()
                .get(client.getOrder().size()-1);
    }

    public OrderStatus getStatus(long orderId) {
        return get(orderId).getStatus();
    }

    public void updateStatus(long orderId, boolean isCompleted){

        Order order = get(orderId);
        if(isCompleted)
            order.setStatus(OrderStatus.COMPLETED);
        else{
            order.getProducts().forEach(product -> {product.setReserved(false);});
            order.setStatus(OrderStatus.CANCELED);
        }
        orderRepository.save(order);
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

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }
}
