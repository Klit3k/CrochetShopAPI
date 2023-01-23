package pl.edu.wat.crochetshopapi.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.edu.wat.crochetshopapi.model.PayuAuthorizeResponse;

import java.util.Objects;

@Service
public class PayuService {
    public String getToken() {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl("https://secure.snd.payu.com/pl/standard/user/oauth/authorize")
                .queryParam("grant_type", "client_credentials")
                .queryParam("client_id", "460266")
                .queryParam("client_secret", "2b81eb5752403809aeb994e2bae81610")
                .encode()
                .toUriString();

        try {
            ResponseEntity<PayuAuthorizeResponse> exchange = new RestTemplate().exchange(
                    urlTemplate,
                    HttpMethod.POST,
                    HttpEntity.EMPTY,
                    PayuAuthorizeResponse.class);
            return Objects.requireNonNull(exchange.getBody()).getAccess_token();
        } catch (RestClientException ex) {
            System.out.println("Server response error.");
        }
        return "";
    }
}
