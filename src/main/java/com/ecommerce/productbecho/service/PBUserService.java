package com.ecommerce.productbecho.service;

import com.ecommerce.productbecho.entity.PBUser;
import com.ecommerce.productbecho.pojo.AddressData;
import com.ecommerce.productbecho.pojo.GuestUserData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface PBUserService {

    PBUser addUser(GuestUserData guestUserData, HttpServletRequest httpServletRequest,
                   HttpServletResponse httpServletResponse) throws Exception;

    Optional<PBUser> getUser(GuestUserData guestUserData);

    void addAddress(AddressData addressData, HttpServletRequest httpServletRequest,
                    HttpServletResponse httpServletResponse) throws Exception;

    List<PBUser> findAllUsers();
}
