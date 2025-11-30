package com.code.research.springboot.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Profile("prod")
class RealPaymentClient implements PaymentClient {
    public void pay(BigDecimal amount) {
        // call real external API
    }
}