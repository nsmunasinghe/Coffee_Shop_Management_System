package com.bion.project2.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    private String orderID;

    @Column(name = "customer_id")
    private String customerID;

    @Column(name = "total")
    private Double total;

    @Column(name = "paid_amount")
    private Double paidAmount;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<OrderInfo> orderInfos;
}
