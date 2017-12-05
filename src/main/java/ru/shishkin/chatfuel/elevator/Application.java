package ru.shishkin.chatfuel.elevator;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {

    @Autowired
    private StateMachine<States, Events> stateMachine;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                log.info("Please input a line");
                long then = System.currentTimeMillis();
                String line = scanner.nextLine();
                long now = System.currentTimeMillis();
                log.debug("Waited {} s for user input", (now - then) / 1000d);
                log.debug("User input was: {}", line);
                Events event = Events.valueOf(line);
                stateMachine.sendEvent(event);
            }
        } catch (Exception e) {
            log.error("System.in was closed; exiting", e);
        }
    }

}