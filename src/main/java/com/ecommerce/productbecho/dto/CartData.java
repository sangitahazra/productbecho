package com.ecommerce.productbecho.dto;

import com.ecommerce.productbecho.pojo.AddressData;
import com.ecommerce.productbecho.pojo.GuestUserData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartData {
    private String code;
    private List<CartEntryData> entries;
    private AddressData address;
    private GuestUserData user;
    private Double total;
}
