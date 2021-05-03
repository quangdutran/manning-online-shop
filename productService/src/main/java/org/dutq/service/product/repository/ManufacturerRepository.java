package org.dutq.service.product.repository;

import com.dutq.core.model.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, BigInteger> {
}
