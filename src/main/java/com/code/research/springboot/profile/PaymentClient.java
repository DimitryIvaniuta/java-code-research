package com.code.research.springboot.profile;

import java.math.BigDecimal;

public interface PaymentClient {
    void pay(BigDecimal amount);
}
