package ru.shishkin.chatfuel.elevator.action;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import ru.shishkin.chatfuel.elevator.Events;
import ru.shishkin.chatfuel.elevator.States;

public class RequestElevatorOnFloorAction implements Action<States, Events> {

    @Override
    public void execute(StateContext<States, Events> context) {
        // TODO Auto-generated method stub

    }

}
