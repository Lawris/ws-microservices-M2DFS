package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitGratuitException;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;



public class ProductController {

    @Autowired
    private ProductDao productDao;


    //Récupérer la liste des produits
    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    public MappingJacksonValue listeProduits() {
        Iterable<Product> produits = productDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDeNosFiltres);
        return produitsFiltres;
    }


    //Récupérer un produit par son Id
    @ApiOperation(value = "Renvoie un produit sélectionné par son ID")
    @GetMapping("getProduitId/{id}")
    public Product afficherUnProduit(@PathVariable(value = "id") int id) {
        return productDao.findById(id);
    }



    //ajouter un produit
    @ApiOperation(value = "Ajoute un produit en base décrit par les params POST")
    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {
        Product productAdded =  productDao.save(product);

        if (productAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    // supprimer un produit
    @ApiOperation(value = "Supprime un produit dans la base correspondant à l'id fournit")
    @RequestMapping(value = "/suprimerProduit/{id}", method = RequestMethod.GET)
    public void supprimerProduit(@PathVariable(value = "id") int id) {
        productDao.delete(id);
    }


    // Mettre à jour un produit
    @ApiOperation(value = "Update d'un produit de la base")
    @RequestMapping(value = "/updateProduit", method = RequestMethod.PUT)
    public void updateProduit(@RequestBody Product product) {
        if (product.getPrix() == 0) {
            throw new ProduitGratuitException("Le nouveau prix du produit est gratuit");
        } else {
            productDao.save(product);
        }
    }

    @ApiOperation(value = "Retourne tous les produits de la base avec la marge")
    @GetMapping("/AdminProduits")
    public String showAdminProduits() {
        Iterable<Product> produits = productDao.findAll();
        StringBuilder res = new StringBuilder("} <br>");
        for (Product produit: produits) {
            res.append(produit.toString()).append(" : ").append(produit.calculerMargeProduit()).append(", <br>");
        }
        res.append("}");
        return res.toString();
    }

    @ApiOperation(value = "Retourne tous les produits triés par nom croissant")
    @GetMapping("/ProduitsTri")
    public List<Product> trierProduitsParOrdreAlphabetique() {
        return productDao.findAllByOrderByNomAsc();
    }


    //Pour les tests
    @GetMapping(value = "test/produits/{prix}")
    public List<Product>  testeDeRequetes(@PathVariable int prix) {
        return productDao.chercherUnProduitCher(400);
    }



}
