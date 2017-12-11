package ru.shishkin.chatfuel.elevator.sm.actions;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import ru.shishkin.chatfuel.elevator.sm.ElevatorEvents;
import ru.shishkin.chatfuel.elevator.sm.ElevatorStates;
import ru.shishkin.chatfuel.elevator.sm.ElevatorVariables;
import ru.shishkin.chatfuel.elevator.sm.MessageHeaders;

public class DoorCloseAction implements Action<ElevatorStates, ElevatorEvents> {

    @Override
    public void execute(StateContext<ElevatorStates, ElevatorEvents> context) {
        MessageHeaders.DOOR_CLOSED.setTo(context, ElevatorVariables.DOOR_CLOSED);
    }

}
