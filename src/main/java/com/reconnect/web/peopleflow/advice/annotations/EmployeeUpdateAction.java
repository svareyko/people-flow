package com.reconnect.web.peopleflow.advice.annotations;

import com.reconnect.web.peopleflow.enums.EmployeeState;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that will trigger state update actions based on method arguments and {@link #state()} parameter.
 *
 * @author s.vareyko
 * @see com.reconnect.web.peopleflow.advice.EmployeeAdvisor
 * @since 01.04.2021
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmployeeUpdateAction {

    /**
     * Requested state name, to be called.
     *
     * @return name of the state
     * @see EmployeeState
     */
    EmployeeState state();
}
