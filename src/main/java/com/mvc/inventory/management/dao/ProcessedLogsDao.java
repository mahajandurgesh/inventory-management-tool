package com.mvc.inventory.management.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mvc.inventory.management.model.ProcessedLogs;
import com.mvc.inventory.management.model.RawLogs;

@Component
public class ProcessedLogsDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Transactional
	public int insert(ProcessedLogs processedLogs){
		return (Integer)this.hibernateTemplate.save(processedLogs);
	}
	
	public List<ProcessedLogs> getAll(){
		return this.hibernateTemplate.loadAll(ProcessedLogs.class);
	}
}
