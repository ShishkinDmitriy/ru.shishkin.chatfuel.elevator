package ru.shishkin.chatfuel.elevator.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
public class ShellConfig {

    private static final String PROMPT = "elevator> ";

    @Bean
    public PromptProvider promptProvider() {
        return () -> new AttributedString(PROMPT,
                AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }

}
