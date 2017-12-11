package ru.shishkin.chatfuel.elevator.sm.actions;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import ru.shishkin.chatfuel.elevator.sm.ElevatorEvents;
import ru.shishkin.chatfuel.elevator.sm.ElevatorStates;
import ru.shishkin.chatfuel.elevator.sm.MessageHeaders;

public class CabinMoveDownAction implements Action<ElevatorStates, ElevatorEvents> {

    @Override
    public void execute(StateContext<ElevatorStates, ElevatorEvents> context) {
        Integer cabinFloor = MessageHeaders.CABIN_FLOOR.getFrom(context);
        MessageHeaders.CABIN_FLOOR.setTo(context, --cabinFloor);
    }

}
