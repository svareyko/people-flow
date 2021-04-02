package com.reconnect.web.peopleflow.machine;

import com.reconnect.web.peopleflow.AppTest;
import com.reconnect.web.peopleflow.enums.EmployeeEvent;
import com.reconnect.web.peopleflow.enums.EmployeeState;
import com.reconnect.web.peopleflow.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.statemachine.support.DefaultExtendedState;
import org.springframework.statemachine.support.DefaultStateContext;

import java.util.HashMap;

import static com.reconnect.web.peopleflow.enums.Attrs.ATTR_STATE;
import static com.reconnect.web.peopleflow.enums.Attrs.ATTR_USERNAME;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
@AppTest
public class EmployeeActivateActionTest {

    @MockBean
    private EmployeeService employeeService;
    @Mock
    private DefaultStateContext<EmployeeState, EmployeeEvent> context;

    private EmployeeActivateAction employeeActivateAction;

    @BeforeEach
    public void before() {
        employeeActivateAction = new EmployeeActivateAction(employeeService);
    }

    @Test
    public void activateActionTest() {
        final String username = "Mark";
        final EmployeeState state = EmployeeState.ACTIVE;
        mockAttributes(username, state);

        employeeActivateAction.execute(context);

        verify(employeeService).update(eq(username), eq(state));
    }

    @Test
    public void inCheckActionTest() {
        final String username = "Kate";
        final EmployeeState state = EmployeeState.IN_CHECK;
        mockAttributes(username, state);

        employeeActivateAction.execute(context);

        verify(employeeService).update(eq(username), eq(state));
    }

    @Test
    public void approveActionTest() {
        final String username = "Kate";
        final EmployeeState state = EmployeeState.APPROVED;
        mockAttributes(username, state);

        employeeActivateAction.execute(context);

        verify(employeeService).update(eq(username), eq(state));
    }

    private void mockAttributes(final String username, final EmployeeState state) {
        final HashMap<Object, Object> variables = new HashMap<>();
        variables.put(ATTR_USERNAME, username);
        variables.put(ATTR_STATE, state);
        when(context.getExtendedState()).thenReturn(new DefaultExtendedState(variables));
    }
}
