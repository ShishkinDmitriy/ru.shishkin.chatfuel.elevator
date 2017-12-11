package ru.shishkin.chatfuel.elevator;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @see <a href=
 *      "https://paper.dropbox.com/doc/Backend-Engineer-gwGdGRZyTyM31UVa3b0fE">Chatfuel
 *      test assignment</a>
 */
@SpringBootApplication
public class ElevatorApplication {

    protected ElevatorApplication() {
        super();
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ElevatorApplication.class, args);
    }

}