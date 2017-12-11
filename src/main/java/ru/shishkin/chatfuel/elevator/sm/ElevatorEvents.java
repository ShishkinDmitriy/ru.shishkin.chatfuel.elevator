package ru.shishkin.chatfuel.elevator.sm;

public enum ElevatorEvents {

    REQUEST_TO_FLOOR,

    /**
     * Event indicated that passenger pushed the button inside the elevator.
     */
    MOVE_TO_FLOOR,

    ELEVATOR_QUEUE_DECREASED;

}
