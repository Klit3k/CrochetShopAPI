package pl.edu.wat.crochetshopapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.crochetshopapi.exception.ClientAlreadyExistsException;
import pl.edu.wat.crochetshopapi.exception.ClientNotFoundException;
import pl.edu.wat.crochetshopapi.model.Address;
import pl.edu.wat.crochetshopapi.model.Cart;
import pl.edu.wat.crochetshopapi.model.Client;
import pl.edu.wat.crochetshopapi.repository.AddressRepository;
import pl.edu.wat.crochetshopapi.repository.CartRepository;
import pl.edu.wat.crochetshopapi.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CartRepository cartRepository;
    public Client add(String name, String surname, String email) {
        if (getByEmail(email).isPresent())
            throw new ClientAlreadyExistsException("Cannot add new client because client with the same email already exists.");
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

        return client;
    }
    public void del(Long id) {
        clientRepository.delete(get(id));
    }
    public Client update(Long id, Client editedClient) {
        editedClient.setId(get(id).getId());
        clientRepository.save(editedClient);
        return editedClient;
    }
    public Client get(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found."));
    }
    public Optional<Client> getByEmail(String email) {
        return clientRepository.findByEmail(email);
                //.orElseThrow(() -> new ClientNotFoundException("Client not found."));
    }
    public List<Client> getAllClients() {
        List<Client> clientList = new ArrayList<>();
        clientRepository.findAll().forEach(clientList::add);
        return clientList;
    }
}
