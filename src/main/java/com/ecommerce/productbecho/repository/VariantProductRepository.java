package com.ecommerce.productbecho.repository;

import com.ecommerce.productbecho.entity.VariantProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantProductRepository extends JpaRepository<VariantProduct, Integer> {
}
