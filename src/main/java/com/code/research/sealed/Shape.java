package com.code.research.sealed;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.MANDATORY)
public sealed interface Shape permits Circle, Triangle {
    void doDraw();
}
