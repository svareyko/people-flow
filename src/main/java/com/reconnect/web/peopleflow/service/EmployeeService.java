package com.reconnect.web.peopleflow.service;

import com.reconnect.web.peopleflow.dto.EmployeeDto;
import com.reconnect.web.peopleflow.enums.EmployeeState;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
public interface EmployeeService {

    /**
     * A simple create method, that changes {@link EmployeeDto#setState(EmployeeState)} to {@link EmployeeState#ADDED}.
     *
     * @param employeeDto to be stored
     * @return saved dto
     */
    EmployeeDto create(EmployeeDto employeeDto);

    /**
     * A simple method that executes the update of entry in the DB and validates the result.
     *
     * @param id    user's ID
     * @param state is to be set to that user
     * @return new state
     * @throws com.reconnect.web.peopleflow.exceptions.StateUpdateException if no changes performed to DB
     */
    EmployeeState update(Long id, EmployeeState state);
}
