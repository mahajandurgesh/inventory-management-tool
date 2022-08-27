package com.mvc.inventory.management.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mvc.inventory.management.model.RawLogs;
import com.mvc.inventory.management.model.RawMaterial;

@Component
public class RawLogsDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Transactional
	public int insert(RawLogs rawLogs){
		return (Integer)this.hibernateTemplate.save(rawLogs);
	}
	
	public List<RawLogs> getAll(){
		return this.hibernateTemplate.loadAll(RawLogs.class);
	}
}
