package com.bzw.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="t_orderlist")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class OrderLineItems {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String skucode;
    private BigDecimal price;
    private Integer quantity;


}
