package com.code.research.sealed;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class Cat implements Animal {
    @Transactional(propagation = Propagation.REQUIRED)
    public void doDraw() {}
}
