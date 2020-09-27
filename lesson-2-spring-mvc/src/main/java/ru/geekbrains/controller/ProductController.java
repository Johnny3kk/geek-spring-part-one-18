package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepo;
import ru.geekbrains.persist.repo.ProductSpecification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/product")
public class ProductController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ProductRepo productRepo;

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

        model.addAttribute("productsPage", productRepo.findAll(spec, pageRequest));



        return "productIndex";

      /*  List<Product> allProducts;
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
       return "productIndex";*/
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
