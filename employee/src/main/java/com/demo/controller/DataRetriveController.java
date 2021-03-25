package com.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.exception.RecordNotfoundException;
import com.demo.model.AuditListener;
import com.demo.model.Department;
import com.demo.model.Employee;
import com.demo.model.Employee.Designation;
import com.demo.service.EmployeeService;

@RestController
@RequestMapping("employee-management")
public class DataRetriveController {
	@Autowired
	EmployeeService empService;
	
	
	//Create and Employee
	@PostMapping(path="/saveEmp")
	private String saveEmpData(@Valid @RequestBody Employee e)  {
		empService.saveEmpData(e);
		return AuditListener.response;	
	}
	
	//Update Employee
	@PostMapping(path="/updateEmp")
	private String updateEmpData(@Valid @RequestBody List<Employee> e) throws Exception{
		empService.updateEmpData(e);
		return AuditListener.response;
	}
	
	//Create & Update Department
	@PostMapping(path="/postDept")
	private void postDept(@Valid @RequestBody Department d) {
		empService.saveDept(d);
	}
	
	//Get Employee By Id
	@GetMapping(path="/findByEmpID")
	@ResponseBody
	private ResponseEntity<Object> findById(@RequestParam("Id") long empID) {
		Optional<Employee> result =empService.findByID(empID,false);
		if(result.isPresent())
		return new ResponseEntity<Object>(result,HttpStatus.OK);
		else
			throw new RecordNotfoundException("Record Not Found","EmployeeID",String.valueOf(empID));
	}
	
	//Get Department by ID
	@GetMapping(path="/findDeptById")
	@ResponseBody
	private ResponseEntity <Object> findDeptById(@RequestParam("Id") long empID){
		Optional<Employee> result = empService.findByID(empID,false);
		if(result.isPresent() && result.get()!=null)
			return new ResponseEntity<Object>(result.get().getDepartment(),HttpStatus.OK);
		else
			throw new RecordNotfoundException("Department Not Found for the given Employee ID","Employee ID", String.valueOf(empID));
	}
	

	//Get all employees by Department ID
	@GetMapping(path="/getEmpByDeptID")
	@ResponseBody
	private ResponseEntity<Object> findById(@RequestParam("Id") int deptID){
		Optional<Department> result = empService.findByDepId(deptID);
		if(result!=null && result.isPresent()) 
			return new ResponseEntity<Object>(result.get().getEmployees(),HttpStatus.OK);
		else
			throw new RecordNotfoundException("Employee Not Found under this Department","Department ID",String.valueOf(deptID));
	}
	
	//Get Employee by ID using specification
	@GetMapping(path="/fetchByEmpID")
	@ResponseBody
	private ResponseEntity<Object> findByIdUsingSpec(@RequestParam("Id") long empID){
		Optional<Employee> result = empService.findByID(empID,true);
		if(result.isPresent()) 
			return new ResponseEntity<Object>(result,HttpStatus.OK);
		else
			throw new RecordNotfoundException("Employee Not found","Employee ID",String.valueOf(empID));
	}
	
	//Get all employees whose salary greater than specified amount
	@GetMapping(path="/getEmpBySalary")
	@ResponseBody
	private ResponseEntity<Object> findEmpBySalary(@RequestParam("Salary") double Salary){
		List<Employee> emp = empService.findEmpBySalary(Salary);
		if(emp!=null && emp.size()>0)
			return new ResponseEntity<Object>(emp,HttpStatus.OK);	
		else
			throw new RecordNotfoundException("Employee not found in this salary range","Salary",String.valueOf(Salary));
	}
	
	//Get all employees for given department whose salary greater than specified amount
	@GetMapping(path="/getEmpinDeptBySalary")
	@ResponseBody
	private ResponseEntity<Object> getEmpinDeptBySalary(@RequestParam("deptID") int deptID,@RequestParam("Salary") double salary){
		List<Employee> emp = null;
		Optional<Department> result = empService.findByDepId(deptID);
		if(result.isPresent()) {
			emp = empService.findEmpBySalary(result.get().getEmployees(), salary);
		return new ResponseEntity<Object>(emp,HttpStatus.OK);
		}
		else
			throw new RecordNotfoundException("Employee Not found under the given Department on this salary range","DEPT ID-SALARY",String.valueOf(deptID)+"-"+String.valueOf(salary));
	 }
	
	//Get Employees based on given designation
	@GetMapping(path="/getEmpbyRole")
	@ResponseBody
	private ResponseEntity<Object> getEmpbyRole(@RequestParam("title") String designation){
		List<Employee> emp = empService.findByTitle(checkRole(null!=designation && designation.length()>0?designation.toUpperCase():null));
		if(emp!=null && emp.size()>0)	
		return new ResponseEntity<Object>(emp,HttpStatus.OK);
			else
				throw new RecordNotfoundException("Employee not found in this Role","Designation",designation);
	}
	
	private Designation checkRole(String designation) {
		Designation[] role = Designation.values();
		for(Designation d: role) {
			if(d.toString().equals(designation))
				return d;
		}
		return null;
	}
}
