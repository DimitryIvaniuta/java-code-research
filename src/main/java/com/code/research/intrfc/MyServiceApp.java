package com.code.research.intrfc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyServiceApp {

    public static void main(String[] args) {
        MyService ms = new MyServiceImpl();
        ms.perform("Hello World");
        ms.log("Log Hello World");
        log.info("Calculate: {}", MyService.calculate(1, 2));
    }

}
