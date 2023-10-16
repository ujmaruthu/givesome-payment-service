package com.givesome.payment.controller;

import com.givesome.payment.model.ChargeRequestModel;
import com.givesome.payment.model.ResponseModel;
import com.givesome.payment.service.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${app.api.cors_origin}")
@RequestMapping(path = "/api/charge")
public class ChargeController {
    @Autowired
    ChargeService chargeService;

    @PostMapping("/create")
    public ResponseEntity<ResponseModel> createCharge(@RequestBody ChargeRequestModel chargeModel) {
        var response = chargeService.createCharge(chargeModel);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
