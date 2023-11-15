package com.givesome.payment.payment_processor;

import com.givesome.payment.model.PaymentRequestModel;
import com.givesome.payment.model.PaymentResponseModel;
import com.givesome.payment.model.PaymentIntentModel;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import java.math.BigDecimal;

@Log4j2(topic = "Stripe Payment")
@Service
public class StripePayment {
    public Charge createPayment(PaymentRequestModel paymentRequestModel) {
        try {
            var amountConverted = amountConversion(paymentRequestModel.getYouGive());
            ChargeCreateParams params = ChargeCreateParams.builder()
                    .setAmount(amountConverted)
                    .setCurrency(paymentRequestModel.getCurrency())
                    .setDescription(paymentRequestModel.getDescription())
                    .setSource(paymentRequestModel.getToken())
                    .build();
            log.info("Amount in cents "+params.getAmount());
            return  Charge.create(params);
        }catch (Exception e) {
            log.info("Error: Exception occur in create payment in stripe "+e.getMessage());
            return  null;
        }
    }
    public PaymentIntent createPaymentIntent(PaymentIntentModel paymentIntentModel) {

        PaymentIntent paymentIntent;
        try {
            paymentIntent = PaymentIntent.create(
                    PaymentIntentCreateParams.builder()
                            .setAmount(paymentIntentModel.getAmount())
                            .setCurrency(paymentIntentModel.getCurrency())
                            .setPaymentMethod(paymentIntentModel.getPaymentMethod())
                            .setDescription(paymentIntentModel.getDescription())
                            .build()
            );

            return paymentIntent;
        } catch (StripeException e) {
            return  null;
        }
    }


    private Long amountConversion(BigDecimal amount) {
        return amount.multiply(new BigDecimal(100)).longValue();
    }

}
