package pl.edu.wat.crochetshopapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.edu.wat.crochetshopapi.model.Address;
import pl.edu.wat.crochetshopapi.model.Cart;
import pl.edu.wat.crochetshopapi.model.Client;
import pl.edu.wat.crochetshopapi.repository.AddressRepository;
import pl.edu.wat.crochetshopapi.repository.CartRepository;
import pl.edu.wat.crochetshopapi.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CartRepository cartRepository;

    public ResponseEntity<?> add(String name, String surname, String email) {
        if (clientRepository.findByEmail(email).isPresent()) return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        Client client = clientRepository.save(Client.builder()
                .name(name)
                .surname(surname)
                .email(email)
                .build());

        Address address = new Address();
        address.setClient(client);
        client.setAddress(addressRepository.save(address));

        Cart cart = new Cart();
        cart.setClient(client);
        client.setCart(cart);
        cartRepository.save(cart);

        return new ResponseEntity<>(
                client,
                HttpStatusCode.valueOf(200)
        );
    }

    public ResponseEntity<?> del(Long id) {
        clientRepository.delete(clientRepository.findById(id).orElseThrow());
        return new ResponseEntity<>("Client with id " + id + " has been deleted successfully.", HttpStatusCode.valueOf(200));
    }

    public ResponseEntity<?> update(Long id, Client editedClient) {
        editedClient.setId(clientRepository.findById(id).orElseThrow().getId());
        clientRepository.save(editedClient);
        return new ResponseEntity<>("Client with id " + id + " has been updated successfully.", HttpStatusCode.valueOf(200));
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow();
    }

    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email).orElseThrow();
    }

    public List<Client> getAllClients() {
        List<Client> clientList = new ArrayList<>();
        clientRepository.findAll().forEach(clientList::add);
        return clientList;
    }
}
