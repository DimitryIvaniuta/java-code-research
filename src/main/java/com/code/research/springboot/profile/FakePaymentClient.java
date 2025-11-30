package com.code.research.springboot.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Profile("dev")
class FakePaymentClient implements PaymentClient {
    public void pay(BigDecimal amount) {
        System.out.println("DEV payment: " + amount);
    }
}