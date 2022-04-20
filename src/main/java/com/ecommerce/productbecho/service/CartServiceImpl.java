package com.ecommerce.productbecho.service;

import com.ecommerce.productbecho.entity.AbstractOrder;
import com.ecommerce.productbecho.entity.AbstractOrderEntry;
import com.ecommerce.productbecho.entity.Cart;
import com.ecommerce.productbecho.entity.VariantProduct;
import com.ecommerce.productbecho.repository.AbstractOrderEntryRepository;
import com.ecommerce.productbecho.repository.AbstractOrderRepository;
import com.ecommerce.productbecho.repository.CartRepository;
import com.ecommerce.productbecho.repository.VariantProductRepository;
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
    HttpSession httpSession;

    @Override
    public void addProductToCart(String code, int quantity) {
        Cart cart;
        String cartCode;
        AbstractOrder abstractOrder;
        AbstractOrderEntry abstractOrderEntry;
        if (httpSession.getAttribute("cartID") == null ||
                httpSession.getAttribute("abstractOrder") == null) {
            cart = new Cart();
            cartCode = UUID.randomUUID().toString();
            cart.setCode(cartCode);
            cartRepository.save(cart);
            abstractOrder = new AbstractOrder();
            abstractOrder.setTotalAmount(
                    (variantProductRepository.findByCode(code).getPrice()) * quantity);
            abstractOrderRepository.save(abstractOrder);
            abstractOrderEntry = new AbstractOrderEntry();
            abstractOrderEntry.setQuantity(1);
            abstractOrderEntryRepository.save(abstractOrderEntry);
            httpSession.setAttribute("cartID", cartCode);
            httpSession.setAttribute("abstractOrderEntry", abstractOrderEntry);
            httpSession.setAttribute("abstractOrder", abstractOrder);
        } else {
            cartCode = (String) httpSession.getAttribute("cartID");
            abstractOrder = (AbstractOrder) httpSession.getAttribute("abstractOrder");
            abstractOrderEntry = (AbstractOrderEntry) httpSession.getAttribute("abstractOrderEntry");
            calculateOrderTotal(code, quantity, abstractOrder, abstractOrderEntry);
        }
    }

    private void calculateOrderTotal(String code, int quantity,
                                     AbstractOrder abstractOrder, AbstractOrderEntry abstractOrderEntry) {

        VariantProduct variantProduct = variantProductRepository.findByCode(code);
        // to write stock check logic
        double total = ((abstractOrder.getTotalAmount()) +
                (variantProduct.getPrice()) * quantity);
        abstractOrder.setTotalAmount(total);
        abstractOrderRepository.save(abstractOrder);
        abstractOrderEntry.setQuantity(abstractOrderEntry.getQuantity() + 1);
        abstractOrderEntryRepository.save(abstractOrderEntry);
    }
}
