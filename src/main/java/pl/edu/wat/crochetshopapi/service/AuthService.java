package pl.edu.wat.crochetshopapi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.wat.crochetshopapi.Configuration;
import pl.edu.wat.crochetshopapi.model.AuthResponse;
import pl.edu.wat.crochetshopapi.model.Client;
import pl.edu.wat.crochetshopapi.repository.ClientRepository;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ClientRepository clientRepository;
    @NotNull
    public ResponseEntity<?> getJwtToken(String email, String password) {
        try {

            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            Client client = (Client) authenticate.getPrincipal();

            Algorithm algorithm = Algorithm.HMAC256(Configuration.SECRET_WORD);
            String token = JWT.create()
                    .withSubject(client.getUsername())
                    .withIssuer("Wojciech Klicki")
                    .withClaim("roles", client.getAuthorities()
                            .stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()))
                    .sign(algorithm);
            AuthResponse authResponse = new AuthResponse(client.getUsername(), token);
            return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
        } catch (UsernameNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    public boolean checkJwtUser(String accessToken) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(Configuration.SECRET_WORD);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Wojciech Klicki")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(accessToken.replace("Bearer ", ""));
            return clientRepository.existsByEmail(decodedJWT.getSubject());
        } catch(JWTVerificationException | JWTCreationException ex) {
            return false;
        }
    }
    public boolean checkJwtAdmin(String accessToken) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(Configuration.SECRET_WORD);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Wojciech Klicki")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(accessToken.replace("Bearer ", ""));
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
            if(Arrays.stream(roles).findFirst().orElseThrow().equals("ROLE_ADMIN"))
                return clientRepository.existsByEmail(decodedJWT.getSubject());
            else return false;
        } catch(JWTVerificationException | JWTCreationException | NoSuchElementException ex) {
            return false;
        }
    }
}
