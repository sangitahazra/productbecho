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
@Table(name = "stock")
public class Stock implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk")
    private int pk;

    @Column(name = "warehouse_code")
    private String warehouseCode;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "vp_pk")
    private VariantProduct variantProduct;

}
