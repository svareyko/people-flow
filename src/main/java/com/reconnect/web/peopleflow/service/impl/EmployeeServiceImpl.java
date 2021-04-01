package com.reconnect.web.peopleflow.service.impl;

import com.reconnect.web.peopleflow.dto.EmployeeDto;
import com.reconnect.web.peopleflow.enums.EmployeeState;
import com.reconnect.web.peopleflow.exceptions.StateUpdateException;
import com.reconnect.web.peopleflow.model.Employee;
import com.reconnect.web.peopleflow.repository.EmployeeRepository;
import com.reconnect.web.peopleflow.service.EmployeeService;
import com.reconnect.web.peopleflow.service.mapper.EmployeeMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final int ONE_CHANGE = 1;

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;

    @Override
    public EmployeeDto create(final EmployeeDto employeeDto) {
        final Employee entity = mapper.map(employeeDto);
        entity.setState(EmployeeState.ADDED);
        final Employee saved = repository.save(entity);
        return mapper.map(saved);
    }

    @SneakyThrows
    @Override
    @Transactional
    public EmployeeState update(final String username, final EmployeeState state) {
        if (repository.updateState(username, state) == ONE_CHANGE) {
            return state;
        }
        throw new StateUpdateException(username, state);
    }
}
