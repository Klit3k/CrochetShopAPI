package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.crochetshopapi.dto.Mapper;
import pl.edu.wat.crochetshopapi.exception.ClientNotFoundException;
import pl.edu.wat.crochetshopapi.model.Address;
import pl.edu.wat.crochetshopapi.model.Client;
import pl.edu.wat.crochetshopapi.service.ClientService;

@RestController
public class ClientController {
    @Autowired
    ClientService clientService;
    @Autowired
    Mapper mapper;
    @GetMapping(value = "/client")
    public ResponseEntity<?> getClient(@RequestParam(name = "id") long id) {
        return new ResponseEntity<>(clientService.get(id), HttpStatusCode.valueOf(200));
    }
    @GetMapping(value = "/client-id")
    public ResponseEntity<?> getClientId(@RequestParam(name = "email") String email) {
        return new ResponseEntity<>(mapper.clientIdDTO(clientService.getByEmail(email).orElseThrow(() -> new ClientNotFoundException("Client with email" + email + "not found"))), HttpStatusCode.valueOf(200));
    }
    @GetMapping(value = "/client-by-email")
    public ResponseEntity<?> getClientByEmail(@RequestParam(name = "email") String email) {
        return new ResponseEntity<>(mapper.clientDTO(clientService.getByEmail(email).orElseThrow(() -> new ClientNotFoundException("Client with email" + email + "not found"))), HttpStatusCode.valueOf(200));
    }
    @GetMapping(value = "/clients")
    public ResponseEntity<?> getClients() {
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatusCode.valueOf(200));
    }

    @PostMapping(value = "/client/edit")
    public ResponseEntity<?> editClient(@RequestParam(name = "id") long id,
                                        @RequestParam(name = "name") String name,
                                        @RequestParam(name = "surname") String surname,
                                        @RequestParam(name = "phone") String phone){
        Client client = clientService.get(id);
        client.setName(name);
        client.setSurname(surname);
        client.setPhone(phone);

        clientService.update(id, client);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping(value = "/add-address")
    public ResponseEntity<?> addAddress(@RequestParam("clientId") long clientId,
                                        @RequestParam(name = "city") String city,
                                        @RequestParam(name = "street") String street,
                                        @RequestParam(name = "postalCode") String postalCode,
                                        @RequestParam(name = "houseNumber") String houseNumber) {

        return new ResponseEntity<>(clientService.addAdress(clientId, Address.builder()
                .city(city)
                .street(street)
                .postalCode(postalCode)
                .houseNumber(houseNumber)
                .build()), HttpStatusCode.valueOf(200));
    }
}
