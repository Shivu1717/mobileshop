package com.searv.mobileshop.dto;

import lombok.Data;
import java.util.List;

@Data
public class BillRequest {

    private String customerName;

    private String mobileNumber;

    private String customerEmail;

    private Double discount;

    private List<BillItemRequest> items;
}