package com.demo.model;

import java.time.Instant;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AuditListener {
	
	public static String response="";
	
	@PrePersist
	public static void prePersist(Employee e) {
		AuditLog addLog;
		if(e.getAuditLog()==null) {
			addLog = new AuditLog();
			e.setAuditLog(addLog);
		}
		e.getAuditLog().setCreatedBy("MOHANA");
		e.getAuditLog().setCreatedDate(Instant.now());
		e.getAuditLog().setModifiedBy("MOHANA");
		e.getAuditLog().setModifiedDate(Instant.now());		
	}
	
	@PreUpdate
	public static void preUpdate(Employee e) {
		AuditLog addLog;
		if(e.getAuditLog()==null) {
			addLog = new AuditLog();
			e.setAuditLog(addLog);
			e.getAuditLog().setCreatedDate(Instant.now());
			e.getAuditLog().setModifiedDate(Instant.now());
		}else {
			e.getAuditLog().setCreatedDate(e.getAuditLog().getCreatedDate());
			e.getAuditLog().setModifiedDate(Instant.now());
		}
		e.getAuditLog().setCreatedBy("MOHANA");
		e.getAuditLog().setModifiedBy("MOHANA");
	}
	
	@PostPersist
	public static void postPesist(Employee e) {
		response ="EMPLOYEE RECORDS INSERTED SUCCESSULLY";
	}
	
	@PostUpdate
	public static void postUpdate(Employee e) {
		response ="EMPLOYEE RECORDS UPDATED SUCCESSFULLY";
	}

}
