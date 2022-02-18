package ru.vluzhnykh.quickstart.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class SubscribersConfig {

    @Bean
    public Consumer<String> msgLogger() {
        return log::info;
    }
}
