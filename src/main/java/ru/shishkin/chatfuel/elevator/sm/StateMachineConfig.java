package ru.shishkin.chatfuel.elevator.sm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;

import javafx.util.Duration;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.shishkin.chatfuel.elevator.sm.actions.CabinMoveDownAction;
import ru.shishkin.chatfuel.elevator.sm.actions.CabinMoveUpAction;
import ru.shishkin.chatfuel.elevator.sm.actions.DoorCloseAction;
import ru.shishkin.chatfuel.elevator.sm.actions.DoorOpenAction;
import ru.shishkin.chatfuel.elevator.sm.actions.ElevatorInitializeAction;
import ru.shishkin.chatfuel.elevator.sm.actions.ElevatorPopRequestedFloorAction;
import ru.shishkin.chatfuel.elevator.sm.actions.ElevatorPushRequestedFloorAction;
import ru.shishkin.chatfuel.elevator.sm.guards.CabinFloorEqualsGuard;
import ru.shishkin.chatfuel.elevator.sm.guards.CabinFloorHigherGuard;
import ru.shishkin.chatfuel.elevator.sm.guards.CabinFloorLowerGuard;
import ru.shishkin.chatfuel.elevator.sm.guards.ElevatorQueueIsEmptyGuard;
import ru.shishkin.chatfuel.elevator.sm.guards.HouseFloorBoundsGuard;

@Slf4j
@Configuration
@EnableStateMachine
public class StateMachineConfig
        extends EnumStateMachineConfigurerAdapter<ElevatorStates, ElevatorEvents> {

    @Setter(onMethod_ = @Value("${doors.delay:10}"))
    private double doorsDelay;

    @Setter(onMethod_ = @Value("${floor.height:3}"))
    private double floorHeight;

    @Setter(onMethod_ = @Value("${floor.count:10}"))
    private int floorCount;

    @Setter(onMethod_ = @Value("${cabin.velocity:1}"))
    private double cabinVelocity;

    // ====================================================================//
    // State Machine
    // ====================================================================//

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        log.info("Prepare task scheduler...");
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setThreadNamePrefix("elevator");
        taskScheduler.initialize();
        return taskScheduler;
    }

    @Bean
    public TaskExecutor elevatorTaskExecutor() {
        return taskScheduler();
    }

    @Bean
    public TaskScheduler elevatorTaskScheduler() {
        return taskScheduler();
    }

    @Override
    public void configure(
            StateMachineConfigurationConfigurer<ElevatorStates, ElevatorEvents> config)
            throws Exception {
        config.withConfiguration()
                .autoStartup(true)
//                .taskExecutor(elevatorTaskExecutor())
//                .taskScheduler(elevatorTaskScheduler())
                .listener(elevatorLogger());
    }

    @Override
    public void configure(StateMachineStateConfigurer<ElevatorStates, ElevatorEvents> states)
            throws Exception {
        //@formatter:off
        states
            .withStates()
                .initial(ElevatorStates.ELEVATOR_INITIAL, elevatorInitializeAction())
                .state(ElevatorStates.ELEVATOR_INITIAL)
                .state(ElevatorStates.ELEVATOR_IDLE)
                .state(ElevatorStates.ELEVATOR_BUSY)
                .and()
            .withStates()
                .parent(ElevatorStates.ELEVATOR_BUSY)
                .initial(ElevatorStates.CABIN_INITIAL)
                .state(ElevatorStates.CABIN_INITIAL)
                .choice(ElevatorStates.CABIN_MOVE_CHOICE)
                .state(ElevatorStates.CABIN_MOVE_DOWN)
                .state(ElevatorStates.CABIN_MOVE_UP)
                .state(ElevatorStates.CABIN_ARRIVED)
                .end(ElevatorStates.CABIN_IDLE)
                .and()
            .withStates()
                .parent(ElevatorStates.CABIN_ARRIVED)
                .initial(ElevatorStates.DOOR_INITIAL)
                .state(ElevatorStates.DOOR_INITIAL)
                .state(ElevatorStates.DOOR_CLOSED)
                .state(ElevatorStates.DOOR_OPEN)
                .end(ElevatorStates.DOOR_CLOSED);
        //@formatter:on
    }

    @Override
    public void configure(
            StateMachineTransitionConfigurer<ElevatorStates, ElevatorEvents> transitions)
            throws Exception {
        //@formatter:off
        transitions
            // request elevator to floor
            .withExternal()
                .source(ElevatorStates.ELEVATOR_INITIAL)
                .target(ElevatorStates.ELEVATOR_IDLE)
                .and()
            .withExternal()
                .source(ElevatorStates.ELEVATOR_IDLE)
                .target(ElevatorStates.ELEVATOR_BUSY)
                .event(ElevatorEvents.REQUEST_TO_FLOOR)
                .action(elevatorPushRequestedFloorAction())
                .and()
            .withExternal()
                .source(ElevatorStates.ELEVATOR_IDLE)
                .target(ElevatorStates.ELEVATOR_BUSY)
                .event(ElevatorEvents.MOVE_TO_FLOOR)
                .action(elevatorPushRequestedFloorAction())
                .and()
            .withExternal()
                .source(ElevatorStates.ELEVATOR_BUSY)
                .target(ElevatorStates.ELEVATOR_BUSY)
                .event(ElevatorEvents.MOVE_TO_FLOOR)
                .action(elevatorPushRequestedFloorAction())
                .and()
            .withExternal()
                .source(ElevatorStates.ELEVATOR_BUSY)
                .target(ElevatorStates.ELEVATOR_IDLE)
                .guard(elevatorQueueIsEmptyGuard())
                .and()
            // [CABIN_INITIAL -> CABIN_MOVE_CHOICE] triggerless transition
            .withExternal()
                .source(ElevatorStates.CABIN_INITIAL)
                .target(ElevatorStates.CABIN_MOVE_CHOICE)
                .and()
            // [CABIN_MOVE_CHOICE -> CABIN_MOVE_UP || CABIN_MOVE_DOWN || CABIN_ARRIVED || CABIN_IDLE]
            // to choice movement direction
            .withChoice()
                .source(ElevatorStates.CABIN_MOVE_CHOICE)
                .first(ElevatorStates.CABIN_MOVE_UP, cabinFloorLowerGuard())
                .then(ElevatorStates.CABIN_MOVE_DOWN, cabinFloorHigherGuard())
                .then(ElevatorStates.CABIN_ARRIVED, cabinFloorEqualsGuard())
                .last(ElevatorStates.CABIN_IDLE)
                .and()
            // [CABIN_ARRIVED -> CABIN_MOVE_CHOICE] when elevator queue was increased
            .withExternal()
                .source(ElevatorStates.CABIN_ARRIVED)
                .target(ElevatorStates.CABIN_MOVE_CHOICE)
                .event(ElevatorEvents.ELEVATOR_QUEUE_DECREASED)
                .and()
            // [CABIN_MOVE_UP -> CABIN_MOVE_CHOICE] when cabin move by 1 floor up
            .withExternal()
                .source(ElevatorStates.CABIN_MOVE_UP)
                .target(ElevatorStates.CABIN_MOVE_CHOICE)
                .timerOnce(timeToMove(floorHeight, cabinVelocity))
                .action(cabinMoveUpAction())
                .and()
            // [CABIN_MOVE_DOWN -> CABIN_MOVE_CHOICE] when cabin move by 1 floor down
            .withExternal()
                .source(ElevatorStates.CABIN_MOVE_DOWN)
                .target(ElevatorStates.CABIN_MOVE_CHOICE)
                .timerOnce(timeToMove(floorHeight, cabinVelocity))
                .action(cabinMoveDownAction())
                .and()
            // [DOOR_INITIAL -> DOOR_OPEN] when cabin arrived to the floor triggerless transition
            .withExternal()
                .source(ElevatorStates.DOOR_INITIAL)
                .target(ElevatorStates.DOOR_OPEN)
                .action(doorOpenAction())
                .and()
            // [DOOR_OPEN -> DOOR_CLOSED] when doors was opened specified time
            .withExternal()
                .source(ElevatorStates.DOOR_OPEN)
                .target(ElevatorStates.DOOR_CLOSED)
                .timerOnce(secondsToMillis(doorsDelay))
                .action(doorCloseAction())
                .action(elevatorPopRequestedFloorAction());
        //@formatter:on
    }

    @Bean
    public StateMachineListener<ElevatorStates, ElevatorEvents> elevatorLogger() {
        return new ElevatorLogger();
    }

    // ====================================================================//
    // Guards
    // ====================================================================//

