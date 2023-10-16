package com.givesome.payment.service;

import com.givesome.payment.constant.ResponseStatusCode;
import com.givesome.payment.model.ChargeRequestModel;
import com.givesome.payment.model.ResponseModel;
import com.givesome.payment.payment_processor.StripePayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ChargeService {

    @Autowired
    StripePayment stripePayment;

    public ResponseModel createCharge(ChargeRequestModel chargeModel) {
        ResponseModel responseModel = new ResponseModel();

        try {
            if(chargeModel.getAmount()!=null&&chargeModel.getCurrency()!=null
                    &&!chargeModel.getCurrency().isBlank()&&chargeModel.getToken()!=null&&!chargeModel.getToken().isBlank()&&!isLessThan0_5(chargeModel.getAmount())) {
                var chargeCreationResponse = stripePayment.createCharge(chargeModel);
                if (chargeCreationResponse != null) {
                    responseModel.setStatus(ResponseStatusCode.SUCCESS);
                    responseModel.setMessage("Payment Success");
                    responseModel.setData(chargeCreationResponse);
                } else {
                    responseModel.setStatus(ResponseStatusCode.INTERNAL_SERVER_ERROR);
                    responseModel.setMessage("Payment Failed");
                }
            }
            else{
                responseModel.setStatus(ResponseStatusCode.NOT_FOUND_EXCEPTION);
                responseModel.setMessage("Bad Request");
            }
            return responseModel;
        } catch (Exception e) {
            responseModel.setStatus(ResponseStatusCode.INTERNAL_SERVER_ERROR);
            responseModel.setMessage("Payment Failed - "+e.getMessage());
            return responseModel;
        }
    }

    public static boolean isLessThan0_5(BigDecimal value) {
        BigDecimal threshold = new BigDecimal("0.5");
        return value.compareTo(threshold) < 0;
    }
}
