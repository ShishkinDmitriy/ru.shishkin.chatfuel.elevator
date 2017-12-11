package ru.shishkin.chatfuel.elevator;

import org.junit.Test;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;

import ru.shishkin.chatfuel.elevator.sm.ElevatorEvents;
import ru.shishkin.chatfuel.elevator.sm.ElevatorStates;

public class ElevatorTests {

    @Test
    public void test() {
//        StateMachine<ElevatorStates, ElevatorEvents> machine = buildMachine();
//        StateMachineTestPlan<ElevatorStates, ElevatorEvents> plan =
//                StateMachineTestPlanBuilder.<ElevatorStates, ElevatorEvents>builder()
//                    .defaultAwaitTime(2)
//                    .stateMachine(machine)
//                    .step()
//                        .expectStates("SI")
//                        .and()
//                    .step()
//                        .sendEvent("E1")
//                        .expectStateChanged(1)
//                        .expectStates("S1")
//                        .and()
//                    .build();
//        plan.test();
    }

}
