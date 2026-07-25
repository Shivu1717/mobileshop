package com.searv.mobileshop.respository;

import com.searv.mobileshop.entity.Mobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileRepository  extends JpaRepository<Mobile,Long> {


    boolean existsByBrandAndModelAndRamAndStorageAndColour(
            String brand,
            String model,
            String ram,
            String storage,
            String colour
    );


}
