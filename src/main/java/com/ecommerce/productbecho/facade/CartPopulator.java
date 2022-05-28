package com.ecommerce.productbecho.facade;

import com.ecommerce.productbecho.dto.CartData;
import com.ecommerce.productbecho.dto.CartEntryData;
import com.ecommerce.productbecho.dto.ProductDTO;
import com.ecommerce.productbecho.entity.AbstractOrder;
import com.ecommerce.productbecho.entity.VariantProduct;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class CartPopulator {

    public CartData populate(AbstractOrder abstractOrder) {
        CartData cartData = new CartData();
        if (Objects.nonNull(abstractOrder.getCart())) {
            cartData.setCode(abstractOrder.getCart().getCode());
        }
        cartData.setTotal(abstractOrder.getTotalAmount());
        List<CartEntryData> entries = new ArrayList<>();
        if (!CollectionUtils.isEmpty(abstractOrder.getAbstractOrderEntries())) {
            abstractOrder.getAbstractOrderEntries().stream().forEach(entry -> {
                CartEntryData cartEntryData = new CartEntryData();
                cartEntryData.setQuantity(entry.getQuantity());

                ProductDTO productDTO = new ProductDTO();
                VariantProduct variantProduct = entry.getVariantProduct();
                productDTO.setCode(variantProduct.getCode());
                productDTO.setDescription(variantProduct.getDescription());
                productDTO.setImage(Base64.getEncoder().encodeToString(variantProduct.getImage()));
                productDTO.setPrice(variantProduct.getPrice());
                productDTO.setName(variantProduct.getName());

                cartEntryData.setProduct(productDTO);

                entries.add(cartEntryData);
            });
        }
        cartData.setEntries(entries);
        return cartData;
    }
}
