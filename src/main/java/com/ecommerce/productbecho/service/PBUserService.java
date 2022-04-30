package com.ecommerce.productbecho.service;

import com.ecommerce.productbecho.entity.PBUser;
import com.ecommerce.productbecho.pojo.AddressData;
import com.ecommerce.productbecho.pojo.GuestUserData;

import java.util.Optional;

public interface PBUserService {

    PBUser addUser(GuestUserData guestUserData) throws Exception;

    Optional<PBUser> getUser(GuestUserData guestUserData);

    void addAddress(AddressData addressData) throws Exception;
}
