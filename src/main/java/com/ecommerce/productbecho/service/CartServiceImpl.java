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
    StockRepository stockRepository;


    @Autowired
    HttpSession httpSession;

    @Override
    public void addProductToCart(String code, int quantity) throws Exception {
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
            if (httpSession.getAttribute("cartID") == null ||
                    httpSession.getAttribute("abstractOrder") == null) {
                cart = new Cart();
                abstractOrder = new AbstractOrder();
                abstractOrder.setTotalAmount(variantProductEntity.getPrice() * quantity);
                AbstractOrder abstractOrderEntity = abstractOrderRepository.save(abstractOrder);
                cartCode = UUID.randomUUID().toString();
                cart.setCode(cartCode);
                cart.setAbstractOrderPk(abstractOrderEntity.getPk());
                cartRepository.save(cart);
                abstractOrderEntry = new AbstractOrderEntry();
                abstractOrderEntry.setQuantity(quantity);
                abstractOrderEntry.setVariantProduct(variantProductEntity);
                abstractOrderEntry.setAbstractOrder(abstractOrderEntity);
                abstractOrderEntryRepository.save(abstractOrderEntry);
                httpSession.setAttribute("cartID", cartCode);
                httpSession.setAttribute("abstractOrder", abstractOrderEntity);
            } else {
                abstractOrder = (AbstractOrder) httpSession.getAttribute("abstractOrder");
                calculateOrderTotal(code, quantity, abstractOrder, variantProductEntity);
            }
        } else throw new Exception();
    }

    private void calculateOrderTotal(String code, int quantity,
                                     AbstractOrder abstractOrder, VariantProduct variantProductEntity) {

        double total = ((abstractOrder.getTotalAmount()) +
                (variantProductEntity.getPrice()) * quantity);
        abstractOrder.setTotalAmount(total);
        AbstractOrder abstractOrderEntity = abstractOrderRepository.save(abstractOrder);
        httpSession.setAttribute("abstractOrder", abstractOrderEntity);
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
}

