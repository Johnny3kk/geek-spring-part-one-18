package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.persistance.Product;
import ru.geekbrains.persistance.ProductRepo;
import ru.geekbrains.persistance.User;

import java.sql.SQLException;
import java.util.List;


@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepo repo;

    @GetMapping
    public String allProducts(Model model) throws SQLException {
        List<Product> allProducts = repo.getAllProducts();
        model.addAttribute("productIndex", allProducts);
        return "productIndex";
    }

    @PostMapping("/add")
    public String addProduct(Product prod) {

//        return "redirect:product/";
        return "product";
    }
}