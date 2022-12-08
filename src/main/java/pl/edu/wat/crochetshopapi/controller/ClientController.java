package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.crochetshopapi.model.Client;
import pl.edu.wat.crochetshopapi.service.ClientService;

import java.util.NoSuchElementException;

@RestController
public class ClientController {
    @Autowired
    ClientService clientService;

    @PostMapping(value = "/client")
    public ResponseEntity<?> addClient(@RequestParam(name = "name") String name,
                                    @RequestParam(name = "surname") String surname,
                                    @RequestParam(name = "email") String email) {
        return clientService.add(name, surname, email);
    }
    @GetMapping(value = "/client")
    public ResponseEntity<?> getClient(@RequestParam(name = "id") long id){
        return new ResponseEntity<>(clientService.getClientById(id), HttpStatusCode.valueOf(200));
    }
}
