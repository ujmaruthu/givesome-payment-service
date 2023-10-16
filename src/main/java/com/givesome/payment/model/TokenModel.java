package com.givesome.payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenModel {

    private String cardNumber;

    private String expMonth;

    private String expYear;

    private String cvc;


}
