package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepo;

import java.sql.SQLException;
import java.util.List;


@Controller
@RequestMapping("/product")
public class ProductController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ProductRepo productRepo;

    @GetMapping
    public String allProducts(Model model) throws SQLException {
        List<Product> allProducts = productRepo.findAll();
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
        productRepo.save(product);
        return "redirect:/product";
    }
}
