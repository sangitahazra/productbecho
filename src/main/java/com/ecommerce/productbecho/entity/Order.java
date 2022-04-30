package com.ecommerce.productbecho.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "`order`")
public class Order implements Serializable {
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
