package ru.geekbrains.persistance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepo {

    public static List<Product> list = new ArrayList<>();

    @Autowired
    public ProductRepo() {
        list.add(new Product(1, "non", 6 ));
        list.add(new Product(2, "oct", 8 ));
        list.add(new Product(3, "dec", 10 ));
    }

    public List<Product> getAllProducts() {
        return list;
    }

    public Product findById(Long id) {
        for (Product product : list) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
}
