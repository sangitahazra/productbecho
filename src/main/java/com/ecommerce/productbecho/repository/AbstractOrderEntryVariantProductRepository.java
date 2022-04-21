package com.ecommerce.productbecho.repository;

import com.ecommerce.productbecho.entity.AbstractOrderEntryVariantProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbstractOrderEntryVariantProductRepository extends JpaRepository<AbstractOrderEntryVariantProduct, Integer> {
}