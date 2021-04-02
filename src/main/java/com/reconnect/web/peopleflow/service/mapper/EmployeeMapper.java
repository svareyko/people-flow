package com.reconnect.web.peopleflow.service.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Simple copy of data from DTO to new Entity.
     *
     * @param dto as a source object
     * @return entity that was created based on the DTO data
     */
    public Employee map(final EmployeeDto dto) {
        return mapper.convertValue(dto, Employee.class);
    }

    /**
     * Simple copy of data from Entity to new DTO.
     *
     * @param entity as a source object
     * @return DTO that was created based on the Entity data
     */
    public EmployeeDto map(final Employee entity) {
        return mapper.convertValue(entity, EmployeeDto.class);
    }
}
