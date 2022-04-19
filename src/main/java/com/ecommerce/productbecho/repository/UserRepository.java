package com.ecommerce.productbecho.repository;

import com.ecommerce.productbecho.entity.PBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<PBUser, String> {

    PBUser findByUserName(String userName);
}
