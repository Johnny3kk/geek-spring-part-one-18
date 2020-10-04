package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RequestMapping("/api/v1/product")
@RestController
public class ProductResource {

    private final ProductRepository productRepository;

    @Autowired
    public ProductResource(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @GetMapping(path = "/{id}/id", produces = "application/json")
    public Product findById(@PathVariable("id") int id) {
        return productRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Product createUser(@RequestBody Product product) {
        if (product.getId() != null) {
            throw new IllegalArgumentException("Id found in the create request");
        }
        productRepository.save(product);
        return product;
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public Product updateUser(@RequestBody Product product) {
        productRepository.save(product);
        return product;
    }

    @DeleteMapping(path = "/{id}/id", produces = "application/json")
    public void deleteById(@PathVariable("id") int id) {
        productRepository.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> sqlIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException ex) {
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
