package com.mvc.inventory.management.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mvc.inventory.management.model.RawMaterial;

@Component
public class RawMaterialDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Transactional
	public int insert(RawMaterial rawMaterial){
		Integer i = (Integer) this.hibernateTemplate.save(rawMaterial);
		return i;
	}
	
	public List<RawMaterial> getAll(){
		return this.hibernateTemplate.loadAll(RawMaterial.class);
	}
	
	public RawMaterial get(int id){
		return this.hibernateTemplate.get(RawMaterial.class, id);
	}
	
	@Transactional
	public void delete(int id){
		this.hibernateTemplate.delete(this.get(id));
	}
	
	@Transactional
	public void update(RawMaterial updatedRawMaterial){
		this.hibernateTemplate.update(updatedRawMaterial);
	}
}
