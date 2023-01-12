package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.crochetshopapi.dto.Mapper;
import pl.edu.wat.crochetshopapi.service.AuthService;
import pl.edu.wat.crochetshopapi.service.ClientService;

@RestController
public class AuthController {
    @Autowired
    ClientService clientService;
    @Autowired
    AuthService authService;

    @Autowired
    Mapper mapper;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestParam(name = "email") String email,
                                   @RequestParam(name = "password") String password) {
        return authService.getJwtToken(email, password);
    }


    @PostMapping(value = "/auth/register")
    public ResponseEntity<?> register(@RequestParam(name = "name") String name,
                                      @RequestParam(name = "surname") String surname,
                                      @RequestParam(name = "email") String email,
                                      @RequestParam(name = "password") String password) {

        return new ResponseEntity<>(mapper.clientDTO(clientService.add(name, surname, email, password)), HttpStatus.OK);
    }
}
