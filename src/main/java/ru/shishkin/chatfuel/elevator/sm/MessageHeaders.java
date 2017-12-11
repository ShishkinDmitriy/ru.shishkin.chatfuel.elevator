package ru.shishkin.chatfuel.elevator.sm;

import java.util.Queue;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageHeaders {

    public static final MessageHeader<Queue> ELEVATOR_QUEUE = new MessageHeader<>("ELEVATOR_QUEUE",
            Queue.class);

    public static final MessageHeader<Integer> CABIN_FLOOR = new MessageHeader<>("CABIN_FLOOR",
            Integer.class);

    public static final MessageHeader<Integer> REQUEST_FLOOR = new MessageHeader<>("REQUEST_FLOOR",
            Integer.class);

    public static final MessageHeader<Boolean> DOOR_CLOSED = new MessageHeader<>("DOOR_CLOSED",
            Boolean.class);

    public static final MessageHeader<Integer> FLOOR_COUNT = new MessageHeader<>("FLOOR_COUNT",
            Integer.class);

}
