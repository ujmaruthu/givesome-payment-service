package com.givesome.payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PaymentIntentModel {

    private Long amount;

    private String currency;

    private String customer;

    private String description;
    
    private String paymentMethod;


}
