package com.ecommerce.productbecho.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartEntryData {
    private Integer quantity;
    private ProductDTO product;
}
