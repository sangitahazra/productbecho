package com.ecommerce.productbecho.repository;

import com.ecommerce.productbecho.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByCode(String code);
}
