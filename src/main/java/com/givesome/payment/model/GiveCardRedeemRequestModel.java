package com.givesome.payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GiveCardRedeemRequestModel {

    private Integer givecardId;

    private Integer userId;

    private Integer givacardBalance;
}
