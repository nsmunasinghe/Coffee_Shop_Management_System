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
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id")
    private String productID;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "date")
    private Date date;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<OrderInfo> orderInfos;

    public Product(String productID, String description, String type, Double unitPrice, Integer stock, String imagePath, Date date) {
        this.productID = productID;
        this.description = description;
        this.type = type;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.imagePath = imagePath;
        this.date = date;
    }
}
