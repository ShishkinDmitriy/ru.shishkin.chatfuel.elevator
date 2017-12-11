package ru.shishkin.chatfuel.elevator.sm;

import org.springframework.statemachine.StateContext;

import lombok.Value;

@Value
public final class MessageHeader<T> {

    private final String name;

    private final Class<T> type;

    public T getFrom(StateContext<ElevatorStates, ElevatorEvents> context) {
        return context.getExtendedState().get(name, type);
    }

    public void setTo(StateContext<ElevatorStates, ElevatorEvents> context, T value) {
        context.getExtendedState().getVariables().put(name, value);
    }

}
