package com.hystrix.circuitbreaker.controller;

import com.hystrix.circuitbreaker.delegate.ProductServiceDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductServiceController {

    @Autowired
    ProductServiceDelegate productServiceDelegate;

    @GetMapping("/Produits")
    public MappingJacksonValue getAllProducts() {
        System.out.println("ici1");
        return productServiceDelegate.callProductServiceAndGetAll();
    }

    @GetMapping("/AdminProduits")
    public String getAllProductsAdmin() {
        return productServiceDelegate.callProductServiceAndGetAllAdmin();
    }

    @GetMapping("/ProduitsTri")
    public MappingJacksonValue getAllProductsSorted() {
        return productServiceDelegate.callProductServiceAndGetTri();
    }

    @GetMapping("/updateProduit")
    public MappingJacksonValue updateProduct() {
        return productServiceDelegate.callProductServiceAndUpdate();
    }

    @GetMapping("/suprimerProduit/{id}")
    public MappingJacksonValue deleteProductId() {
        return productServiceDelegate.callProductServiceAndSupprime();
    }
}
