package com.reconnect.web.peopleflow.web;

import com.reconnect.web.peopleflow.advice.annotations.EmployeeCreateAction;
import com.reconnect.web.peopleflow.advice.annotations.EmployeeUpdateAction;
import com.reconnect.web.peopleflow.dto.EmployeeDto;
import com.reconnect.web.peopleflow.enums.EmployeeState;
import com.reconnect.web.peopleflow.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping("/create")
    @EmployeeCreateAction
    public EmployeeDto createEmployee(@Valid @RequestBody final EmployeeDto employee) {
        return service.create(employee);
    }

    @EmployeeUpdateAction(state = EmployeeState.ACTIVE)
    @PostMapping("/activate/{userId}")
    public void activateEmployee(@PathVariable final Long userId) {
        log.info("Requested activation for user with ID {}", userId);
    }

    @EmployeeUpdateAction(state = EmployeeState.IN_CHECK)
    @PostMapping("/check/{userId}")
    public void inCheckEmployee(@PathVariable final Long userId) {
        log.info("Requested in-check for user with ID {}", userId);
    }

    @EmployeeUpdateAction(state = EmployeeState.APPROVED)
    @PostMapping("/approve/{userId}")
    public void approveEmployee(@PathVariable final Long userId) {
        log.info("Requested approval for user with ID {}", userId);
    }
}
