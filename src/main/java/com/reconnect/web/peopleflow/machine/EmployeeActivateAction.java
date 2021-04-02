package com.reconnect.web.peopleflow.machine;

import com.reconnect.web.peopleflow.enums.Attrs;
import com.reconnect.web.peopleflow.enums.EmployeeEvent;
import com.reconnect.web.peopleflow.enums.EmployeeState;
import com.reconnect.web.peopleflow.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 * Action that will be executed on the transition to any of {@link EmployeeState}.
 * The action reads attributes and calls service to execute the update.
 *
 * @author s.vareyko
 * @see EmployeeState
 * @see com.reconnect.web.peopleflow.config.StateMachineConfiguration
 * @since 01.04.2021
 */
@Slf4j
@Component
@AllArgsConstructor
public class EmployeeActivateAction implements Action<EmployeeState, EmployeeEvent> {

    private final EmployeeService employeeService;

    @Override
    public void execute(final StateContext<EmployeeState, EmployeeEvent> context) {
        final ExtendedState ext = context.getExtendedState();
        final Long userId = ext.get(Attrs.ATTR_USER_ID, Long.class);
        final EmployeeState state = ext.get(Attrs.ATTR_STATE, EmployeeState.class);
        final EmployeeState newState = employeeService.update(userId, state);
        log.info("Assigned a new state: {} to {}", newState, userId);
    }
}
