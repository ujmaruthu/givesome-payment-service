package com.givesome.payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ChargeRequestModel {

    private BigDecimal amount;

    private String currency;

    private  String token;

    private String description;

}
