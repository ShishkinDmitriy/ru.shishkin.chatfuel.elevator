package ru.shishkin.chatfuel.elevator.sm.guards;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

import lombok.AllArgsConstructor;
import ru.shishkin.chatfuel.elevator.sm.ElevatorEvents;
import ru.shishkin.chatfuel.elevator.sm.ElevatorStates;
import ru.shishkin.chatfuel.elevator.sm.MessageHeaders;

@AllArgsConstructor
public class HouseFloorBoundsGuard implements Guard<ElevatorStates, ElevatorEvents> {

    private final int floorCount;

    @Override
    public boolean evaluate(StateContext<ElevatorStates, ElevatorEvents> context) {
        Integer requested = (Integer) context
                .getMessageHeader(MessageHeaders.REQUEST_FLOOR.getName());
        return requested > 0 && requested < floorCount;
    }

}
