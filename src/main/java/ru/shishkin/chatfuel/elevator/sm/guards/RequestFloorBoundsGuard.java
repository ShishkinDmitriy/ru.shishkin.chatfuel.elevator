package ru.shishkin.chatfuel.elevator.sm.guards;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

import ru.shishkin.chatfuel.elevator.sm.ElevatorEvents;
import ru.shishkin.chatfuel.elevator.sm.ElevatorStates;

public final class RequestFloorBoundsGuard implements Guard<ElevatorStates, ElevatorEvents> {

    @Override
    public boolean evaluate(StateContext<ElevatorStates, ElevatorEvents> context) {
        return true;
    }

}