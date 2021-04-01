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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
@Slf4j
@RestController
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping
    @EmployeeCreateAction
    public EmployeeDto createEmployee(@Valid @RequestBody final EmployeeDto employee) {
        return service.create(employee);
    }

    @EmployeeUpdateAction(state = EmployeeState.ACTIVE)
    @PostMapping("/activate/{username}")
    public void activateEmployee(@PathVariable final String username) {
        log.info("Requested activation for user {}", username);
    }

    @EmployeeUpdateAction(state = EmployeeState.IN_CHECK)
    @PostMapping("/check/{username}")
    public void inCheckEmployee(@PathVariable final String username) {
        log.info("Requested in-check for user {}", username);
    }

    @EmployeeUpdateAction(state = EmployeeState.APPROVED)
    @PostMapping("/approve/{username}")
    public void approveEmployee(@PathVariable final String username) {
        log.info("Requested approval for user {}", username);
    }
}
