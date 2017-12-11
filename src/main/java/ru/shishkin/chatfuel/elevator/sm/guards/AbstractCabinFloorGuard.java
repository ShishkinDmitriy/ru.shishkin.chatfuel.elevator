package ru.shishkin.chatfuel.elevator.sm.guards;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

import ru.shishkin.chatfuel.elevator.sm.ElevatorEvents;
import ru.shishkin.chatfuel.elevator.sm.ElevatorStates;
import ru.shishkin.chatfuel.elevator.sm.MessageHeaders;

public abstract class AbstractCabinFloorGuard implements Guard<ElevatorStates, ElevatorEvents> {

    @Override
    public final boolean evaluate(StateContext<ElevatorStates, ElevatorEvents> context) {
        Integer cabinFLoor = MessageHeaders.CABIN_FLOOR.getFrom(context);
        Integer requestFLoor = (Integer) MessageHeaders.ELEVATOR_QUEUE.getFrom(context).peek();
        if (requestFLoor != null) {
            return evaluate(cabinFLoor, requestFLoor);
        } else {
            return false;
        }
    }

    protected abstract boolean evaluate(Integer cabinFLoor, Integer requestFLoor);

}