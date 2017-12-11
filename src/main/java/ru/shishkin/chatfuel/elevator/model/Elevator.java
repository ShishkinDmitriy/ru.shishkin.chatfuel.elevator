package ru.shishkin.chatfuel.elevator.model;

public interface Elevator {

    void requestToFloor(int floor);

    void moveToFloor(int floor);

    int getMinFloor();

    int getMaxFloor();

}
