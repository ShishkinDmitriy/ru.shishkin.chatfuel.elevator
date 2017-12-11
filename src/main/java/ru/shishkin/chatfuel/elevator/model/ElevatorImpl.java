package ru.shishkin.chatfuel.elevator.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.shishkin.chatfuel.elevator.sm.ElevatorEvents;
import ru.shishkin.chatfuel.elevator.sm.ElevatorStates;
import ru.shishkin.chatfuel.elevator.sm.MessageHeaders;

@Slf4j
public class ElevatorImpl implements Elevator {

    @Autowired
    private StateMachine<ElevatorStates, ElevatorEvents> machine;

    @Setter(onMethod_ = @Value("${floor.count:10}"))
    private int floorCount;

    @Override
    public void requestToFloor(int floor) {
        if (checkFloor(floor)) {
            Message<ElevatorEvents> message = MessageBuilder
                    .withPayload(ElevatorEvents.REQUEST_TO_FLOOR)
                    .setHeader(MessageHeaders.REQUEST_FLOOR.getName(), floor)
                    .build();
            machine.sendEvent(message);
        }
    }

    @Override
    public void moveToFloor(int floor) {
        if (checkFloor(floor)) {
            Message<ElevatorEvents> message = MessageBuilder
                    .withPayload(ElevatorEvents.MOVE_TO_FLOOR)
                    .setHeader(MessageHeaders.REQUEST_FLOOR.getName(), floor)
                    .build();
            machine.sendEvent(message);
        }
    }

    public boolean checkFloor(int floor) {
        if (floor < getMinFloor() || floor > getMaxFloor()) {
            log.warn("Floor should be between {} and {}, but was {}",
                    getMinFloor(),
                    getMaxFloor(),
                    floor);
            return false;
        }
        return true;

    }

    @Override
    public int getMinFloor() {
        return 1;
    }

    @Override
    public int getMaxFloor() {
        return floorCount;
    }

}
