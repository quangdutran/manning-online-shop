package org.dutq.service.product.repository;

import com.dutq.core.model.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, BigInteger> {
    @Query("FROM ProductCategory pc " +
            "left join fetch pc.parent " +
            "left join fetch pc.childCategories WHERE pc.id = :id")
    Optional<ProductCategory> getProductCategoryById(BigInteger id);

    @Query("FROM ProductCategory pc " +
            "left join fetch pc.parent " +
            "left join fetch pc.childCategories WHERE pc.parent = null")
    List<ProductCategory> getProductCategories();
}
