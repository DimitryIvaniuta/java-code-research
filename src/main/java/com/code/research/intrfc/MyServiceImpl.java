package com.code.research.intrfc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyServiceImpl implements MyService, MyServiceAdvanced {

    @Override
    public void perform(String input) {
        log.info("Implemented perform: {}", input);
    }

    public void performAbstract(String input) {
        log.info("Advanced Implemented perform: {}", input);
    }

    @Override
    public void log(String input) {
        MyServiceAdvanced.super.log(input);
        MyService.super.log(input);
//        log.info("Implemented log: {}", input);
    }
}
