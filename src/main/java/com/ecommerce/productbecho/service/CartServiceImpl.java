package com.ecommerce.productbecho.service;

import com.ecommerce.productbecho.entity.*;
import com.ecommerce.productbecho.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
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
    AbstractOrderEntryVariantProductRepository abstractOrderEntryVariantProductRepository;

    @Autowired
    HttpSession httpSession;

    @Override
    public void addProductToCart(String code, int quantity) {
        Cart cart;
        String cartCode;
        AbstractOrder abstractOrder;
        AbstractOrderEntry abstractOrderEntry;
        AbstractOrderEntryVariantProduct abstractOrderEntryVariantProduct;
        if (httpSession.getAttribute("cartID") == null ||
                httpSession.getAttribute("abstractOrder") == null
                || httpSession.getAttribute("abstractOrderEntry") == null) {
            cart = new Cart();
            cartCode = UUID.randomUUID().toString();
            cart.setCode(cartCode);
            cartRepository.save(cart);
            abstractOrder = new AbstractOrder();
            VariantProduct variantProductEntity = variantProductRepository.findByCode(code);
            abstractOrder.setTotalAmount(
                    (variantProductEntity.getPrice()) * quantity);
            abstractOrderRepository.save(abstractOrder);
            abstractOrderEntry = new AbstractOrderEntry();
            abstractOrderEntry.setQuantity(1);
            AbstractOrderEntry abstractOrderEntryEntity = abstractOrderEntryRepository.save(abstractOrderEntry);
            abstractOrderEntryVariantProduct = new AbstractOrderEntryVariantProduct();
            abstractOrderEntryVariantProduct.setAoePk(abstractOrderEntryEntity.getPk());
            abstractOrderEntryVariantProduct.setVpPk(variantProductEntity.getPk());
            abstractOrderEntryVariantProductRepository.save(abstractOrderEntryVariantProduct);
            httpSession.setAttribute("cartID", cartCode);
            httpSession.setAttribute("abstractOrder", abstractOrder);
            httpSession.setAttribute("abstractOrderEntry", abstractOrderEntry);

            httpSession.setAttribute("abstractOrderEntryVariantProduct", abstractOrderEntryVariantProduct);
        } else {
            cartCode = (String) httpSession.getAttribute("cartID");
            abstractOrder = (AbstractOrder) httpSession.getAttribute("abstractOrder");
            abstractOrderEntry = (AbstractOrderEntry) httpSession.getAttribute("abstractOrderEntry");
            abstractOrderEntryVariantProduct = (AbstractOrderEntryVariantProduct)
                    httpSession.getAttribute("abstractOrderEntryVariantProduct");
            calculateOrderTotal(code, quantity, abstractOrder, abstractOrderEntry, abstractOrderEntryVariantProduct);
        }
    }

    private void calculateOrderTotal(String code, int quantity,
                                     AbstractOrder abstractOrder, AbstractOrderEntry abstractOrderEntry, AbstractOrderEntryVariantProduct abstractOrderEntryVariantProduct) {

        VariantProduct variantProductEntity = variantProductRepository.findByCode(code);
        // to write stock check logic
        double total = ((abstractOrder.getTotalAmount()) +
                (variantProductEntity.getPrice()) * quantity);
        abstractOrder.setTotalAmount(total);
        abstractOrderRepository.save(abstractOrder);
        abstractOrderEntry.setQuantity(abstractOrderEntry.getQuantity() + 1);
        AbstractOrderEntry abstractOrderEntryEntity = abstractOrderEntryRepository.save(abstractOrderEntry);
        abstractOrderEntryVariantProduct.setAoePk(abstractOrderEntryEntity.getPk());
        abstractOrderEntryVariantProduct.setVpPk(variantProductEntity.getPk());
        abstractOrderEntryVariantProductRepository.save(abstractOrderEntryVariantProduct);

    }
}
