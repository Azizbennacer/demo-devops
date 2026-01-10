package com.example.demo.Controller;

import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/produits")
public class ProduitController {
    @GetMapping
    public List<Produit> getProduits() { ... }
}
