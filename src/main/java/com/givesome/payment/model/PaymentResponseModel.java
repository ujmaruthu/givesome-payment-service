package com.givesome.payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class PaymentResponseModel {

    private String status;
    private String paymentId;
    private Long amount;
    private Instant createdTime;
    private String exclusiveContentUrl;
    private String moreProjectUrl;
}
