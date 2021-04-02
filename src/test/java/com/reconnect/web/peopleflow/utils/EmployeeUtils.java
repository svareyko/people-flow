package com.reconnect.web.peopleflow.utils;

import com.reconnect.web.peopleflow.dto.EmployeeDto;
import com.reconnect.web.peopleflow.enums.EmployeeState;
import com.reconnect.web.peopleflow.model.Employee;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
public final class EmployeeUtils {

    public static EmployeeDto createEmployeeDto(final boolean withState) {
        return createEmployeeDto("Artsem", withState);
    }

    public static EmployeeDto createEmployeeDto(final String username, final boolean withState) {
        final EmployeeDto dto = new EmployeeDto();
        dto.setUsername(username);
        dto.setAge(32);
        dto.setTechnology("Java");
        dto.setExperience(15);
        dto.setContract("Hired");
        if (withState) {
            dto.setState(EmployeeState.ACTIVE);
        }
        return dto;
    }

    public static Employee createEmployeeEntity(final boolean withState) {
        final Employee entity = new Employee();
        entity.setUsername("Sergei");
        entity.setAge(28);
        entity.setTechnology("Java");
        entity.setExperience(9);
        entity.setContract("Hired");
        if (withState) {
            entity.setState(EmployeeState.ACTIVE);
        }
        return entity;
    }


}
