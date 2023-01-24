package pl.edu.wat.crochetshopapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.edu.wat.crochetshopapi.model.Client;
import pl.edu.wat.crochetshopapi.repository.ClientRepository;
import pl.edu.wat.crochetshopapi.security.filter.JwtTokenFilter;
import pl.edu.wat.crochetshopapi.security.filter.SimpleCORSFilter;

@Configuration
public class SecurityConfiguration {
    @Autowired
    private ClientRepository clientRepository;
    private final JwtTokenFilter jwtTokenFilter;
    private final SimpleCORSFilter simpleCORSFilter;

    public SecurityConfiguration(ClientRepository clientRepository, JwtTokenFilter jwtTokenFilter, SimpleCORSFilter simpleCORSFilter) {
        this.clientRepository = clientRepository;
        this.jwtTokenFilter = jwtTokenFilter;
        this.simpleCORSFilter = simpleCORSFilter;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void saveUser() {
        Client client;
        if(clientRepository.findByEmail(pl.edu.wat.crochetshopapi.Configuration.ADMIN_LOGIN).isPresent()) {
            client = clientRepository.findByEmail(pl.edu.wat.crochetshopapi.Configuration.ADMIN_LOGIN).get();
        } else {
            client = Client.builder()
                    .email(pl.edu.wat.crochetshopapi.Configuration.ADMIN_LOGIN)
                    .role("ROLE_ADMIN")
                    .build();
        }
        client.setPassword(getBcryptPasswordEncoder().encode(pl.edu.wat.crochetshopapi.Configuration.ADMIN_PASSWORD));
        clientRepository.save(client);
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> clientRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Client with email not found: " + username));
    }

    @Bean
    public PasswordEncoder getBcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authorizationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //Without caching data
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests()

                //*** ADMIN ***
                .requestMatchers(HttpMethod.GET,"/product").hasAuthority("ROLE_ADMIN")
//                .requestMatchers(HttpMethod.GET,"/client").hasAuthority("ROLE_ADMIN")


                //*** USER ***

                .requestMatchers(HttpMethod.POST,"/cart").permitAll()//.hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.DELETE,"/cart").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.GET,"/cart").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.DELETE,"/empty-cart").hasAuthority("ROLE_USER")

                .requestMatchers(HttpMethod.POST,"/comment").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.DELETE,"/comment").hasAuthority("ROLE_USER")

                .requestMatchers(HttpMethod.GET,"/image").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.GET,"/images").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.POST,"/image").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.DELETE,"/image").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.PUT,"/empty-cart").hasAuthority("ROLE_USER")

                //ordercontroller...

                //*** ALL ***
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/check").permitAll()
                .requestMatchers(HttpMethod.GET,"/product").permitAll()
                .requestMatchers(HttpMethod.GET,"/client").permitAll()
                .requestMatchers(HttpMethod.GET,"/checkout").permitAll()

                .anyRequest().permitAll();
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(simpleCORSFilter, jwtTokenFilter.getClass());

        return http.build();
    }
}