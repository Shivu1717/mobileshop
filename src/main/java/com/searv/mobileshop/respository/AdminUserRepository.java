package com.searv.mobileshop.respository;

import com.searv.mobileshop.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, Integer> {

    AdminUser findByUsernameAndPassword(String username, String password);

    AdminUser findByMobileNumber(String mobileNumber);

    AdminUser findByEmail(String email);

    AdminUser findByUsername(String username);


}

