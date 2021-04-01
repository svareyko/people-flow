package com.reconnect.web.peopleflow.config;

import com.reconnect.web.peopleflow.enums.EmployeeEvent;
import com.reconnect.web.peopleflow.enums.EmployeeState;
import com.reconnect.web.peopleflow.machine.EmployeeActivateAction;
import com.reconnect.web.peopleflow.machine.EmployeePersister;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.EnumSet;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
@Configuration
@AllArgsConstructor
@EnableStateMachineFactory
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<EmployeeState, EmployeeEvent> {

    private final EmployeeActivateAction employeeActivateAction;

    @Override
    public void configure(final StateMachineConfigurationConfigurer<EmployeeState, EmployeeEvent> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(final StateMachineStateConfigurer<EmployeeState, EmployeeEvent> states) throws Exception {
        states
                .withStates()
                .initial(EmployeeState.ADDED)
                .states(EnumSet.allOf(EmployeeState.class));
    }

    @Override
    public void configure(final StateMachineTransitionConfigurer<EmployeeState, EmployeeEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(null).target(EmployeeState.ADDED)
                .event(EmployeeEvent.EMPLOYEE_ADD)

                .and()
                .withExternal()
                .source(EmployeeState.ADDED).target(EmployeeState.ACTIVE)
                .event(EmployeeEvent.EMPLOYEE_ACTIVATE)
                .action(employeeActivateAction)

                .and()
                .withExternal()
                .source(EmployeeState.ADDED).target(EmployeeState.IN_CHECK)
                .event(EmployeeEvent.EMPLOYEE_ACTIVATE)
                .action(employeeActivateAction)

                .and()
                .withExternal()
                .source(EmployeeState.ADDED).target(EmployeeState.APPROVED)
                .event(EmployeeEvent.EMPLOYEE_ACTIVATE)
                .action(employeeActivateAction);
    }

    @Bean
    public StateMachinePersister<EmployeeState, EmployeeEvent, String> persister() {
        return new DefaultStateMachinePersister<>(new EmployeePersister());
    }
}
