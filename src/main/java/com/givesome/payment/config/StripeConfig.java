package com.givesome.payment.config;


import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class StripeConfig {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Bean
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }
}

