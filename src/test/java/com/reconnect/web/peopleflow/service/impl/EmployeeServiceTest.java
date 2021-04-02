package com.reconnect.web.peopleflow.service.impl;

import com.reconnect.web.peopleflow.AppTest;
import com.reconnect.web.peopleflow.dto.EmployeeDto;
import com.reconnect.web.peopleflow.enums.EmployeeState;
import com.reconnect.web.peopleflow.exceptions.StateUpdateException;
import com.reconnect.web.peopleflow.model.Employee;
import com.reconnect.web.peopleflow.repository.EmployeeRepository;
import com.reconnect.web.peopleflow.service.EmployeeService;
import com.reconnect.web.peopleflow.service.mapper.EmployeeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.atomic.AtomicReference;

import static com.reconnect.web.peopleflow.utils.EmployeeUtils.createEmployeeDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
@AppTest
public class EmployeeServiceTest {

    @MockBean
    private EmployeeRepository repository;

    private EmployeeService employeeService;

    @BeforeEach
    public void before() {
        employeeService = new EmployeeServiceImpl(repository, new EmployeeMapper());
    }

    @Test
    public void createEmployeeTest() {
        final EmployeeDto employee = createEmployeeDto(false);
        final AtomicReference<Employee> entity = new AtomicReference<>(null);
        when(repository.save(any(Employee.class))).thenAnswer(invocation -> {
            final Employee argument = invocation.getArgument(0, Employee.class);
            entity.set(argument);
            return argument;
        });

        final EmployeeDto result = employeeService.create(employee);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(employee.getUsername());
        assertThat(result.getAge()).isEqualTo(employee.getAge());
        assertThat(result.getTechnology()).isEqualTo(employee.getTechnology());
        assertThat(result.getExperience()).isEqualTo(employee.getExperience());

        assertThat(result.getState()).isEqualTo(EmployeeState.ADDED);
        verify(repository).save(eq(entity.get()));
    }

    @Test
    public void updateEmployeeSuccessfulTest() {
        final String username = "Robert";
        final EmployeeState state = EmployeeState.ACTIVE;
        final int resultsNum = 1;
        when(repository.updateState(anyString(), any(EmployeeState.class))).thenReturn(resultsNum);

        final EmployeeState result = employeeService.update(username, state);

        assertThat(result).isEqualTo(state);
        verify(repository).updateState(eq(username), eq(state));
    }

    @Test
    public void updateEmployeeFailureTest() {
        final String username = "Angela";
        final EmployeeState state = EmployeeState.IN_CHECK;
        final int resultsNum = 0;
        when(repository.updateState(anyString(), any(EmployeeState.class))).thenReturn(resultsNum);

        assertThrows(StateUpdateException.class, () -> employeeService.update(username, state));
    }

}
