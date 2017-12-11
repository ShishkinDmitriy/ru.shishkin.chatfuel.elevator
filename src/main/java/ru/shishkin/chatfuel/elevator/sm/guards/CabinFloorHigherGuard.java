package ru.shishkin.chatfuel.elevator.sm.guards;

public final class CabinFloorHigherGuard extends AbstractCabinFloorGuard {

    @Override
    protected boolean evaluate(Integer cabinFLoor, Integer requestFLoor) {
        return cabinFLoor > requestFLoor;
    }

}