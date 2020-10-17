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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;
import ru.geekbrains.persist.repo.ProductSpecification;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/my_products")
public class UserProductController {

        private final static Logger logger = LoggerFactory.getLogger(UserProductController.class);

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
            return "products4users";

        }
}
