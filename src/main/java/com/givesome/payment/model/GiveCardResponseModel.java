package com.givesome.payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GiveCardResponseModel {
    private Integer balance;
    private Integer givecardId;
    private String campaignName;
    private String campaignImage;
    private String campaignMessage;

}
