package com.ecommerce.productbecho.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressData {
    private String name;

    private String line1;

    private String line2;

    private String city;

    private String state;

    private String zip;

    private String phone;
}
