package com.reconnect.web.peopleflow.machine;

import com.reconnect.web.peopleflow.AppTest;
import com.reconnect.web.peopleflow.enums.EmployeeEvent;
import com.reconnect.web.peopleflow.enums.EmployeeState;
import com.reconnect.web.peopleflow.exceptions.MissingStateException;
import org.junit.jupiter.api.Test;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.support.DefaultExtendedState;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
@AppTest
public class EmployeePersisterTest {

    private final EmployeePersister persister = new EmployeePersister();

    @Test
    public void testNormalReadTest() {
        final String userId = "Donald";
        final EmployeeState state = EmployeeState.ADDED;
        final EmployeeEvent event = EmployeeEvent.EMPLOYEE_ADD;
        persister.write(createContext(state, event), userId);

        final StateMachineContext<EmployeeState, EmployeeEvent> context = persister.read(userId);

        assertThat(context.getState()).isEqualTo(state);
        assertThat(context.getEvent()).isEqualTo(event);
    }

    @Test
    public void testIncorrectNameReadTest() {
        final String readUserId = "Bob";
        final String existingUserId = "Alisha";
        final EmployeeState state = EmployeeState.ADDED;
        final EmployeeEvent event = EmployeeEvent.EMPLOYEE_ADD;
        persister.write(createContext(state, event), existingUserId);

        assertThrows(MissingStateException.class, () -> persister.read(readUserId));
    }


    @Test
    public void testEmptyContextReadTest() {
        final String userId = "Jonathan";

        assertThrows(MissingStateException.class, () -> persister.read(userId));
    }

    private StateMachineContext<EmployeeState, EmployeeEvent> createContext(final EmployeeState state, final EmployeeEvent event) {
        final DefaultExtendedState ext = new DefaultExtendedState(new HashMap<>());
        return new DefaultStateMachineContext<>(state, event, new HashMap<>(), ext);
    }
}
