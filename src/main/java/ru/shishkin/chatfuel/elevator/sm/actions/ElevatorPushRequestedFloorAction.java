package ru.shishkin.chatfuel.elevator.sm.actions;

import java.util.Queue;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import lombok.extern.slf4j.Slf4j;
import ru.shishkin.chatfuel.elevator.sm.ElevatorEvents;
import ru.shishkin.chatfuel.elevator.sm.ElevatorStates;
import ru.shishkin.chatfuel.elevator.sm.MessageHeaders;

@Slf4j
public class ElevatorPushRequestedFloorAction implements Action<ElevatorStates, ElevatorEvents> {

    @Override
    @SuppressWarnings("unchecked")
    public void execute(StateContext<ElevatorStates, ElevatorEvents> context) {
        Integer requested = (Integer) context
                .getMessageHeader(MessageHeaders.REQUEST_FLOOR.getName());
        Queue<Object> queue = MessageHeaders.ELEVATOR_QUEUE.getFrom(context);
        queue.offer(requested);
        MessageHeaders.ELEVATOR_QUEUE.setTo(context, queue);
        log.info("ELEVATOR_QUEUE = {}", queue);
    }

}
