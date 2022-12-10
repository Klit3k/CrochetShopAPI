package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.crochetshopapi.service.ClientService;

@RestController
public class ClientController {
    @Autowired
    ClientService clientService;

    @PostMapping(value = "/client")
    public ResponseEntity<?> addClient(@RequestParam(name = "name") String name,
                                       @RequestParam(name = "surname") String surname,
                                       @RequestParam(name = "email") String email) {
        return new ResponseEntity<>(clientService.add(name, surname, email), HttpStatusCode.valueOf(200));
    }

    @GetMapping(value = "/client")
    public ResponseEntity<?> getClient(@RequestParam(name = "id") long id) {
        return new ResponseEntity<>(clientService.get(id), HttpStatusCode.valueOf(200));
    }
}
