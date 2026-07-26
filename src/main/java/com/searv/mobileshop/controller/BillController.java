package com.searv.mobileshop.controller;

import com.searv.mobileshop.dto.BillRequest;
import com.searv.mobileshop.entity.Bill;
import com.searv.mobileshop.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mobile-shop/bill")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://jagdamba-mobile-shoppe-electronics.netlify.app"
})
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/create")
    public Bill createBill(@RequestBody BillRequest request) {
        return billService.createBill(request);
    }

    @GetMapping("/getAll")
    public List<Bill> getAllBills() {
        return billService.getAllBills();
    }

    @GetMapping("/{id}")
    public Bill getBillById(@PathVariable Long id) {
        return billService.getBillById(id);
    }
}