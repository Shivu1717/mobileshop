package com.searv.mobileshop.serviceImpl;

import com.searv.mobileshop.dto.MobileRequest;
import com.searv.mobileshop.entity.Mobile;
import com.searv.mobileshop.respository.MobileRepository;
import com.searv.mobileshop.service.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class MobileServiceImpl implements MobileService {

    @Autowired
    MobileRepository mobileRepository;

    @Override
    public Mobile saveMobileData(MobileRequest request) {

        if (mobileRepository.existsByBrandAndModelAndRamAndStorageAndColour(
                request.getBrand(),
                request.getModel(),
                request.getRam(),
                request.getStorage(),
                request.getColour())) {

            throw new RuntimeException("Record Already Exists With Given Specifications");
        }

        Mobile mobileData = new Mobile();
        mobileData.setBrand(request.getBrand());
        mobileData.setColour(request.getColour());
        mobileData.setPrice(request.getPrice());
        mobileData.setRam(request.getRam());
        mobileData.setModel(request.getModel());
        mobileData.setStorage(request.getStorage());
        mobileData.setStock(request.getStock());
        mobileData.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        mobileData.setImage(request.getImage());

        return mobileRepository.save(mobileData);
    }

    @Override
    public List<Mobile> getAllMobile() {
        return mobileRepository.findAll();
    }

    @Override
    public Mobile updateMobile(Long id, Mobile mobile) {

        Mobile existingMobile = mobileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mobile not found with id: " + id));

        existingMobile.setBrand(mobile.getBrand());
        existingMobile.setModel(mobile.getModel());
        existingMobile.setColour(mobile.getColour());
        existingMobile.setRam(mobile.getRam());
        existingMobile.setStorage(mobile.getStorage());
        existingMobile.setPrice(mobile.getPrice());
        existingMobile.setStock(mobile.getStock());
        existingMobile.setImage(mobile.getImage());

        return mobileRepository.save(existingMobile);
    }

    @Override
    public void deleteMobile(Long id) {

        Mobile existingMobile = mobileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mobile not found with id: " + id));

        mobileRepository.delete(existingMobile);
    }

}
