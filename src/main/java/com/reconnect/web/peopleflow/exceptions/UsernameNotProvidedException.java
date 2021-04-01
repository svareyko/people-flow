package com.reconnect.web.peopleflow.exceptions;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
public class UsernameNotProvidedException extends IllegalStateException {

    public UsernameNotProvidedException() {
        super("Username not found in the method arguments!");
    }
}
