package com.searv.mobileshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name="mobile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mobile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "BRAND")
    private String brand;
    @Column(name = "MODEL")
    private String model;
    @Column(name = "COLOUR")
    private String colour;
    @Column(name = "RAM")
    private String ram;
    @Column(name = "STORAGE")
    private String storage;
    @Column(name = "PRICE")
    private String price;
    @Column(name = "STOCK")
    private String stock;
    @Column(name = "CREATED_DATE")
    private Timestamp createdDate;
    @Column(name = "IMAGE")
    private String image;

}
