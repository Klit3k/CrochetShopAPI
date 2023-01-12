package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.crochetshopapi.service.ClientService;

@RestController
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping(value = "/client")
    public ResponseEntity<?> getClient(@RequestParam(name = "id") long id) {
        return new ResponseEntity<>(clientService.get(id), HttpStatusCode.valueOf(200));
    }
}
