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
@Table(name = "abstract_order_to_abstract_entry")
public class AbstractOrderToAbstractEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk")
    private int pk;

    @Column(name = "abstract_order_pk")
    private int abstractOrderPk;

    @Column(name = "ae_pk")
    private int abstractOrderEntryPk;
}
