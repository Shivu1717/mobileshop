package com.searv.mobileshop.service;

import com.searv.mobileshop.dto.MobileRequest;
import com.searv.mobileshop.entity.Mobile;

import java.util.List;


public interface MobileService {
    public Mobile saveMobileData(MobileRequest mobile);
    public List<Mobile> getAllMobile();
    public void deleteMobile(Long id);
    public Mobile updateMobile(Long id, Mobile mobile);
}