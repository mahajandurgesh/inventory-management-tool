package com.mvc.inventory.management.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mvc.inventory.management.dao.RawLogsDao;
import com.mvc.inventory.management.dao.RawMaterialDao;
import com.mvc.inventory.management.model.RawLogs;
import com.mvc.inventory.management.model.RawMaterial;

@Controller
public class RawMaterialController {
	static ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	static RawMaterialDao rawMaterialDao = (RawMaterialDao) context.getBean("rawMaterialDao");
	static RawLogsDao rawLogsDao = (RawLogsDao) context.getBean("rawLogsDao");
	
	@RequestMapping("/rawIdExists")
	public String idExists(Model m){
		m.addAttribute("id",-1);
		return "dashboard";
	}
	
	@RequestMapping("/insertRawMaterial")
	public RedirectView insertRawMaterial(@ModelAttribute RawMaterial rawMaterial, HttpServletRequest request){
		//System.out.println(rawMaterial);
		RedirectView redirect = new RedirectView();
		List<RawMaterial> rawMaterials = rawMaterialDao.getAll();
		for(RawMaterial rm : rawMaterials){
			if(rm.getId()==rawMaterial.getId()){
				redirect.setUrl(request.getContextPath()+"/rawIdExists");
				return redirect;
			}
		}
		rawMaterialDao.insert(rawMaterial);
		
		RawLogs rawLogs = (RawLogs) context.getBean("rawLogs");
		rawLogs.setLogId(0);
		rawLogs.setId(rawMaterial.getId());
		rawLogs.setName(rawMaterial.getName());
		rawLogs.setQuantity(rawMaterial.getQuantity());
		rawLogs.setStatus("ADDED");
		
		
		rawLogsDao.insert(rawLogs);
		redirect.setUrl(request.getContextPath()+"/viewRawMaterials");
		return redirect;
	}
	
	@RequestMapping("/viewRawMaterials")
	public String viewRawMaterials(Model m){
		List<RawMaterial> allRawMaterials = rawMaterialDao.getAll();
		m.addAttribute("allRawMaterials",allRawMaterials);
		return "viewRawMaterials";
	}
	
	@RequestMapping("/deleteRawMaterial/{id}")
	public RedirectView deleteRawMaterial(@PathVariable("id") int id, HttpServletRequest request){
		RawMaterial rm = rawMaterialDao.get(id);
		
		RawLogs rawLogs = (RawLogs) context.getBean("rawLogs");
		rawLogs.setLogId(0);
		rawLogs.setId(rm.getId());
		rawLogs.setName(rm.getName());
		rawLogs.setQuantity(rm.getQuantity());
		rawLogs.setStatus("DELETED");
		
		
		rawLogsDao.insert(rawLogs);
		
		rawMaterialDao.delete(id);
		RedirectView redirect = new RedirectView();
		redirect.setUrl(request.getContextPath()+"/viewRawMaterials");
		return redirect;
	}
	
	@RequestMapping(value="/updateRawQuantity",method = RequestMethod.GET)
	public String updateRawQuantity(@RequestParam int id, Model m){
		m.addAttribute("id",id);
		return "updateRawQuantity";
	}
	
	@RequestMapping("/incrementRawMaterial")
	public RedirectView incrementRawMaterial(HttpServletRequest request){
		System.out.println(request.getParameter("quantity"));
		System.out.println(request.getParameter("id"));
		int new_quantity = Integer.parseInt(request.getParameter("quantity"));
		int id = Integer.parseInt(request.getParameter("id"));
		RawMaterial updatedRawMaterial = rawMaterialDao.get(id);
		updatedRawMaterial.setQuantity(updatedRawMaterial.getQuantity()+new_quantity);
		rawMaterialDao.update(updatedRawMaterial);
		
		RawLogs rawLogs = (RawLogs) context.getBean("rawLogs");
		rawLogs.setLogId(0);
		rawLogs.setId(updatedRawMaterial.getId());
		rawLogs.setName(updatedRawMaterial.getName());
		rawLogs.setQuantity(new_quantity);
		rawLogs.setStatus("UPDATED");
		
		
		rawLogsDao.insert(rawLogs);
		
		RedirectView redirect = new RedirectView();
		redirect.setUrl(request.getContextPath()+"/viewRawMaterials");
		return redirect;
	}
	
	@RequestMapping(value="/issueRawMaterial",method = RequestMethod.GET)
	public String issueRawMaterial(@RequestParam int id, Model m){
		m.addAttribute("id",id);
		m.addAttribute("maxQuantity",rawMaterialDao.get(id).getQuantity());
		return "issueRawMaterial"; 
	}
	
	@RequestMapping("/decrementRawMaterial")
	public RedirectView decrementRawMaterial(HttpServletRequest request){
		int new_quantity = Integer.parseInt(request.getParameter("quantity"));
		int id = Integer.parseInt(request.getParameter("id"));
		String issuer = request.getParameter("issuer");
		RawMaterial updatedRawMaterial = rawMaterialDao.get(id);
		if(new_quantity<=updatedRawMaterial.getQuantity()){
			updatedRawMaterial.setQuantity(updatedRawMaterial.getQuantity()-new_quantity);
			rawMaterialDao.update(updatedRawMaterial);
			RawLogs rawLogs = (RawLogs) context.getBean("rawLogs");
			rawLogs.setLogId(0);
			rawLogs.setId(id);
			rawLogs.setName(updatedRawMaterial.getName());
			rawLogs.setQuantity(new_quantity);
			rawLogs.setStatus("ISSUED");
			rawLogs.setIssuer(issuer);
			
			rawLogsDao.insert(rawLogs);
		}
		
		RedirectView redirect = new RedirectView();
		redirect.setUrl(request.getContextPath()+"/viewRawMaterials");
		return redirect;
	}
	
	@RequestMapping("/viewRawLogs")
	public String viewRawLogs(Model m){
		List<RawLogs> allRawLogs = rawLogsDao.getAll();
		m.addAttribute("allRawLogs",allRawLogs);
		return "viewRawLogs";
	}
}
