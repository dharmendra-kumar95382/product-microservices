package com.altimetrik.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "inventory")
public class Inventory implements Serializable {
    private static final long serialVersionUID = -1236808518386483799L;

    @Id
    @Column(nullable = false)
    private String productId;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private Double productPrice;
    @Column(nullable = false)
    private Integer productQuantity;
    @Column(nullable = false)
    private String productCategory;

}
