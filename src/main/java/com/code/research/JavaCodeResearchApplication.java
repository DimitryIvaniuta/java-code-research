package com.code.research;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.code.research")
public class JavaCodeResearchApplication {

    /**
     * JavaCodeResearch Application.
     * @param args command line arguments.
     */
    public static void main(final String[] args) {
        SpringApplication.run(com.code.research.JavaCodeResearchApplication.class, args);
    }

}
