package com.ecommerce.productbecho.repository;

import com.ecommerce.productbecho.entity.VariantProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface VariantProductRepository extends JpaRepository<VariantProduct, Integer> {
    VariantProduct findByCode(String code);

    @Query(value = "SELECT vp FROM VariantProduct vp WHERE vp.name LIKE %:name%" +
            " OR vp.code LIKE %:code% OR vp.description LIKE  %:description%")
    Set<VariantProduct> findByKey(@Param("name") String name,
                                  @Param("code") String code,
                                  @Param("description") String description);
}
