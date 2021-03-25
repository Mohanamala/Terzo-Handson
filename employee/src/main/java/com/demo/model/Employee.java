package com.demo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditListener.class)
@Entity
@Table(name="EMPLOYEE")
@JsonIgnoreProperties("managers")
public class Employee implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="emp_seq_val")
	@SequenceGenerator(sequenceName="emp_seq",allocationSize =1,name="emp_seq_val")
	@Column(name="EMPLOYEE_ID")
	private long empID;
	
	@JoinColumn(name="MANAGER_ID",referencedColumnName="EMPLOYEE_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference("manager")
	private Employee manager;
	
	@OneToMany(mappedBy="manager")
	@JsonManagedReference("managers")
	@JsonIgnore
	private Set<Employee> managers = new HashSet<Employee>(0);
	
	@JsonIgnore
	@JsonManagedReference
	public Set<Employee> getManagers() {
		return managers;
	}

	public void setManagers(Set<Employee> managers) {
		this.managers = managers;
	}
	@NotEmpty(message = "Name must not be empty")
	@NotNull(message = "Name must not be null")
	@Column(name="EMPLOYEE_NAME")
	private String empName;
	
	@NotNull(message = "Salary must not be null")
	@Column(name="SALARY")
	private double salary;
	
	
	@NotNull(message = "Dept ID must not be empty")
	@Column(name="DEPARTMENT")
	private int deptID;
	
	
	@Embedded
	private AuditLog auditLog;
	
	public enum Designation{
		ASSOCIATE,
		ARCHITECT,
		MANAGER
	}

	public long getEmpID() {
		return empID;
	}

	public void setEmpID(long empID) {
		this.empID = empID;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public int getDeptID() {
		return deptID;
	}

	public void setDeptID(int deptID) {
		this.deptID = deptID;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="department", referencedColumnName="DEPTNO",nullable = false,insertable = false,updatable = false)
	@JsonBackReference
	private Department department;
	
	
	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}
	
	@NotNull(message = "Designation must not be empty")
	@Enumerated(EnumType.STRING)
	@Column(unique = true,name ="designation")
	private Designation designation;

	public AuditLog getAuditLog() {
		return auditLog;
	}

	public void setAuditLog(AuditLog auditLog) {
		this.auditLog = auditLog;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	

}
