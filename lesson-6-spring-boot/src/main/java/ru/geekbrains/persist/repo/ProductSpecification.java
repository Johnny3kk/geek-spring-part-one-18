package ru.geekbrains.persist.repo;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.entity.Product;

import java.math.BigDecimal;

public final class ProductSpecification {

    public static Specification<Product> trueLiteral() {
        return (root, query, builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<Product> titleLike(String title) {
        return (root, query, builder) -> builder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Product> smallerOrEqual(BigDecimal minCost) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("cost"), minCost);
    }

    public static Specification<Product> greaterOrEqual(BigDecimal maxCost) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("cost"), maxCost);
    }

    public static Specification<Product> between(BigDecimal minCost, BigDecimal maxCost) {
        return (root, query, builder) -> builder.between(root.get("cost"), minCost, maxCost);
    }


}
