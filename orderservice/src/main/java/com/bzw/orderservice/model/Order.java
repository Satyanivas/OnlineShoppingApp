package com.bzw.orderservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "t_order")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String ordernumber;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItems> orderLineItems;
}
