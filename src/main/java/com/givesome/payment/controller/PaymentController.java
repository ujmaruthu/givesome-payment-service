package com.givesome.payment.controller;

import com.givesome.payment.constant.ResponseStatusCode;
import com.givesome.payment.model.GiveCardRedeemRequestModel;
import com.givesome.payment.model.GiveCardRequestModel;
import com.givesome.payment.model.PaymentRequestModel;
import com.givesome.payment.model.ResponseModel;
import com.givesome.payment.service.GivecardService;
import com.givesome.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${app.api.cors_origin}")
@RequestMapping(path = "/api/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;
    @Autowired
    GivecardService givecardService;

    @PostMapping("/create")
    public ResponseEntity<ResponseModel> createPayment(@RequestBody PaymentRequestModel paymentRequestModel) {
        var response = paymentService.createPayment(paymentRequestModel);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/givecard")
    public ResponseEntity<ResponseModel> getGiveCard(@RequestBody GiveCardRequestModel giveCardRequestModel) {
        var response = givecardService.getGiveCardResponse(giveCardRequestModel);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/givecard/redeem")
    public ResponseEntity<ResponseModel> givecardRedeem(@RequestBody GiveCardRedeemRequestModel giveCardRedeemRequestModel) {
        var response = givecardService.givecardRedeem(giveCardRedeemRequestModel);
        ResponseModel responseModel = new ResponseModel();
        if (response) {
            responseModel.setStatus(ResponseStatusCode.SUCCESS);
            responseModel.setMessage("Givecard Redeem Success");
        } else {
            responseModel.setStatus(ResponseStatusCode.INTERNAL_SERVER_ERROR);
            responseModel.setMessage("Givecard Redeem Failed");
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @GetMapping("/currency/list")
    public ResponseEntity<ResponseModel> giveCardRedeem() {
        var response = paymentService.getCurrency();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
