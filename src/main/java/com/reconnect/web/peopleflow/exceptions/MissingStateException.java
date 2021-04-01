package com.reconnect.web.peopleflow.exceptions;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
public class MissingStateException extends IllegalStateException {
    public MissingStateException(final String username) {
        super("State for " + username + " was not found");
    }
}
