package pl.edu.wat.crochetshopapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.crochetshopapi.model.Client;
import pl.edu.wat.crochetshopapi.model.Product;
import pl.edu.wat.crochetshopapi.repository.CartRepository;
import pl.edu.wat.crochetshopapi.repository.ClientRepository;

import java.util.List;

@Service
public class CartService {
    @Autowired
    ClientService clientService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProductService productService;

    public Client addProductToCart(long clientId, long productId) {
        Client client = clientService.get(clientId);
        client.getCart().getProducts().add(productService.get(productId));
        return clientRepository.save(client);
    }

    public void delProductFromCart(long clientId, long productId) {
        Client client = clientService.get(clientId);
        client.getCart().getProducts().remove(productService.get(productId));
        cartRepository.save(client.getCart());
    }

    public boolean isProductInCart(long clientId, long productId) {
        Client client = clientService.get(clientId);
        return client.getCart().getProducts().contains(productService.get(productId));
    }

    public boolean isCartEmpty(long clientId) {
        Client client = clientService.get(clientId);
        return client.getCart().getProducts().isEmpty();
    }

    public List<Product> getProductsFromCart(long clientId) {
        Client client = clientService.get(clientId);
        return client.getCart().getProducts();
    }

    public void emptyCart(long clientId) {
        Client client = clientService.get(clientId);
        client.getCart().getProducts().clear();
        clientRepository.save(client);
    }
}
