package org.dutq.service.product.repository;

import com.dutq.core.model.entity.ProductDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, BigInteger> {
}
