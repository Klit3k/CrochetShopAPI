package pl.edu.wat.crochetshopapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.crochetshopapi.model.Client;
import pl.edu.wat.crochetshopapi.repository.CartRepository;
import pl.edu.wat.crochetshopapi.repository.ClientRepository;
import pl.edu.wat.crochetshopapi.repository.ProductRepository;

@Service
public class CartService {
    @Autowired
    ClientService clientService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ClientRepository clientRepository;

    public Client addProductToCart(long clientId, long productId) {
        Client client = clientService.getClientById(clientId);
        client.getCart()
                .getProducts()
                .add(productRepository.findById(productId).orElseThrow());
        return clientRepository.save(client);
    }
    public void delProductFromCart(long clientId, long productId) {
        Client client = clientService.getClientById(clientId);
        client.getCart()
                .getProducts()
                .add(productRepository.findById(productId).orElseThrow());
    }

}
