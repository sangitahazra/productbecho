package com.ecommerce.productbecho.repository;

import com.ecommerce.productbecho.entity.AbstractOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbstractOrderRepository extends JpaRepository<AbstractOrder,Integer> {
}
