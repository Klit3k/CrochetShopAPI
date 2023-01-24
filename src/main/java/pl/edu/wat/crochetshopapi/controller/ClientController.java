package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.crochetshopapi.model.Address;
import pl.edu.wat.crochetshopapi.service.ClientService;

@RestController
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping(value = "/client")
    public ResponseEntity<?> getClient(@RequestParam(name = "id") long id) {
        return new ResponseEntity<>(clientService.get(id), HttpStatusCode.valueOf(200));
    }

    @GetMapping(value = "/clients")
    public ResponseEntity<?> getClients() {
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatusCode.valueOf(200));
    }

    @GetMapping(value = "/add-address")
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
