package com.mvc.inventory.management.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mvc.inventory.management.model.ProcessedMaterial;
import com.mvc.inventory.management.model.RawMaterial;

@Component
public class ProcessedMaterialDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Transactional
	public int insert(ProcessedMaterial processedMaterial){
		Integer i = (Integer) this.hibernateTemplate.save(processedMaterial);
		return i;
	}
	
	public List<ProcessedMaterial> getAll(){
		return this.hibernateTemplate.loadAll(ProcessedMaterial.class);
	}
	
	public ProcessedMaterial get(int id){
		return this.hibernateTemplate.get(ProcessedMaterial.class, id);
	}
	
	@Transactional
	public void delete(int id){
		this.hibernateTemplate.delete(this.get(id));
	}
	
	@Transactional
	public void update(ProcessedMaterial updatedProcessedMaterial){
		this.hibernateTemplate.update(updatedProcessedMaterial);
	}
}
