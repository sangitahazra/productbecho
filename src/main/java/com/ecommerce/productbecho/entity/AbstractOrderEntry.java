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
@Table(name = "abstract_order_entry")
public class AbstractOrderEntry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk")
    private int pk;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "vp_pk")
    private VariantProduct variantProduct;

    @ManyToOne
    @JoinColumn(name = "abstract_order_pk")
    private AbstractOrder abstractOrder;
}
