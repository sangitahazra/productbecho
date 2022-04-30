package com.ecommerce.productbecho.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk")
    private int pk;

    @Column(name = "code")
    private String code;

    @OneToOne
    @JoinColumn(name = "abstract_order_pk")
    private AbstractOrder abstractOrder;
}
