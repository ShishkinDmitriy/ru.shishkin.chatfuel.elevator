package ru.shishkin.chatfuel.elevator.sm.guards;

public final class CabinFloorEqualsGuard extends AbstractCabinFloorGuard {

    @Override
    protected boolean evaluate(Integer cabinFLoor, Integer requestFLoor) {
        return cabinFLoor == requestFLoor;
    }

}