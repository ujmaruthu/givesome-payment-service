package com.givesome.payment.payment_processor;

import com.givesome.payment.model.ChargeRequestModel;
import com.givesome.payment.model.ChargeResponseModel;
import com.givesome.payment.model.PaymentIntentModel;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import java.math.BigDecimal;
import java.util.Map;

@Log4j2(topic = "Stripe Payment")
@Service
public class StripePayment {

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

            System.out.println(paymentIntent);
            return paymentIntent;
        } catch (StripeException e) {
            return  null;
        }
    }
    public ChargeResponseModel createCharge(ChargeRequestModel chargeModel) {
        try {
            ChargeResponseModel chargeResponseModel =new ChargeResponseModel();
            var amountConverted = amountConversion(chargeModel.getAmount());
            ChargeCreateParams params = ChargeCreateParams.builder()
                    .setAmount(amountConverted)
                    .setCurrency(chargeModel.getCurrency())
                    .setDescription(chargeModel.getDescription())
                    .setSource(chargeModel.getToken())
                    .build();
           var chargeResponse=  Charge.create(params);
            log.info("Amount in cents "+params.getAmount());
            if(chargeResponse!=null&&chargeResponse.getStatus().equalsIgnoreCase("succeeded")){
                chargeResponseModel.setChargeId(chargeResponse.getId());
                chargeResponseModel.setStatus(chargeResponse.getStatus());
                chargeResponseModel.setAmount(chargeResponse.getAmount());
                return  chargeResponseModel;
            }
            else{
                return null;
            }
        }catch (Exception e) {
            log.info("Error: Exception occur in create token "+e.getMessage());
            return  null;
        }
    }

    private Long amountConversion(BigDecimal amount) {
        return amount.multiply(new BigDecimal(100)).longValue();
    }

}
