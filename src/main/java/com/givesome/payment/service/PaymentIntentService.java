package com.givesome.payment.service;

import com.givesome.payment.model.PaymentIntentModel;
import com.givesome.payment.payment_processor.StripePayment;
import com.stripe.model.PaymentIntent;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentIntentService {

    @Autowired
    StripePayment stripePayment;

    public PaymentIntent createPaymentIntent(PaymentIntentModel paymentIntentModel) {

        try {
            return stripePayment.createPaymentIntent(paymentIntentModel);

        } catch (Exception e) {
            return null;
        }
    }


}
