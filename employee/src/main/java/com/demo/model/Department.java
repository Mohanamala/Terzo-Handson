package com.demo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="department")
@JsonIgnoreProperties({"hibernateLazyInitializer","employees"})
public class Department implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull(message = "Dept ID must not be empty")
	@Column(name="DEPTNO")
	private int deptID;
	
    @NotBlank(message = "DepartName must not be empty")
	@Column(name="DEPTNAME")
	private String deptName;
	
	@OneToMany(mappedBy="department",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<Employee> employees = new HashSet<Employee>(0);

	public int getDeptID() {
		return deptID;
	}

	public void setDeptID(int deptID) {
		this.deptID = deptID;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}


}
