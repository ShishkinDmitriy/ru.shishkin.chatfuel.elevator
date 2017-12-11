package ru.shishkin.chatfuel.elevator.sm;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
final class ElevatorLogger implements StateMachineListener<ElevatorStates, ElevatorEvents> {

    @Override
    public void stateChanged(State<ElevatorStates, ElevatorEvents> from,
            State<ElevatorStates, ElevatorEvents> to) {
        log.debug("{} -> {}", getStateLog(from), getStateLog(to));
    }

    @Override
    public void stateEntered(State<ElevatorStates, ElevatorEvents> state) {
        log.debug("stateEntered {}", state.getId());
    }

    @Override
    public void stateExited(State<ElevatorStates, ElevatorEvents> state) {
        log.debug("stateExited {}", state.getId());
    }

    @Override
    public void eventNotAccepted(Message<ElevatorEvents> event) {
        log.info("eventNotAccepted {}", event);
    }

    @Override
    public void transitionStarted(Transition<ElevatorStates, ElevatorEvents> transition) {
        log.debug("transitionStarted {}", getTransitionLog(transition));
    }

    @Override
    public void transition(Transition<ElevatorStates, ElevatorEvents> transition) {
        log.debug("transition {}", getTransitionLog(transition));
    }

    @Override
    public void transitionEnded(Transition<ElevatorStates, ElevatorEvents> transition) {
        log.debug("transitionEnded {}", getTransitionLog(transition));
    }

    @Override
    public void stateMachineStarted(StateMachine<ElevatorStates, ElevatorEvents> stateMachine) {
        log.debug("stateMachineStarted {}", stateMachine);
    }

    @Override
    public void stateMachineStopped(StateMachine<ElevatorStates, ElevatorEvents> stateMachine) {
        log.debug("stateMachineStopped {}", stateMachine);
    }

    @Override
    public void stateMachineError(StateMachine<ElevatorStates, ElevatorEvents> stateMachine,
            Exception exception) {
        log.error("stateMachineError {}, {}", stateMachine, exception);
    }

    @Override
    public void extendedStateChanged(Object key, Object value) {
        log.info("{} = {}", key, value);
    }

    @Override
    public void stateContext(StateContext<ElevatorStates, ElevatorEvents> stateContext) {
        log.debug("stateContext {}", stateContext);
    }

    private String getStateLog(State<?, ?> state) {
        if (state == null) {
            return "null";
        } else {
            return state.getId().toString();
        }
    }

    private String getTransitionLog(Transition<?, ?> transition) {
        return "[" + getStateLog(transition.getSource()) + " -> "
                + getStateLog(transition.getTarget()) + "]";
    }

}