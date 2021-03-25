package com.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.Employee;

@Repository
public interface EmployeeRepo extends CrudRepository<Employee, Long>,JpaSpecificationExecutor<Employee> {
	@Query(value = "SELECT E.EMPLOYEE_ID FROM EMPLOYEE E",nativeQuery = true)
	public List<String> fetchID();

}
