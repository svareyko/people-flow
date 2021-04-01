package com.reconnect.web.peopleflow.repository;

import com.reconnect.web.peopleflow.enums.EmployeeState;
import com.reconnect.web.peopleflow.model.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String> {

    @Modifying
    @Query("update Employee set state = :state where username = :username")
    int updateState(String username, EmployeeState state);
}
