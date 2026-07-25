package com.searv.mobileshop.dto;

import lombok.Data;

@Data
public class MobileRequest {

    private String brand;
    private String model;
    private String colour;
    private String ram;
    private String storage;
    private String price;
    private String stock;
    private String image;

}
