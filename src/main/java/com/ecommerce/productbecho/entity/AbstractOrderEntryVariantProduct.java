package com.ecommerce.productbecho.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "abstract_order_entry_variant_product")
public class AbstractOrderEntryVariantProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk")
    private int pk;

    @Column(name = "aoe_pk")
    private int aoePk;

    @Column(name = "vp_pk")
    private int vpPk;
}
