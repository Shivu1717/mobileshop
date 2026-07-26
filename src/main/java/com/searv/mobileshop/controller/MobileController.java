package com.searv.mobileshop.controller;

import com.searv.mobileshop.dto.MobileRequest;
import com.searv.mobileshop.entity.Mobile;
import com.searv.mobileshop.service.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://jagdamba-mobile-shoppe-electronics.netlify.app"
})
@RestController
@RequestMapping("/mobile-shop/operation")
public class MobileController {

    @Autowired
    private MobileService mobileService;

    @PostMapping("/saveData")
    public ResponseEntity<String> saveMobile(@RequestBody MobileRequest request) {
        try {
            Mobile mobileSavedData = mobileService.saveMobileData(request);

            return ResponseEntity.ok(
                    "Mobile Data Saved Successfully"
            );

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping ("/getAllData")
    public ResponseEntity<List> getAllMobile(){
        List<Mobile> MobileList=mobileService.getAllMobile();
        return ResponseEntity.ok(MobileList);
    }

    @PutMapping("/updateData/{id}")
    public ResponseEntity<Mobile> updateMobileData(
            @PathVariable Long id,
            @RequestBody Mobile mobile) {

        Mobile updatedMobile = mobileService.updateMobile(id, mobile);
        return ResponseEntity.ok(updatedMobile);
    }

    @DeleteMapping("/deleteData/{id}")
    public ResponseEntity<String> deleteMobile(@PathVariable Long id) {

        mobileService.deleteMobile(id);
        return ResponseEntity.ok("Mobile deleted successfully");
    }
}
