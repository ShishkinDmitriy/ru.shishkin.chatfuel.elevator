package ru.shishkin.chatfuel.elevator.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import ru.shishkin.chatfuel.elevator.model.Elevator;

@ShellComponent
public class ElevatorCommands {

    @Autowired
    private Elevator elevator;

    private static final String GROUP = "Elevator Commands";

    @ShellMethod(value = "Request elevator from outside to specified floor", group = GROUP)
    public void floor(@ShellOption(help = "The floor where elevator was requested") int floor) {
        elevator.requestToFloor(floor);
    }

    @ShellMethod(value = "Call to move elevator from inside to specified floor", group = GROUP)
    public void call(@ShellOption(help = "The floor where elevator was requested") int floor) {
        elevator.moveToFloor(floor);
    }

}
