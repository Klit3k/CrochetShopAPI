package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.crochetshopapi.dto.Mapper;
import pl.edu.wat.crochetshopapi.model.AuthRequest;
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

    //TODO: Need to change on @RequestParam
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        return authService.getJwtToken(authRequest.getEmail(), authRequest.getPassword());
    }

    @PostMapping("/auth/check")
    public ResponseEntity<?> checkJwt(@RequestHeader String Authorization ){
        return new ResponseEntity<>(authService.checkJwtToken(Authorization), HttpStatus.OK);
    }

    @PostMapping(value = "/auth/register")
    public ResponseEntity<?> register(@RequestParam(name = "name") String name,
                                      @RequestParam(name = "surname") String surname,
                                      @RequestParam(name = "email") String email,
                                      @RequestParam(name = "password") String password,
                                      @RequestParam(name = "phone") String phone) {
        return new ResponseEntity<>(mapper.clientDTO(clientService.add(name, surname, phone, email, password)), HttpStatus.OK);
    }
}
