package com.ecommerce.productbecho.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class PBUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk")
    private int pk;

    @Column(name = "username")
    private String userName;

    @Column(name = "role")
    private String role;

    @Column(name = "password")
    private String pwd;

    @Column(name = "phone")
    private String phone;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "pbUser")
    private Set<AbstractOrder> abstractOrders;

}
