package ru.shishkin.chatfuel.elevator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        // SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        config.withConfiguration()
                // .taskExecutor(taskExecutor)
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        //@formatter:off
        states
            .withStates()
                .initial(States.ELEVATOR_IDLE)
                .and()
            .withStates()
                .state(States.ELEVATOR_BUSY)
                .and()
                .withStates()
                    .parent(States.ELEVATOR_BUSY)
                    .initial(States.CABIN_MOVE_CHOICE)
                    .choice(States.CABIN_MOVE_CHOICE)
                    .state(States.CABIN_MOVE_DOWN)
                    .state(States.CABIN_MOVE_UP)
                    .state(States.CABIN_STOPPED);
        //@formatter:on
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        //@formatter:off
		transitions
			.withExternal()
				.source(States.ELEVATOR_IDLE)
				.target(States.CABIN_MOVE_CHOICE)
				.event(Events.E1)
				.and()
            .withChoice()
                .source(States.CABIN_MOVE_CHOICE)
                .first(States.CABIN_MOVE_UP, s3Guard())
                .then(States.CABIN_MOVE_DOWN, s3Guard())
                .last(States.CABIN_STOPPED)
                .and()
			.withExternal()
				.source(States.CABIN_MOVE_CHOICE)
				.target(States.CABIN_MOVE_DOWN)
				.event(Events.E2);
		//@formatter:on
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                log.info("State change to {}", to.getId());
            }
        };
    }

    @Bean
    public Guard<States, Events> s3Guard() {
        return new Guard<States, Events>() {

            @Override
            public boolean evaluate(StateContext<States, Events> context) {
                return true;
            }
        };
    }
}