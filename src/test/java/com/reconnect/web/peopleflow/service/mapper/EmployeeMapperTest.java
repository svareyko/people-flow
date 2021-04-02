package com.reconnect.web.peopleflow.service.mapper;

import com.reconnect.web.peopleflow.AppTest;
import com.reconnect.web.peopleflow.dto.EmployeeDto;
import com.reconnect.web.peopleflow.model.Employee;
import org.junit.jupiter.api.Test;

import static com.reconnect.web.peopleflow.utils.EmployeeUtils.createEmployeeDto;
import static com.reconnect.web.peopleflow.utils.EmployeeUtils.createEmployeeEntity;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
@AppTest
public class EmployeeMapperTest {

    private final EmployeeMapper mapper = new EmployeeMapper();

    @Test
    public void entityToDtoConversionTest() {
        final Employee entity = createEmployeeEntity(true);

        final EmployeeDto result = mapper.map(entity);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(entity.getUsername());
        assertThat(result.getAge()).isEqualTo(entity.getAge());
        assertThat(result.getContract()).isEqualTo(entity.getContract());
        assertThat(result.getExperience()).isEqualTo(entity.getExperience());
        assertThat(result.getTechnology()).isEqualTo(entity.getTechnology());
        assertThat(result.getState()).isEqualTo(entity.getState());
    }

    @Test
    public void dtoToEntityConversionTest() {
        final EmployeeDto dto = createEmployeeDto(true);

        final Employee result = mapper.map(dto);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(dto.getUsername());
        assertThat(result.getAge()).isEqualTo(dto.getAge());
        assertThat(result.getContract()).isEqualTo(dto.getContract());
        assertThat(result.getExperience()).isEqualTo(dto.getExperience());
        assertThat(result.getTechnology()).isEqualTo(dto.getTechnology());
        assertThat(result.getState()).isEqualTo(dto.getState());
    }
}
