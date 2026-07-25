package com.searv.mobileshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    private String model;

    private Integer quantity;

    private Double price;

    private Double amount;

    private String colour;

    private String ram;

    private String storage;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    @JsonIgnore
    private com.searv.mobileshop.entity.Bill bill;
}