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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PBUserServiceImpl implements PBUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    HttpSession httpSession;

    @Autowired
    AbstractOrderRepository abstractOrderRepository;

    @Override
    public PBUser addUser(final GuestUserData guestUserData, HttpServletRequest httpServletRequest,
                          HttpServletResponse httpServletResponse) throws Exception {
        PBUser pbUser = new PBUser();
        pbUser.setUserName(guestUserData.getEmail());
        pbUser.setPhone(guestUserData.getPhone());
        pbUser.setName(guestUserData.getName());
        pbUser.setRole("GUEST");
        PBUser pbUserEntity = userRepository.save(pbUser);
        AbstractOrder abstractOrder = getAbstractOrderFromCookie(httpServletRequest);
        if (null != abstractOrder) {
            abstractOrder.setPbUser(pbUserEntity);
            AbstractOrder abstractOrderEntity = abstractOrderRepository.save(abstractOrder);
            setCookie("abstractOrder", String.valueOf(abstractOrderEntity.getPk()), httpServletResponse);
            return pbUserEntity;
        } else throw new Exception();
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
    public void addAddress(final AddressData addressData, HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse) throws Exception {
        Address address = new Address();
        address.setCity(addressData.getCity());
        address.setLine1(addressData.getLine1());
        address.setLine2(addressData.getLine2());
        address.setName(addressData.getName());
        address.setState(addressData.getState());
        address.setZip(addressData.getZip());
        address.setPhone(addressData.getPhone());
        Address addressEntity = addressRepository.save(address);
        AbstractOrder abstractOrder = getAbstractOrderFromCookie(httpServletRequest);
        if (null != abstractOrder) {
            abstractOrder.setAddress(addressEntity);
            AbstractOrder abstractOrderEntity = abstractOrderRepository.save(abstractOrder);
            setCookie("abstractOrder", String.valueOf(abstractOrderEntity.getPk()), httpServletResponse);
        } else throw new Exception();
    }

    @Override
    public List<PBUser> findAllUsers() {
        return userRepository.findAll();
    }

    public AbstractOrder getAbstractOrderFromCookie(HttpServletRequest httpServletRequest) {
        String abstractOrderPk = readCookie("abstractOrder", httpServletRequest).get();
        Optional<AbstractOrder> abstractOrderOptional = abstractOrderRepository.findById(Integer.valueOf(abstractOrderPk));
        return abstractOrderOptional.orElse(null);
    }


    public Optional<String> readCookie(String key, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getCookies() != null) {
            return Arrays.stream(httpServletRequest.getCookies())
                    .filter(c -> key.equals(c.getName()))
                    .map(Cookie::getValue)
                    .findAny();
        }
        return Optional.empty();
    }
    public void setCookie(String name, String value, HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }
}
