package com.ecommerce.productbecho.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "abstract_order")
public class AbstractOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk")
    private int pk;

    @Column(name = "total_amount")
    private Double totalAmount;

    @OneToMany(mappedBy = "abstractOrder")
    private Set<AbstractOrderEntry> abstractOrderEntries;

    @OneToOne(mappedBy = "abstractOrder")
    private Cart cart;

}