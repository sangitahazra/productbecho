package com.ecommerce.productbecho.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuestUserData {
    private String email;
    private String phone;
    private String name;
}
