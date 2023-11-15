package com.givesome.payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GiveCardRequestModel {

    private String code;

    private Integer userId;

    private Integer supplierId;

}
