package com.reconnect.web.peopleflow.exceptions;

import com.reconnect.web.peopleflow.enums.EmployeeState;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
public class StateUpdateException extends IllegalStateException {
    public StateUpdateException(final String username, final EmployeeState state) {
        super("Unable to update state of employee " + username + ", required state is " + state);
    }
}
