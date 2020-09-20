package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepo;

import java.math.BigDecimal;
import java.util.List;


@Controller
@RequestMapping("/product")
public class ProductController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ProductRepo productRepo;

    @GetMapping
    public String allProducts(Model model, @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "minCost", required = false) BigDecimal minCost,
                              @RequestParam(value = "maxCost", required = false) BigDecimal maxCost) {

        logger.info("Filtering by name: {} minCost: {} maxCost {}", name, minCost, maxCost);

        List<Product> allProducts;
       if((name == null || name.isEmpty()) && (minCost == null) && (maxCost == null)) {
           allProducts = productRepo.findAll();
       } else if ((minCost == null) && (maxCost == null)) {
           allProducts = productRepo.findByTitleLike("%" + name + "%");
       } else if ((name == null || name.isEmpty()) && (maxCost == null)) {
           allProducts = productRepo.queryBySmaller(minCost);
       } else if ((name == null || name.isEmpty()) && (minCost == null)) {
           allProducts = productRepo.queryByBigger(maxCost);
       } else if (name == null || name.isEmpty()) {
           allProducts = productRepo.queryByScope(minCost, maxCost);
       } else if (maxCost == null) {
           allProducts = productRepo.queryByTitleAndMin("%" + name + "%", minCost);
       } else if (minCost == null) {
           allProducts = productRepo.queryByTitleAndMax("%" + name + "%", maxCost);
       } else {
           allProducts = productRepo.queryByTitleAndScope("%" + name + "%", minCost, maxCost);
       }

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
    public String insertProduct(Model model, Product product) {
        productRepo.save(product);
        return "redirect:/product";
    }
}
