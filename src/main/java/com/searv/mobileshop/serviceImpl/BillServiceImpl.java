package com.searv.mobileshop.serviceImpl;


import com.searv.mobileshop.dto.BillItemRequest;
import com.searv.mobileshop.dto.BillRequest;
import com.searv.mobileshop.entity.Bill;
import com.searv.mobileshop.entity.BillItem;
import com.searv.mobileshop.respository.BillRepository;
import com.searv.mobileshop.service.BillMailService;
import com.searv.mobileshop.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillMailService billMailService;

    @Override
    public Bill createBill(BillRequest request) {

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("Bill item is required");
        }

        Bill bill = new Bill();

        bill.setCustomerName(request.getCustomerName());
        bill.setMobileNumber(request.getMobileNumber());
        bill.setCustomerEmail(request.getCustomerEmail());
        bill.setBillDate(LocalDateTime.now());

        double totalAmount = 0.0;

        for (BillItemRequest itemRequest : request.getItems()) {

            BillItem item = new BillItem();

            item.setBrand(itemRequest.getBrand());
            item.setModel(itemRequest.getModel());
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(itemRequest.getPrice());
            item.setColour(itemRequest.getColour());
            item.setRam(itemRequest.getRam());
            item.setStorage(itemRequest.getStorage());

            double amount = itemRequest.getQuantity() * itemRequest.getPrice();

            item.setAmount(amount);
            item.setBill(bill);

            bill.getItems().add(item);

            totalAmount = totalAmount + amount;
        }

        double discount = request.getDiscount() == null ? 0.0 : request.getDiscount();

        bill.setTotalAmount(totalAmount);
        bill.setDiscount(discount);
        bill.setFinalAmount(totalAmount - discount);

        Bill savedBill = billRepository.save(bill);

        billMailService.sendBillMail(savedBill);

        return savedBill;
    }

    @Override
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    @Override
    public Bill getBillById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found with id : " + id));
    }
}