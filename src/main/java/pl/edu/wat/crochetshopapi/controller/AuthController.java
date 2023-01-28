package pl.edu.wat.crochetshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.crochetshopapi.dto.Mapper;
import pl.edu.wat.crochetshopapi.model.AuthRequest;
import pl.edu.wat.crochetshopapi.model.AuthResponse;
import pl.edu.wat.crochetshopapi.service.AuthService;
import pl.edu.wat.crochetshopapi.service.ClientService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        Map<String, Boolean> result = new HashMap<>();
        result.put("isTokenVerified", authService.checkJwtUser(Authorization));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/auth/check-admin")
    public ResponseEntity<?> checkJwtAdmin(@RequestHeader String Authorization ){
        Map<String, Boolean> result = new HashMap<>();
        result.put("isTokenVerified", authService.checkJwtAdmin(Authorization));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/auth/register")
    public ResponseEntity<?> register(@RequestParam(name = "name") String name,
                                      @RequestParam(name = "surname") String surname,
                                      @RequestParam(name = "email") String email,
                                      @RequestParam(name = "password") String password,
                                      @RequestParam(name = "phone") String phone) {
        clientService.add(name, surname, phone, email, password);
        AuthResponse authResponse = (AuthResponse) Objects.requireNonNull(authService.getJwtToken(email, password).getBody());
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
