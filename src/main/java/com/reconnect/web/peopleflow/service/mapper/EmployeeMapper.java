package com.reconnect.web.peopleflow.service.mapper;

import com.reconnect.web.peopleflow.dto.EmployeeDto;
import com.reconnect.web.peopleflow.model.Employee;
import org.springframework.stereotype.Component;

/**
 * Helper component that copies data from DTO to Entity and back.
 *
 * @author s.vareyko
 * @since 01.04.2021
 */
@Component
public class EmployeeMapper {

    /**
     * Simple copy of data from DTO to new Entity.
     *
     * @param dto as a source object
     * @return entity that was created based on the DTO data
     */
    public Employee map(final EmployeeDto dto) {
        final Employee entity = new Employee();
        entity.setAge(dto.getAge());
        entity.setContract(dto.getContract());
        entity.setExperience(dto.getExperience());
        entity.setUsername(dto.getUsername());
        entity.setTechnology(dto.getTechnology());
        entity.setState(dto.getState());
        return entity;
    }

    /**
     * Simple copy of data from Entity to new DTO.
     *
     * @param entity as a source object
     * @return DTO that was created based on the Entity data
     */
    public EmployeeDto map(final Employee entity) {
        final EmployeeDto dto = new EmployeeDto();
        dto.setUsername(entity.getUsername());
        dto.setExperience(entity.getExperience());
        dto.setAge(entity.getAge());
        dto.setContract(entity.getContract());
        dto.setTechnology(entity.getTechnology());
        dto.setState(entity.getState());
        return dto;
    }
}
