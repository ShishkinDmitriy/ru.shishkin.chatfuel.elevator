package ru.shishkin.chatfuel.elevator.sm.actions;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import ru.shishkin.chatfuel.elevator.sm.ElevatorEvents;
import ru.shishkin.chatfuel.elevator.sm.ElevatorStates;
import ru.shishkin.chatfuel.elevator.sm.MessageHeaders;

public class ElevatorInitializeAction implements Action<ElevatorStates, ElevatorEvents> {

    @Override
    public void execute(StateContext<ElevatorStates, ElevatorEvents> context) {
        MessageHeaders.CABIN_FLOOR.setTo(context, 1);
        MessageHeaders.ELEVATOR_QUEUE.setTo(context, new ConcurrentLinkedQueue<>());
        MessageHeaders.DOOR_CLOSED.setTo(context, true);
    }

}
