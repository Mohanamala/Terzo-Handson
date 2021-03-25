package com.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.demo.model.Employee;
import com.demo.model.Employee.Designation;
import com.demo.repo.EmployeeRepo;

public class EmployeeSpec {
	
	public static Specification<Employee> findById(long id){
		return(root,query,criteriaBuilder)->{
			return(criteriaBuilder.equal(root.get("empID"), id));
		};
	}
	
	public static Specification<Employee> hasSalaryAbove(double salary,EmployeeRepo repo){
		return(root,query,criteriaBuilder)->{
			List<Predicate> predicates = new ArrayList<>();
			CriteriaBuilder.In<Long> in = criteriaBuilder.in(root.get("empID"));
			predicates.add(processInCondition(in,repo.fetchID()));
			predicates.add(criteriaBuilder.greaterThan(root.get("salary"), salary));
			query.orderBy(criteriaBuilder.asc(root.get("empName")));
			return(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
		};
	}
	
	public static Specification<Employee> hasSalaryAbove(double salary,List<String> emp){
		return(root,query,criteriaBuilder)->{
			List<Predicate> predicates = new ArrayList<>();
			query.orderBy(criteriaBuilder.desc(root.get("salary")));
			CriteriaBuilder.In<Long> in = criteriaBuilder.in(root.get("empID"));
			predicates.add(processInCondition(in,emp));
			predicates.add(criteriaBuilder.greaterThan(root.get("salary"), salary));
			return(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
		};
	}
	
	public static CriteriaBuilder.In<Long> processInCondition(CriteriaBuilder.In<Long> in,List<String> condition){
		for(int i=0;i<condition.size();i++)
			in.value(Long.valueOf(condition.get(i)));
		return in;
	}
	
	@SuppressWarnings("serial")
	public static Specification<Employee> findByTitle(Designation id){
		return new Specification<Employee>() {
			public Predicate toPredicate(Root<Employee> root,CriteriaQuery<?> query,CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get("designation"), id);
			}
		};
	}

	public static Specification<Employee> hasEmpID(EmployeeRepo empRepo) {
		return(root,query,criteriaBuilder)->{
			CriteriaBuilder.In<Long> in = criteriaBuilder.in(root.get("empID"));
			Predicate eq = processInCondition(in,empRepo.fetchID());
			return(criteriaBuilder.and(eq));
		};
	}
	
}
