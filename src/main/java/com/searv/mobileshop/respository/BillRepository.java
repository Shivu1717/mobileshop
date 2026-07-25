package com.searv.mobileshop.respository;

import com.searv.mobileshop.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}