package com.ecommerce.productbecho.service;

import com.ecommerce.productbecho.entity.AbstractOrder;
import com.ecommerce.productbecho.entity.Address;
import com.ecommerce.productbecho.entity.PBUser;
import com.ecommerce.productbecho.pojo.AddressData;
import com.ecommerce.productbecho.pojo.GuestUserData;
import com.ecommerce.productbecho.repository.AbstractOrderRepository;
import com.ecommerce.productbecho.repository.AddressRepository;
import com.ecommerce.productbecho.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

@Service
public class PBUserServiceImpl implements PBUserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    HttpSession httpSession;

    @Autowired
    AbstractOrderRepository abstractOrderRepository;

    @Override
    public PBUser addUser(final GuestUserData guestUserData) throws Exception {
        PBUser pbUser = new PBUser();
        pbUser.setUserName(guestUserData.getEmail());
        pbUser.setPhone(guestUserData.getPhone());
        pbUser.setName(guestUserData.getName());
        pbUser.setRole("GUEST");
        PBUser pbUserEntity = userRepository.save(pbUser);
        AbstractOrder abstractOrder = (AbstractOrder) httpSession.getAttribute("abstractOrder");
        if(null != abstractOrder) {
            abstractOrder.setPbUser(pbUserEntity);
            AbstractOrder abstractOrderEntity = abstractOrderRepository.save(abstractOrder);
            httpSession.setAttribute("abstractOrder", abstractOrderEntity);
            return pbUserEntity;
        }
        else throw new Exception();
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
    public void addAddress(final AddressData addressData) throws Exception {
        Address address = new Address();
        address.setCity(addressData.getCity());
        address.setLine1(addressData.getLine1());
        address.setLine2(addressData.getLine2());
        address.setName(addressData.getName());
        address.setState(addressData.getState());
        address.setZip(addressData.getZip());
        address.setPhone(addressData.getPhone());
        Address addressEntity = addressRepository.save(address);
        AbstractOrder abstractOrder = (AbstractOrder) httpSession.getAttribute("abstractOrder");
        if(null != abstractOrder) {
            abstractOrder.setAddress(addressEntity);
            AbstractOrder abstractOrderEntity = abstractOrderRepository.save(abstractOrder);
            httpSession.setAttribute("abstractOrder", abstractOrderEntity);
        }
        else throw new Exception();
    }
}
