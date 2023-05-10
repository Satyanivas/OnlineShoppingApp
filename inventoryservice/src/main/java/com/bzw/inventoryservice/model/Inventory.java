package com.bzw.inventoryservice.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name="t_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String skucode;
    private Integer quantity;
}
