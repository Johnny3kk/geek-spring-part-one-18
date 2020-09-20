package ru.geekbrains.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geekbrains.persist.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    List<Product> findByTitle(String title);

    List<Product> findByTitleLike(String titlePattern);

    @Query("from Product p where p.cost = :minCost or p.cost < :minCost")
    List<Product> queryBySmaller(@Param("minCost") BigDecimal minCost);

    @Query("from Product p where p.cost = :maxCost or p.cost > :maxCost")
    List<Product> queryByBigger(@Param("maxCost") BigDecimal maxCost);

    @Query("from Product p where p.cost > :minCost and p.cost < :maxCost")
    List<Product> queryByScope(@Param("minCost") BigDecimal minCost, @Param("maxCost") BigDecimal maxCost);

    @Query("from Product p where p.title like :title and (p.cost = :minCost or p.cost < :minCost)")
    List<Product> queryByTitleAndMin(@Param("title") String title, @Param("minCost") BigDecimal minCost);

    @Query("from Product p where p.title like :title and (p.cost = :maxCost or p.cost > :maxCost)")
    List<Product> queryByTitleAndMax(@Param("title") String title, @Param("maxCost") BigDecimal maxCost);

    @Query("from Product p where p.title like :title and p.cost > :minCost and p.cost < :maxCost")
    List<Product> queryByTitleAndScope(@Param("title") String title, @Param("minCost") BigDecimal minCost, @Param("maxCost") BigDecimal maxCost);

}
