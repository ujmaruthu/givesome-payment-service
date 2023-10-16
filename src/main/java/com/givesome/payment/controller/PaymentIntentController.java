package com.givesome.payment.controller;

import com.givesome.payment.model.PaymentIntentModel;
import com.givesome.payment.service.PaymentIntentService;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/payment_intent")
public class PaymentIntentController {
    @Autowired
    PaymentIntentService paymentIntentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentIntent> createPaymentIntent(@RequestBody PaymentIntentModel paymentIntentModel) {
        var response = paymentIntentService.createPaymentIntent(paymentIntentModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
