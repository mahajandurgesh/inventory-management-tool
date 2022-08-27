package com.mvc.inventory.management.controller;

import java.util.List;

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
import org.springframework.web.servlet.view.RedirectView;

import com.mvc.inventory.management.dao.ProcessedLogsDao;
import com.mvc.inventory.management.dao.ProcessedMaterialDao;
import com.mvc.inventory.management.dao.ProcessedMaterialDao;
import com.mvc.inventory.management.model.ProcessedMaterial;
import com.mvc.inventory.management.model.RawLogs;
import com.mvc.inventory.management.model.RawMaterial;
import com.mvc.inventory.management.model.ProcessedLogs;
import com.mvc.inventory.management.model.ProcessedMaterial;
import com.mvc.inventory.management.model.ProcessedLogs;
import com.mvc.inventory.management.model.ProcessedLogs;
import com.mvc.inventory.management.model.ProcessedMaterial;
import com.mvc.inventory.management.model.ProcessedLogs;
import com.mvc.inventory.management.model.ProcessedMaterial;

@Controller
public class ProcessedMaterialController {

	static ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	static ProcessedMaterialDao processedMaterialDao = (ProcessedMaterialDao) context.getBean("processedMaterialDao");
	static ProcessedLogsDao processedLogsDao = (ProcessedLogsDao) context.getBean("processedLogsDao");
	
	
	@RequestMapping("/processedIdExists")
	public String processedIdExists(Model m){
		m.addAttribute("id",-1);
		return "addProcessedMaterial";
	}
	
	@RequestMapping("/addProcessedItem")
	public String addProcessedItem(){
		return "addProcessedMaterial";
	}
	
	@RequestMapping("/insertProcessedMaterial")
	public RedirectView insertProcessedMaterial(@ModelAttribute ProcessedMaterial processedMaterial, HttpServletRequest request){
		//System.out.println(processedMaterial);
		RedirectView redirect = new RedirectView();
		List<ProcessedMaterial> processedMaterials = processedMaterialDao.getAll();
		for(ProcessedMaterial rm : processedMaterials){
			if(rm.getId()==processedMaterial.getId()){
				redirect.setUrl(request.getContextPath()+"/processedIdExists");
				return redirect;
			}
		}
		processedMaterialDao.insert(processedMaterial);
		
		ProcessedLogs processedLogs = (ProcessedLogs) context.getBean("processedLogs");
		processedLogs.setLogId(0);
		processedLogs.setId(processedMaterial.getId());
		processedLogs.setName(processedMaterial.getName());
		processedLogs.setQuantity(processedMaterial.getQuantity());
		processedLogs.setStatus("ADDED");
		processedLogsDao.insert(processedLogs);
		
		
		redirect.setUrl(request.getContextPath()+"/viewProcessedMaterials");
		return redirect;
	}
	
	@RequestMapping("/viewProcessedMaterials")
	public String viewProcessedMaterials(Model m){
		List<ProcessedMaterial> allProcessedMaterials = processedMaterialDao.getAll();
		m.addAttribute("allProcessedMaterials",allProcessedMaterials);
		return "viewProcessedMaterials";
	}
	
	@RequestMapping("/deleteProcessedMaterial/{id}")
	public RedirectView deleteProcessedMaterial(@PathVariable("id") int id, HttpServletRequest request){
		ProcessedMaterial rm = processedMaterialDao.get(id);
		
		ProcessedLogs processedLogs = (ProcessedLogs) context.getBean("processedLogs");
		processedLogs.setLogId(0);
		processedLogs.setId(rm.getId());
		processedLogs.setName(rm.getName());
		processedLogs.setQuantity(rm.getQuantity());
		processedLogs.setStatus("DELETED");
		
		
		processedLogsDao.insert(processedLogs);
		
		processedMaterialDao.delete(id);
		RedirectView redirect = new RedirectView();
		redirect.setUrl(request.getContextPath()+"/viewProcessedMaterials");
		return redirect;
	}
	
	@RequestMapping(value="/updateProcessedQuantity",method = RequestMethod.GET)
	public String updateProcessedQuantity(@RequestParam int id, Model m){
		m.addAttribute("id",id);
		return "updateProcessedQuantity";
	}
	
	@RequestMapping("/incrementProcessedMaterial")
	public RedirectView incrementProcessedMaterial(HttpServletRequest request){
		System.out.println(request.getParameter("quantity"));
		System.out.println(request.getParameter("id"));
		int new_quantity = Integer.parseInt(request.getParameter("quantity"));
		int id = Integer.parseInt(request.getParameter("id"));
		ProcessedMaterial updatedProcessedMaterial = processedMaterialDao.get(id);
		updatedProcessedMaterial.setQuantity(updatedProcessedMaterial.getQuantity()+new_quantity);
		processedMaterialDao.update(updatedProcessedMaterial);
		
		ProcessedLogs processedLogs = (ProcessedLogs) context.getBean("processedLogs");
		processedLogs.setLogId(0);
		processedLogs.setId(updatedProcessedMaterial.getId());
		processedLogs.setName(updatedProcessedMaterial.getName());
		processedLogs.setQuantity(new_quantity);
		processedLogs.setStatus("UPDATED");
		
		
		processedLogsDao.insert(processedLogs);
		
		RedirectView redirect = new RedirectView();
		redirect.setUrl(request.getContextPath()+"/viewProcessedMaterials");
		return redirect;
	}
	
	@RequestMapping(value="/issueProcessedMaterial",method = RequestMethod.GET)
	public String issueProcessedMaterial(@RequestParam int id, Model m){
		m.addAttribute("id",id);
		m.addAttribute("maxQuantity",processedMaterialDao.get(id).getQuantity());
		return "issueProcessedMaterial"; 
	}
	
	@RequestMapping("/decrementProcessedMaterial")
	public RedirectView decrementProcessedMaterial(HttpServletRequest request){
		int new_quantity = Integer.parseInt(request.getParameter("quantity"));
		int id = Integer.parseInt(request.getParameter("id"));
		String issuer = request.getParameter("issuer");
		ProcessedMaterial updatedProcessedMaterial = processedMaterialDao.get(id);
		if(new_quantity<=updatedProcessedMaterial.getQuantity()){
			updatedProcessedMaterial.setQuantity(updatedProcessedMaterial.getQuantity()-new_quantity);
			processedMaterialDao.update(updatedProcessedMaterial);
			ProcessedLogs processedLogs = (ProcessedLogs) context.getBean("processedLogs");
			processedLogs.setLogId(0);
			processedLogs.setId(id);
			processedLogs.setName(updatedProcessedMaterial.getName());
			processedLogs.setQuantity(new_quantity);
			processedLogs.setStatus("ISSUED");
			processedLogs.setIssuer(issuer);
			
			processedLogsDao.insert(processedLogs);
		}
		
		RedirectView redirect = new RedirectView();
		redirect.setUrl(request.getContextPath()+"/viewProcessedMaterials");
		return redirect;
	}
	
	@RequestMapping("/viewProcessedLogs")
	public String viewRawLogs(Model m){
		List<ProcessedLogs> allProcessedLogs = processedLogsDao.getAll();
		m.addAttribute("allProcessedLogs",allProcessedLogs);
		return "viewProcessedLogs";
	}
}
