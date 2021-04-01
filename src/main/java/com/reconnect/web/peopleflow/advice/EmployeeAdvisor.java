package com.reconnect.web.peopleflow.advice;

import com.reconnect.web.peopleflow.advice.annotations.EmployeeUpdateAction;
import com.reconnect.web.peopleflow.dto.EmployeeDto;
import com.reconnect.web.peopleflow.enums.EmployeeEvent;
import com.reconnect.web.peopleflow.enums.EmployeeState;
import com.reconnect.web.peopleflow.exceptions.RequiredEmployeeAsReturnException;
import com.reconnect.web.peopleflow.exceptions.UsernameNotProvidedException;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

import static com.reconnect.web.peopleflow.enums.Attrs.ATTR_STATE;
import static com.reconnect.web.peopleflow.enums.Attrs.ATTR_USERNAME;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
@Aspect
@Component
@AllArgsConstructor
public class EmployeeAdvisor {

    private final StateMachinePersister<EmployeeState, EmployeeEvent, String> persister;
    private final StateMachineFactory<EmployeeState, EmployeeEvent> stateMachineFactory;

    /**
     * The handler method, that forces the state to be persisted.
     * Reads the return value, generates properties and persists with the persister.
     *
     * @param retVal of the method, from which will be extracted username
     * @see com.reconnect.web.peopleflow.advice.annotations.EmployeeCreateAction
     */
    @AfterReturning(
            pointcut = "@annotation(com.reconnect.web.peopleflow.advice.annotations.EmployeeCreateAction)",
            returning = "retVal"
    )
    public void afterEmployeeCreate(final Object retVal) throws Throwable {

        EmployeeDto employee = (EmployeeDto) retVal;
        if (Objects.isNull(employee)) throw new RequiredEmployeeAsReturnException();
        final String username = employee.getUsername();

        // persist SM after employee created
        final StateMachine<EmployeeState, EmployeeEvent> machine = stateMachineFactory.getStateMachine(username);
        machine.getExtendedState().getVariables().put(ATTR_USERNAME, username);
        machine.getExtendedState().getVariables().put(ATTR_STATE, employee.getState());
        persister.persist(machine, username);
    }

    /**
     * The handler of employee state update operations.
     * Reads arguments and annotation params then restore state and fires {@link EmployeeEvent#EMPLOYEE_ACTIVATE}.
     *
     * @param joinPoint where need to execute following code
     * @see com.reconnect.web.peopleflow.advice.annotations.EmployeeUpdateAction
     */
    @Before("@annotation(com.reconnect.web.peopleflow.advice.annotations.EmployeeUpdateAction)")
    public void beforeEmployeeUpdate(final JoinPoint joinPoint) throws Throwable {

        String username = findFirstStringArgument(joinPoint);
        if (Objects.isNull(username)) throw new UsernameNotProvidedException();
        final EmployeeState state = findEmployeeState(joinPoint);

        // restore state
        final StateMachine<EmployeeState, EmployeeEvent> machine = stateMachineFactory.getStateMachine(username);
        persister.restore(machine, username);

        // put values into it
        final Map<Object, Object> variables = machine.getExtendedState().getVariables();
        variables.put(ATTR_USERNAME, username);
        variables.put(ATTR_STATE, state);

        // execute transition
        machine.sendEvent(EmployeeEvent.EMPLOYEE_ACTIVATE);

        // persist results
        persister.persist(machine, username);
    }

    /**
     * Helper method that searches for first string argument in the {@link JoinPoint}.
     *
     * @param joinPoint for a search
     * @return first found string or null
     */
    private String findFirstStringArgument(final JoinPoint joinPoint) {
        final Object[] args = joinPoint.getArgs();
        String username = null;
        for (Object arg : args) {
            if (arg instanceof String) {
                username = (String) arg;
                break;
            }
        }
        return username;
    }

    /**
     * Helper method, that extracts specified {@link EmployeeState} from the annotation.
     *
     * @param joinPoint for extract
     * @return specified value
     */
    private EmployeeState findEmployeeState(final JoinPoint joinPoint) {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final Method method = signature.getMethod();
        final EmployeeUpdateAction config = method.getDeclaredAnnotation(EmployeeUpdateAction.class);
        return config.state();
    }
}
