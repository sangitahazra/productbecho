package com.ecommerce.productbecho.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk")
    private int pk;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "abstract_order_pk")
    private AbstractOrder abstractOrder;
}
