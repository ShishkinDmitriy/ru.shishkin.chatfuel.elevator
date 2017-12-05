package ru.shishkin.chatfuel.elevator;

/*
 * @startuml
 *
 * [*] --> ELEVATOR_IDLE
 * ELEVATOR_IDLE --> ELEVATOR_BUSY : request
 *
 * state ELEVATOR_BUSY {
 *   [*]             --> CABIN_MOVE_DOWN : cabin_floor >  request_floor
 *   [*]             --> CABIN_STOPPED   : cabin_floor == request_floor
 *   [*]             --> CABIN_MOVE_UP   : cabin_floor <  request_floor
 *   CABIN_MOVE_DOWN -->  CABIN_MOVE_DOWN : cabin_floor >  request_floor
 *   CABIN_MOVE_UP   -->  CABIN_MOVE_UP   : cabin_floor <  request_floor
 *   CABIN_MOVE_DOWN --> CABIN_STOPPED   : cabin_floor == request_floor
 *   CABIN_MOVE_UP   --> CABIN_STOPPED   : cabin_floor == request_floor
 *   state CABIN_STOPPED {
 *     [*] --> DOOR_CLOSED
 *     DOOR_CLOSED --> DOOR_OPEN
 *     DOOR_OPEN --> DOOR_CLOSED
 *     DOOR_CLOSED -> [*]
 *   }
 * }
 *
 * @enduml
 */

public enum States {

    SI, S1, S2,

    /**
     * Indicate that elevator is waiting for passengers.
     */
    ELEVATOR_IDLE,

    /**
     * Indicate that elevator is handle some passenger's request.
     */
    ELEVATOR_BUSY,

    CABIN_MOVE_CHOICE,

    CABIN_STOPPED,

    CABIN_MOVE_DOWN,

    CABIN_MOVE_UP,

    DOOR_CLOSED,

    DOOR_OPEN;

}
