package com.reconnect.web.peopleflow.web;

import com.reconnect.web.peopleflow.AppTest;
import com.reconnect.web.peopleflow.dto.EmployeeDto;
import com.reconnect.web.peopleflow.enums.EmployeeEvent;
import com.reconnect.web.peopleflow.enums.EmployeeState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;

import static com.reconnect.web.peopleflow.enums.Attrs.ATTR_STATE;
import static com.reconnect.web.peopleflow.enums.Attrs.ATTR_USERNAME;
import static com.reconnect.web.peopleflow.utils.EmployeeUtils.createEmployeeDto;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
@AppTest
public class EmployeeAdvisorTest {

    @Autowired
    private StateMachinePersister<EmployeeState, EmployeeEvent, String> persister;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private StateMachineFactory<EmployeeState, EmployeeEvent> stateMachineFactory;
    @Autowired
    private EmployeeController controller;

    @Test
    public void employeeCreateTest() throws Exception {
        final String username = "Alex";
        final EmployeeDto dto = createEmployeeDto(username, false);

        final EmployeeDto employee = controller.createEmployee(dto);

        final StateMachine<EmployeeState, EmployeeEvent> machine = stateMachineFactory.getStateMachine(employee.getUsername());
        final StateMachine<EmployeeState, EmployeeEvent> state = persister.restore(machine, employee.getUsername());
        final String persistedUsername = state.getExtendedState().get(ATTR_USERNAME, String.class);
        final EmployeeState persistedState = state.getExtendedState().get(ATTR_STATE, EmployeeState.class);

        assertThat(persistedUsername).isEqualTo(employee.getUsername());
        assertThat(persistedState).isNotNull();
        assertThat(persistedState).isEqualTo(employee.getState());
    }

    @Test
    public void employeeActivateTest() throws Exception {
        final String username = "Dorian";
        prepareState(username);

        final StateMachine<EmployeeState, EmployeeEvent> machine = stateMachineFactory.getStateMachine(username);
        final StateMachine<EmployeeState, EmployeeEvent> prevMachine = persister.restore(machine, username);
        assertThat(prevMachine.getExtendedState().get(ATTR_STATE, EmployeeState.class)).isEqualTo(EmployeeState.ADDED);

        controller.activateEmployee(username);

        final StateMachine<EmployeeState, EmployeeEvent> resultMachine = persister.restore(machine, username);
        final String resultUsername = resultMachine.getExtendedState().get(ATTR_USERNAME, String.class);
        final EmployeeState resultState = resultMachine.getExtendedState().get(ATTR_STATE, EmployeeState.class);
        assertThat(resultUsername).isEqualTo(username);
        assertThat(resultState).isEqualTo(EmployeeState.ACTIVE);
    }

    @Test
    public void employeeInCheckTest() throws Exception {
        final String username = "Helena";
        prepareState(username);

        final StateMachine<EmployeeState, EmployeeEvent> machine = stateMachineFactory.getStateMachine(username);
        final StateMachine<EmployeeState, EmployeeEvent> prevMachine = persister.restore(machine, username);
        assertThat(prevMachine.getExtendedState().get(ATTR_STATE, EmployeeState.class)).isEqualTo(EmployeeState.ADDED);

        controller.inCheckEmployee(username);

        final StateMachine<EmployeeState, EmployeeEvent> resultMachine = persister.restore(machine, username);
        final String resultUsername = resultMachine.getExtendedState().get(ATTR_USERNAME, String.class);
        final EmployeeState resultState = resultMachine.getExtendedState().get(ATTR_STATE, EmployeeState.class);
        assertThat(resultUsername).isEqualTo(username);
        assertThat(resultState).isEqualTo(EmployeeState.IN_CHECK);
    }

    @Test
    public void employeeApproveTest() throws Exception {
        final String username = "Hanna";
        prepareState(username);

        final StateMachine<EmployeeState, EmployeeEvent> machine = stateMachineFactory.getStateMachine(username);
        final StateMachine<EmployeeState, EmployeeEvent> prevMachine = persister.restore(machine, username);
        assertThat(prevMachine.getExtendedState().get(ATTR_STATE, EmployeeState.class)).isEqualTo(EmployeeState.ADDED);

        controller.approveEmployee(username);

        final StateMachine<EmployeeState, EmployeeEvent> resultMachine = persister.restore(machine, username);
        final String resultUsername = resultMachine.getExtendedState().get(ATTR_USERNAME, String.class);
        final EmployeeState resultState = resultMachine.getExtendedState().get(ATTR_STATE, EmployeeState.class);
        assertThat(resultUsername).isEqualTo(username);
        assertThat(resultState).isEqualTo(EmployeeState.APPROVED);
    }

    private void prepareState(final String username) {
        final EmployeeDto dto = createEmployeeDto(username, false);
        controller.createEmployee(dto);
    }
}
