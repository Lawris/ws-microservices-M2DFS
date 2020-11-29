package com.hystrix.circuitbreaker.delegate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ProductServiceDelegate {

    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @HystrixCommand(fallbackMethod = "callProductServiceAndGetAll_Fallback")
    public MappingJacksonValue callProductServiceAndGetAll() {
        ResponseEntity<MappingJacksonValue> produits = restTemplate.exchange("http://localhost:9090/Produits", HttpMethod.GET, null, MappingJacksonValue.class);
        return produits.getBody();
    }

    @HystrixCommand(fallbackMethod = "callProductServiceAndGetAll_Fallback")
    public MappingJacksonValue callProductServiceAndGetTri() {
        ResponseEntity<MappingJacksonValue> produits = restTemplate.exchange("http://localhost:9090/ProduitsTri", HttpMethod.GET, null, MappingJacksonValue.class);
        return produits.getBody();
    }

    @HystrixCommand(fallbackMethod = "callProductServiceAndGetAll_Fallback")
    public MappingJacksonValue callProductServiceAndUpdate() {
        ResponseEntity<MappingJacksonValue> produits = restTemplate.exchange("http://localhost:9090/updateProduit", HttpMethod.PUT, null, MappingJacksonValue.class);
        return produits.getBody();
    }

    @HystrixCommand(fallbackMethod = "callProductServiceAndGetAll_Fallback")
    public MappingJacksonValue callProductServiceAndSupprime() {
        ResponseEntity<MappingJacksonValue> produits = restTemplate.exchange("http://localhost:9090/suprimerProduit/{id}", HttpMethod.GET, null, MappingJacksonValue.class);
        return produits.getBody();
    }

    @HystrixCommand(fallbackMethod = "callProductServiceAndGetAll_Fallback")
    public String callProductServiceAndGetAllAdmin() {
        ResponseEntity<String> produits = restTemplate.exchange("http://localhost:9090/AdminProduits", HttpMethod.GET, null, String.class);
        return  produits.getBody();
    }

    public String callProductServiceAndGetAllAdmin_Fallback() {
        return "Le micro service Produit n'est pas accessible pour le moment";
    }


}
