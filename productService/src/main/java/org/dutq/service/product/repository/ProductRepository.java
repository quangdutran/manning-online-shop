package org.dutq.service.product.repository;

import com.dutq.core.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("FROM Product p INNER JOIN FETCH p.productDescription " +
            "LEFT JOIN FETCH p.manufacturer " +
            "LEFT JOIN FETCH p.photos " +
            "where p.id = :id")
    Optional<Product> getProductById(String id);

    @Query("FROM Product p INNER JOIN FETCH p.productDescription " +
            "LEFT JOIN FETCH p.photos WHERE p.createdDate between :from and :to" +
            " ORDER BY p.createdDate")
    List<Product> findByCreatedDateBetween(LocalDateTime from, LocalDateTime to);

    @Query("FROM Product p INNER JOIN FETCH p.productDescription " +
            "LEFT JOIN FETCH p.photos ORDER BY p.createdDate")
    List<Product> findTop10OrOrderByCreatedDate();
}
