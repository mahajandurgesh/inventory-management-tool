package com.mvc.inventory.management.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String home(){
		return "index";
	}
	
	@RequestMapping("/loginProcess")
	public String loginProcess(HttpServletRequest request, Model m){
		String email = request.getParameter("Email");
		String password = request.getParameter("Password");
		
		if(email.equals("admin@gmail.com") && password.equals("admin")){
			return "dashboard";
		}
		else{
			m.addAttribute("id",-1);
			return "index";
		}
	}
	
	@RequestMapping("/dashboard")
	public String dashboard(){
		return "dashboard";
	}
	
	@RequestMapping("/logout")
	public String logout(){
		return "index";
	}
}
