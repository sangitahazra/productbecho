package com.ecommerce.productbecho.repository;

import com.ecommerce.productbecho.entity.AbstractOrderToAbstractEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbstractOrderToAbstractEntryRepository extends JpaRepository<AbstractOrderToAbstractEntry, Integer> {

}
