package com.reconnect.web.peopleflow.model;

import com.reconnect.web.peopleflow.enums.EmployeeState;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
@Data
@Entity
public class Employee {

    @Id
    private String username;
    private Integer age;
    private String contract;
    private Integer experience;
    private String technology;
    @Enumerated(EnumType.STRING)
    private EmployeeState state;
}
