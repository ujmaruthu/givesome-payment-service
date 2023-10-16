package com.givesome.payment.model;

import com.stripe.model.Charge;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
    @Setter
    @NoArgsConstructor
    public class ResponseModel {
        private String message;
        private int status;
        private ChargeResponseModel data;
    }

