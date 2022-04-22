package com.ecommerce.productbecho.service;

import com.ecommerce.productbecho.entity.PBUser;
import com.ecommerce.productbecho.pojo.AddressData;
import com.ecommerce.productbecho.pojo.GuestUserData;

import java.util.Optional;

public interface PBUserService {

    void addUser(GuestUserData guestUserData);

    Optional<PBUser> getUser(GuestUserData guestUserData);

    void addAddress(AddressData addressData);
}
