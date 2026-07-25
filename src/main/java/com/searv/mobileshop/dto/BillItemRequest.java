package com.searv.mobileshop.dto;

import lombok.Data;

@Data
public class BillItemRequest {

    private String brand;

    private String model;

    private Integer quantity;

    private Double price;

    private String colour;

    private String ram;

    private String storage;
}