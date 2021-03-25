package com.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Department;
import com.demo.model.Employee;
import com.demo.model.Employee.Designation;
import com.demo.repo.DepartmentRepo;
import com.demo.repo.EmployeeRepo;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepo empRepo;
	
	@Autowired
	DepartmentRepo deptRepo;
	

	public Optional<Employee> findByID(long id,boolean isSpec) {
		Optional<Employee> result;
		if(isSpec)
			result = empRepo.findOne(EmployeeSpec.findById(id));
		else
			result = empRepo.findById(id);
		
			return result;
	}

	
	public List<Employee> findByTitle(Designation title){
		return  empRepo.findAll(EmployeeSpec.hasEmpID(empRepo).and(EmployeeSpec.findByTitle(title)));
	}
	
	public void updateEmpData(List<Employee> eAll) {
		for(Employee e: eAll) {
			if(e.getDesignation().equals(Designation.MANAGER))
				e.setManager(e);
		}
		empRepo.saveAll(eAll);
		}
	
	
	public Optional<Department> findByDepId(int id){
		Optional<Department> result = deptRepo.findById(id);
		if(result.isPresent())
			return result;
		else
			return null;
	}
	
	
	public void saveEmpData(Employee e) {
		if(e.getDepartment()!=null) {
			Department dept = deptRepo.findById(e.getDepartment().getDeptID()).orElse(null);
			if(dept == null) {
				dept = new Department();
				dept.setDeptID(e.getDepartment().getDeptID());
				dept.setDeptName(e.getDepartment().getDeptName());
				e.setDepartment(dept);
				deptRepo.save(e.getDepartment());
			}
		}
		if(e.getDesignation().equals(Designation.MANAGER))
			e.setManager(e);
		empRepo.save(e);
	}
	
	public void saveDept(Department d) {
		deptRepo.save(d);
	}
	
	public List<Employee> findEmpBySalary(double salary){
		return empRepo.findAll(EmployeeSpec.hasSalaryAbove(salary,empRepo));
	}
	
	public List<Employee> findEmpBySalary(Set<Employee>e,double salary){
		List<String> empIDList = new ArrayList<String>();
		for(Employee emp :e)
			empIDList.add(String.valueOf(emp.getEmpID()));
		return empRepo.findAll(EmployeeSpec.hasSalaryAbove(salary,empIDList));
	}
}
