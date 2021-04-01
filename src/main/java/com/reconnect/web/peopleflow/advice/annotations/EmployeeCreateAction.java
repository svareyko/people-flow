package com.reconnect.web.peopleflow.advice.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation, that will force the state creation, require {@link com.reconnect.web.peopleflow.dto.EmployeeDto}
 * as a return value, based on which generates a key.
 *
 * @author s.vareyko
 * @see com.reconnect.web.peopleflow.dto.EmployeeDto
 * @see com.reconnect.web.peopleflow.advice.EmployeeAdvisor
 * @since 01.04.2021
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmployeeCreateAction {
}
