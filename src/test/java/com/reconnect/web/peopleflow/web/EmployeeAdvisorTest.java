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
import static com.reconnect.web.peopleflow.enums.Attrs.ATTR_USER_ID;
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
        final EmployeeDto dto = createEmployeeDto(false);

        final EmployeeDto employee = controller.createEmployee(dto);

        final String employeeId = String.valueOf(employee.getId());
        final StateMachine<EmployeeState, EmployeeEvent> machine = stateMachineFactory.getStateMachine(employeeId);
        final StateMachine<EmployeeState, EmployeeEvent> state = persister.restore(machine, employeeId);
        final Long persistedUserId = state.getExtendedState().get(ATTR_USER_ID, Long.class);
        final EmployeeState persistedState = state.getExtendedState().get(ATTR_STATE, EmployeeState.class);

        assertThat(String.valueOf(persistedUserId)).isEqualTo(employeeId);
        assertThat(persistedState).isNotNull();
        assertThat(persistedState).isEqualTo(employee.getState());
    }

    @Test
    public void employeeActivateTest() throws Exception {
        final EmployeeDto existing = prepareState("Nick");
        final Long userId = existing.getId();
        final String userIdStr = String.valueOf(userId);

        final StateMachine<EmployeeState, EmployeeEvent> machine = stateMachineFactory.getStateMachine(userIdStr);
        final StateMachine<EmployeeState, EmployeeEvent> prevMachine = persister.restore(machine, userIdStr);
        assertThat(prevMachine.getExtendedState().get(ATTR_STATE, EmployeeState.class)).isEqualTo(EmployeeState.ADDED);

        controller.activateEmployee(userId);

        final StateMachine<EmployeeState, EmployeeEvent> resultMachine = persister.restore(machine, userIdStr);
        final Long resultUserId = resultMachine.getExtendedState().get(ATTR_USER_ID, Long.class);
        final EmployeeState resultState = resultMachine.getExtendedState().get(ATTR_STATE, EmployeeState.class);
        assertThat(String.valueOf(resultUserId)).isEqualTo(userIdStr);
        assertThat(resultState).isEqualTo(EmployeeState.ACTIVE);
    }

    @Test
    public void employeeInCheckTest() throws Exception {
        final EmployeeDto existing = prepareState("Adolf");
        final Long userId = existing.getId();
        final String userIdStr = String.valueOf(userId);

        final StateMachine<EmployeeState, EmployeeEvent> machine = stateMachineFactory.getStateMachine(userIdStr);
        final StateMachine<EmployeeState, EmployeeEvent> prevMachine = persister.restore(machine, userIdStr);
        assertThat(prevMachine.getExtendedState().get(ATTR_STATE, EmployeeState.class)).isEqualTo(EmployeeState.ADDED);

        controller.inCheckEmployee(userId);

        final StateMachine<EmployeeState, EmployeeEvent> resultMachine = persister.restore(machine, userIdStr);
        final Long resultUserId = resultMachine.getExtendedState().get(ATTR_USER_ID, Long.class);
        final EmployeeState resultState = resultMachine.getExtendedState().get(ATTR_STATE, EmployeeState.class);
        assertThat(String.valueOf(resultUserId)).isEqualTo(userIdStr);
        assertThat(resultState).isEqualTo(EmployeeState.IN_CHECK);
    }

    @Test
    public void employeeApproveTest() throws Exception {
        final EmployeeDto existing = prepareState("Han");
        final Long userId = existing.getId();
        final String userIdStr = String.valueOf(userId);

        final StateMachine<EmployeeState, EmployeeEvent> machine = stateMachineFactory.getStateMachine(userIdStr);
        final StateMachine<EmployeeState, EmployeeEvent> prevMachine = persister.restore(machine, userIdStr);
        assertThat(prevMachine.getExtendedState().get(ATTR_STATE, EmployeeState.class)).isEqualTo(EmployeeState.ADDED);

        controller.approveEmployee(userId);

        final StateMachine<EmployeeState, EmployeeEvent> resultMachine = persister.restore(machine, userIdStr);
        final Long resultUserId = resultMachine.getExtendedState().get(ATTR_USER_ID, Long.class);
        final EmployeeState resultState = resultMachine.getExtendedState().get(ATTR_STATE, EmployeeState.class);
        assertThat(String.valueOf(resultUserId)).isEqualTo(userIdStr);
        assertThat(resultState).isEqualTo(EmployeeState.APPROVED);
    }

    private EmployeeDto prepareState(final String username) {
        final EmployeeDto dto = createEmployeeDto(username, false);
        return controller.createEmployee(dto);
    }
}
