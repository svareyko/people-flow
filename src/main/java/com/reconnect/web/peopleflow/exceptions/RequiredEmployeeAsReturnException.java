package com.reconnect.web.peopleflow.exceptions;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
public class RequiredEmployeeAsReturnException extends IllegalStateException {

    public RequiredEmployeeAsReturnException() {
        super("Annotated method must return DTO that contains username, check details in the annotation");
    }
}
