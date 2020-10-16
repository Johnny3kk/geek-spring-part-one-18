package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;
import ru.geekbrains.persist.repo.ProductSpecification;

import java.math.BigDecimal;
import java.util.Optional;


@Controller
@RequestMapping("/product")
public class ProductController {

    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String allProducts(Model model, @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "minCost", required = false) BigDecimal minCost,
                              @RequestParam(value = "maxCost", required = false) BigDecimal maxCost,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size) {

        logger.info("Filtering by name: {} minCost: {} maxCost {}", name, minCost, maxCost);

        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(7),
                                                Sort.Direction.ASC, "title");

        Specification<Product> spec = ProductSpecification.trueLiteral();

        if (name != null && !name.isEmpty()) {
            spec = spec.and(ProductSpecification.titleLike(name));
        }

        if (minCost != null && maxCost == null) {
            spec = spec.and(ProductSpecification.smallerOrEqual(minCost));
        }

        if (maxCost != null && minCost == null) {
            spec = spec.and(ProductSpecification.greaterOrEqual(maxCost));
        }

        if (maxCost != null && minCost != null) {
            spec = spec.and(ProductSpecification.between(minCost, maxCost));
        }

        model.addAttribute("productsPage", productRepository.findAll(spec, pageRequest));
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
        productRepository.save(product);
        return "redirect:/product";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model) {
        Product product = productRepository.findById(id).get();
        model.addAttribute("product", product);
        return "product";
    }

}

