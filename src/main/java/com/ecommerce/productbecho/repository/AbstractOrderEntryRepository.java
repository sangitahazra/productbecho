package com.ecommerce.productbecho.repository;

import com.ecommerce.productbecho.entity.AbstractOrder;
import com.ecommerce.productbecho.entity.AbstractOrderEntry;
import com.ecommerce.productbecho.entity.VariantProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AbstractOrderEntryRepository extends JpaRepository<AbstractOrderEntry, Integer> {

    AbstractOrderEntry findByVariantProductAndAbstractOrder(VariantProduct variantProductEntity, AbstractOrder abstractOrderEntity);
}