//    @Bean
//    public Guard<ElevatorStates, ElevatorEvents> houseFloorBoundsGuard(int count) {
//        return new HouseFloorBoundsGuard(count);
//    }

    @Bean
    public Guard<ElevatorStates, ElevatorEvents> cabinFloorHigherGuard() {
        return new CabinFloorHigherGuard();
    }

    @Bean
    public Guard<ElevatorStates, ElevatorEvents> cabinFloorLowerGuard() {
        return new CabinFloorLowerGuard();
    }

    @Bean
    public Guard<ElevatorStates, ElevatorEvents> cabinFloorEqualsGuard() {
        return new CabinFloorEqualsGuard();
    }

    @Bean
    public Guard<ElevatorStates, ElevatorEvents> elevatorQueueIsEmptyGuard() {
        return new ElevatorQueueIsEmptyGuard();
    }

    // ====================================================================//
    // Actions
    // ====================================================================//

    @Bean
    public Action<ElevatorStates, ElevatorEvents> elevatorInitializeAction() {
        return new ElevatorInitializeAction();
    }

    @Bean
    public Action<ElevatorStates, ElevatorEvents> elevatorPushRequestedFloorAction() {
        return new ElevatorPushRequestedFloorAction();
    }

    @Bean
    public Action<ElevatorStates, ElevatorEvents> elevatorPopRequestedFloorAction() {
        return new ElevatorPopRequestedFloorAction();
    }

    @Bean
    public Action<ElevatorStates, ElevatorEvents> cabinMoveUpAction() {
        return new CabinMoveUpAction();
    }

    @Bean
    public Action<ElevatorStates, ElevatorEvents> cabinMoveDownAction() {
        return new CabinMoveDownAction();
    }

    @Bean
    public Action<ElevatorStates, ElevatorEvents> doorCloseAction() {
        return new DoorCloseAction();
    }

    @Bean
    public Action<ElevatorStates, ElevatorEvents> doorOpenAction() {
        return new DoorOpenAction();
    }

    private static long secondsToMillis(double value) {
        return Math.round(Duration.seconds(value).toMillis());
    }

    private static long timeToMove(double length, double velocity) {
        return Math.round(Duration.seconds(length / velocity).toMillis());
    }

}
