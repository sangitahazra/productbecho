package com.ecommerce.productbecho.service;

import com.ecommerce.productbecho.entity.Address;
import com.ecommerce.productbecho.entity.PBUser;
import com.ecommerce.productbecho.pojo.AddressData;
import com.ecommerce.productbecho.pojo.GuestUserData;
import com.ecommerce.productbecho.repository.AddressRepository;
import com.ecommerce.productbecho.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PBUserServiceImpl implements PBUserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public void addUser(final GuestUserData guestUserData) {
        PBUser pbUser = new PBUser();
        pbUser.setUserName(guestUserData.getEmail());
        pbUser.setPhone(guestUserData.getPhone());
        pbUser.setRole("GUEST");
        userRepository.save(pbUser);
    }

    @Override
    public Optional<PBUser> getUser(final GuestUserData guestUserData) {
        PBUser pbUser = userRepository.findByUserName(guestUserData.getEmail());
        if (Objects.nonNull(pbUser)) {
            pbUser.setPhone(guestUserData.getPhone());
            userRepository.save(pbUser);
            return Optional.of(pbUser);
        }
        return Optional.empty();
    }

    @Override
    public void addAddress(final AddressData addressData) {
        Address address = new Address();
        address.setCity(addressData.getCity());
        address.setLine1(addressData.getLine1());
        address.setLine2(addressData.getLine2());
        address.setName(addressData.getName());
        address.setState(addressData.getState());
        address.setZip(addressData.getZip());
        address.setPhone(addressData.getPhone());
        addressRepository.save(address);
    }
}
