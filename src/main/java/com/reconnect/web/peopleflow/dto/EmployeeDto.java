package com.reconnect.web.peopleflow.dto;

import com.reconnect.web.peopleflow.enums.EmployeeState;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import static com.reconnect.web.peopleflow.enums.Attrs.AGE_MAX;
import static com.reconnect.web.peopleflow.enums.Attrs.AGE_MIN;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
@Data
public class EmployeeDto {

    @NotEmpty
    private String username;
    @Min(AGE_MIN)
    @Max(AGE_MAX)
    private Integer age;
    private String contract;
    private Integer experience;
    private String technology;
    private EmployeeState state;
}
