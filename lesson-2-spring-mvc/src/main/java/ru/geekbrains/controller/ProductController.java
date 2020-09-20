package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.ProductRepo;

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

    @GetMapping("/add")
    public String addProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("/insert")
    public String insertProduct(Model model, Product product) throws SQLException {
        repo.insert(product);
        return "redirect:/product";
    }
}
