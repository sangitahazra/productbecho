package com.ecommerce.productbecho.service;

import com.ecommerce.productbecho.entity.*;
import com.ecommerce.productbecho.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    AbstractOrderRepository abstractOrderRepository;

    @Autowired
    VariantProductRepository variantProductRepository;

    @Autowired
    AbstractOrderEntryRepository abstractOrderEntryRepository;

    @Autowired
    StockRepository stockRepository;


    @Override
    public void addProductToCart(String code, int quantity, HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse)
            throws Exception {
        Cart cart;
        String cartCode;
        AbstractOrder abstractOrder;
        AbstractOrderEntry abstractOrderEntry;
        VariantProduct variantProductEntity = variantProductRepository.findByCode(code);
        Stock stockEntity;
        if (variantProductEntity != null) {
            stockEntity = stockRepository.findByVariantProduct(variantProductEntity);
        } else throw new Exception();
        if (stockEntity != null && stockEntity.getQuantity() >= quantity) {
            if (readCookie("cartCode", httpServletRequest).isEmpty()
                    || readCookie("abstractOrder", httpServletRequest).isEmpty()) {
                cart = new Cart();
                abstractOrder = new AbstractOrder();
                abstractOrder.setTotalAmount(variantProductEntity.getPrice() * quantity);
                AbstractOrder abstractOrderEntity = abstractOrderRepository.save(abstractOrder);
                cartCode = UUID.randomUUID().toString();
                cart.setCode(cartCode);
                cart.setAbstractOrder(abstractOrderEntity);
                cartRepository.save(cart);
                abstractOrderEntry = new AbstractOrderEntry();
                abstractOrderEntry.setQuantity(quantity);
                abstractOrderEntry.setVariantProduct(variantProductEntity);
                abstractOrderEntry.setAbstractOrder(abstractOrderEntity);
                abstractOrderEntryRepository.save(abstractOrderEntry);
                setCookie("cartCode", cartCode, httpServletResponse);
                setCookie("abstractOrder", String.valueOf(abstractOrderEntity.getPk()),
                        httpServletResponse);
            } else {
                String abstractOrderPk = readCookie("abstractOrder", httpServletRequest).get();
                Optional<AbstractOrder> abstractOrderOptional = abstractOrderRepository.findById(Integer.valueOf(abstractOrderPk));
                if (abstractOrderOptional.isPresent()) {
                    abstractOrder = abstractOrderOptional.get();
                    calculateOrderTotal(code, quantity, abstractOrder, variantProductEntity, httpServletResponse);
                }
            }
        } else throw new Exception();
    }

    @Override
    public void updateQuantity(String code, int quantity, HttpServletRequest httpServletRequest) throws Exception {
        String abstractOrderPk = readCookie("abstractOrder", httpServletRequest).get();
        Optional<AbstractOrder> abstractOrderOptional = abstractOrderRepository.findById(Integer.valueOf(abstractOrderPk));
        if (abstractOrderOptional.isPresent()) {
            AbstractOrder abstractOrder = abstractOrderOptional.get();
            VariantProduct variantProductEntity = variantProductRepository.findByCode(code);
            if (variantProductEntity != null
                    && stockRepository.findByVariantProduct(variantProductEntity).getQuantity() >= quantity) {
                AbstractOrderEntry abstractOrderEntry =
                        abstractOrderEntryRepository.
                                findByVariantProductAndAbstractOrder(variantProductEntity, abstractOrder);
                abstractOrderEntry.setQuantity(quantity);
                abstractOrderEntryRepository.save(abstractOrderEntry);
            }
        } else throw new Exception();

    }

    @Override
    public void removeProduct(String code, HttpServletRequest httpServletRequest) throws Exception {
        String abstractOrderPk = readCookie("abstractOrder", httpServletRequest).get();
        Optional<AbstractOrder> abstractOrderOptional = abstractOrderRepository.findById(Integer.valueOf(abstractOrderPk));
        if (abstractOrderOptional.isPresent()) {
            AbstractOrder abstractOrder = abstractOrderOptional.get();
            VariantProduct variantProductEntity = variantProductRepository.findByCode(code);
            AbstractOrderEntry abstractOrderEntryEntity = abstractOrderEntryRepository.findByVariantProductAndAbstractOrder(
                    variantProductEntity, abstractOrder);
            abstractOrder.setTotalAmount(abstractOrder.getTotalAmount()
                    - (abstractOrderEntryEntity.getQuantity() * variantProductEntity.getPrice()));
            abstractOrderRepository.save(abstractOrder);
            abstractOrderEntryRepository.delete(abstractOrderEntryEntity);
        } else throw new Exception();
    }

    private void calculateOrderTotal(String code, int quantity,
                                     AbstractOrder abstractOrder, VariantProduct variantProductEntity,
                                     HttpServletResponse httpServletResponse) {

        double total = ((abstractOrder.getTotalAmount()) +
                (variantProductEntity.getPrice()) * quantity);
        abstractOrder.setTotalAmount(total);
        AbstractOrder abstractOrderEntity = abstractOrderRepository.save(abstractOrder);
        setCookie("abstractOrder", String.valueOf(abstractOrderEntity.getPk()), httpServletResponse);
        AbstractOrderEntry abstractOrderEntry =
                abstractOrderEntryRepository.
                        findByVariantProductAndAbstractOrder(variantProductEntity, abstractOrderEntity);
        if (abstractOrderEntry != null) {
            abstractOrderEntry.setQuantity(abstractOrderEntry.getQuantity() + quantity);
            abstractOrderEntryRepository.save(abstractOrderEntry);
        } else {
            AbstractOrderEntry abstractOrderNewEntry = new AbstractOrderEntry();
            abstractOrderNewEntry.setAbstractOrder(abstractOrderEntity);
            abstractOrderNewEntry.setVariantProduct(variantProductEntity);
            abstractOrderNewEntry.setQuantity(quantity);
            abstractOrderEntryRepository.save(abstractOrderNewEntry);
        }
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

