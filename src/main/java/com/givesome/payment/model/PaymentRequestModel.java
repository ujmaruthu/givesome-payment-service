package com.givesome.payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequestModel {

    private BigDecimal amount;

    private String currency;

    private  String token;

    private String description;

    private String projectId;

    private String supplierId;

    private String postalCode;

    private Integer userId;

    private String givecardCode;

    private BigDecimal youGive;

    private Integer givecardAmount;

    private Integer givacardBalance;

    private Integer givecardId;



}
