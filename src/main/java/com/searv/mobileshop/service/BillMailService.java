package com.searv.mobileshop.service;

import com.searv.mobileshop.entity.Bill;

public interface BillMailService {

    void sendBillMail(Bill bill);
}