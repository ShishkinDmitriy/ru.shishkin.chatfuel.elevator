package ru.shishkin.chatfuel.elevator.sm;

/*
 * @startuml
 *
 * [*]              --> ELEVATOR_INITIAL
 * ELEVATOR_INITIAL --> ELEVATOR_IDLE
 * ELEVATOR_IDLE    --> ELEVATOR_BUSY : REQUEST_TO_FLOOR
 * ELEVATOR_IDLE    --> ELEVATOR_BUSY : MOVE_TO_FLOOR
 * ELEVATOR_BUSY    --> ELEVATOR_BUSY : MOVE_TO_FLOOR
 *
 * state ELEVATOR_BUSY {
 *   [*]               --> CABIN_INITIAL
 *   CABIN_INITIAL     --> CABIN_MOVE_CHOICE
 *   CABIN_MOVE_CHOICE --> CABIN_MOVE_DOWN   : [cabin_floor > request_floor]
 *   CABIN_MOVE_CHOICE --> CABIN_MOVE_UP     : [cabin_floor < request_floor]
 *   CABIN_MOVE_CHOICE --> CABIN_ARRIVED     : [cabin_floor == request_floor]
 *   CABIN_MOVE_CHOICE ->  CABIN_IDLE
 *   CABIN_MOVE_DOWN   -u-> CABIN_MOVE_CHOICE : [timeout]
 *   CABIN_MOVE_UP     -u-> CABIN_MOVE_CHOICE : [timeout]
 *   CABIN_ARRIVED     -u-> CABIN_MOVE_CHOICE : [queue_decreased]
 *   CABIN_IDLE        ->  [*]
 *   state CABIN_ARRIVED {
 *     [*]          --> DOOR_INITIAL
 *     DOOR_INITIAL --> DOOR_OPEN
 *     DOOR_OPEN    --> DOOR_CLOSED : [timeout]
 *     DOOR_CLOSED  --> [*]
 *   }
 * }
 *
 * @enduml
 */

public enum ElevatorStates {

    ELEVATOR_INITIAL,

    /**
     * Indicate that elevator is waiting for passengers.
     */
    ELEVATOR_IDLE,

    /**
     * Indicate that elevator is handle some passenger's request.
     */
    ELEVATOR_BUSY,

    CABIN_INITIAL,

    CABIN_MOVE_CHOICE,

    CABIN_ARRIVED,

    CABIN_MOVE_DOWN,

    CABIN_MOVE_UP,

    CABIN_IDLE,

    DOOR_INITIAL,

    DOOR_CLOSED,

    DOOR_OPEN;

}
