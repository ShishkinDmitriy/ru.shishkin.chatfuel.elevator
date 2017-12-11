package ru.shishkin.chatfuel.elevator.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElevatorConfig {

    @Bean
    public Elevator elevator() {
        return new ElevatorImpl();
    }

}
