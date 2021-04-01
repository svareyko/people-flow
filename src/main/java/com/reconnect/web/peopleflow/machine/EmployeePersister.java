package com.reconnect.web.peopleflow.machine;

import com.reconnect.web.peopleflow.enums.EmployeeEvent;
import com.reconnect.web.peopleflow.enums.EmployeeState;
import com.reconnect.web.peopleflow.exceptions.MissingStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
@RequiredArgsConstructor
public class EmployeePersister implements StateMachinePersist<EmployeeState, EmployeeEvent, String> {

    // todo: implement storing in the DB if need to remember machine states between restarts
    private final Map<String, StateMachineContext<EmployeeState, EmployeeEvent>> SIMPLEST_FASTEST_RUNTIME_CACHE = new ConcurrentHashMap<>();

    @Override
    public void write(final StateMachineContext<EmployeeState, EmployeeEvent> context, final String username) {
        SIMPLEST_FASTEST_RUNTIME_CACHE.put(username, context);
    }

    @Override
    public StateMachineContext<EmployeeState, EmployeeEvent> read(final String username) {
        final StateMachineContext<EmployeeState, EmployeeEvent> context = SIMPLEST_FASTEST_RUNTIME_CACHE.get(username);
        if (Objects.nonNull(context)) {
            return context;
        }
        throw new MissingStateException(username);
    }
}
