package pl.edu.wat.crochetshopapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.edu.wat.crochetshopapi.dto.*;
import pl.edu.wat.crochetshopapi.model.Order;

import java.util.List;
import java.util.Objects;


@Service
public class PayuService {
    @Value("${payu.url}")
    String PAYU_URL;
    @Value("${payu.merchantId}")
    String PAYU_MERCHANT_ID;
    @Autowired
    private Mapper mapper;



    public String getToken() {
        String urlTemplate = UriComponentsBuilder.newInstance()
                .queryParam("grant_type", "client_credentials")
                .queryParam("client_id", "460266")
                .queryParam("client_secret", "2b81eb5752403809aeb994e2bae81610")
                .scheme("https")
                .host(PAYU_URL)
                .path("/pl/standard/user/oauth/authorize")
                .encode()
                .build()
                .toUriString();

        try {
            ResponseEntity<PayuAuthorizeResponse> exchange = new RestTemplate().exchange(
                    urlTemplate,
                    HttpMethod.POST,
                    HttpEntity.EMPTY,
                    PayuAuthorizeResponse.class,
                    PAYU_URL
            );
            return Objects.requireNonNull(exchange.getBody()).getAccess_token();
        } catch (RestClientException ex) {
            System.out.println("Server response error " + ex);
        } catch (IllegalArgumentException ex) {
            System.out.println("Cannot connect with entered URL " + ex);
        }
        return "";
    }

    public PayuCheckoutResponse checkout(Order order) {


        List<PayuProduct> products = order
                .getProducts()
                .stream()
                .map(e -> mapper.productToPayu(e))
                .toList();

        PayuCheckoutRequest req = PayuCheckoutRequest.builder()
                .currencyCode("PLN")
                .customerIp("127.0.0.1")
                .description("CrochetShop !")
                .totalAmount(String.valueOf(products.stream().mapToLong(PayuProduct::getUnitPrice).sum()))
                .merchantPosId(PAYU_MERCHANT_ID)
                .products(products)
                .buyer(mapper.clientToPayu(order.getClient()))
                .build();

        String urlTemplate = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(PAYU_URL)
                .path("/api/v2_1/orders")
                .encode()
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("bearer %s", getToken()));
        headers.add("Content-Type", "application/json");

        try {
            HttpEntity<String> httpEntity = new HttpEntity<>(new ObjectMapper().writeValueAsString(req), headers);
            ResponseEntity<PayuCheckoutResponse> exchange = new RestTemplate().exchange(
                    urlTemplate,
                    HttpMethod.POST,
                    httpEntity,
                    PayuCheckoutResponse.class,
                    PAYU_URL
            );

            order.setPayuOrderId(Objects.requireNonNull(exchange.getBody()).getOrderId());

            return Objects.requireNonNull(exchange.getBody());
        } catch (RestClientException ex) {
            System.out.println("Server response error " + ex);
        } catch (IllegalArgumentException ex) {
            System.out.println("Cannot connect with entered URL " + ex);
        } catch (JsonProcessingException ex) {
            System.out.println("Json processing problem" + ex);
        }
        return null;
    }


}
