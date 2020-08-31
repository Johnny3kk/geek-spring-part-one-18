package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.persistance.Product;
import ru.geekbrains.persistance.ProductRepo;


@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepo repo;

    @GetMapping
    public String allProducts(Model model) {
        model.addAttribute("products", repo.getAllProducts());
        return "products";
    }

    @PostMapping("/add")
    public String addProduct(Product prod) {
        repo.list.add(prod);
        return "redirect:product/";
    }
}
