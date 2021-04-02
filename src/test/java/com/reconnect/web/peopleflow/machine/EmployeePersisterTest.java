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
        final String username = "Donald";
        final EmployeeState state = EmployeeState.ADDED;
        final EmployeeEvent event = EmployeeEvent.EMPLOYEE_ADD;
        persister.write(createContext(state, event), username);

        final StateMachineContext<EmployeeState, EmployeeEvent> context = persister.read(username);

        assertThat(context.getState()).isEqualTo(state);
        assertThat(context.getEvent()).isEqualTo(event);
    }

    @Test
    public void testIncorrectNameReadTest() {
        final String readUsername = "Bob";
        final String existingUsername = "Alisha";
        final EmployeeState state = EmployeeState.ADDED;
        final EmployeeEvent event = EmployeeEvent.EMPLOYEE_ADD;
        persister.write(createContext(state, event), existingUsername);

        assertThrows(MissingStateException.class, () -> persister.read(readUsername));
    }


    @Test
    public void testEmptyContextReadTest() {
        final String username = "Jonathan";

        assertThrows(MissingStateException.class, () -> persister.read(username));
    }

    private StateMachineContext<EmployeeState, EmployeeEvent> createContext(final EmployeeState state, final EmployeeEvent event) {
        final DefaultExtendedState ext = new DefaultExtendedState(new HashMap<>());
        return new DefaultStateMachineContext<>(state, event, new HashMap<>(), ext);
    }
}
