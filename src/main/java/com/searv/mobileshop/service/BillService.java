package com.searv.mobileshop.service;

import com.searv.mobileshop.dto.BillRequest;
import com.searv.mobileshop.entity.Bill;

import java.util.List;

public interface BillService {

    Bill createBill(BillRequest request);

    List<Bill> getAllBills();

    Bill getBillById(Long id);
}