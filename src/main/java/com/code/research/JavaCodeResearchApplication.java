package com.code.research;

import com.code.research.service.messagedecorator.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.code.research")
@Slf4j
public class JavaCodeResearchApplication {

    /**
     * JavaCodeResearch Application.
     * @param args command line arguments.
     */
    public static void main(final String[] args) {
        SpringApplication.run(com.code.research.JavaCodeResearchApplication.class, args);
    }


    // This runner demonstrates the primary decorator chain from our configuration.
    @Bean
    public CommandLineRunner demo(MessageService messageService) {
        return args -> {
            String message = messageService.getMessage();
            log.info("Primary decorated message: {}", message);
        };
    }

}
