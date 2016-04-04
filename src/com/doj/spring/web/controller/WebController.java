package com.doj.spring.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doj.spring.web.bean.Student;

@Controller
public class WebController {
	
	//multiple mapping with one remote method
	@RequestMapping(value={"/", "/index","/home","/welcome"})
	public String home(){
		return "home";
	}
	
	//With Model and Model Name to View Resolver
	@RequestMapping("/indexc")
	public ModelAndView welcome(){
		Map<String, String> model = new HashMap<>();
		model.put("name", "Sumit");
		return new ModelAndView("home","model", model);
	}
	
	//We are using Spring ModelMap for return the model value
	@RequestMapping("/hello")
	public String index(ModelMap model){
		model.put("name", "Sumit");
		return "home";
	}
	
	//We are using Spring ModelMap and fetching request parameter here
	@RequestMapping("/doj")
	public String hello(ModelMap model, HttpServletRequest request){
		String name = request.getParameter("name");
		model.put("name", name);
		return "home";
	}
	
	//We are using Spring ModelMap and Mapping the attribte with request param annotation with attributes whatever is ur requirement 
	@RequestMapping("/dojc")
	public String doj(ModelMap model, @RequestParam(defaultValue = "DOJ Students", required=true, value="fname") String name, 
			@RequestParam(required = false, value="lname") String sname){
		if(sname != null){
			name = name +" "+ sname;
		}
		model.put("name", name);
		return "home";
	}
	
	@RequestMapping("/doj-student-{fname}-{lname}")
	public String dojStudent(ModelMap model, @PathVariable(value="fname") String name, @PathVariable(value="lname") String sname,
			@RequestParam String address){
		if(sname != null){
			name = name +" "+ sname;
		}
		model.put("name", name);
		return "home";
	}
	
	@RequestMapping(value="/doj-student", method=RequestMethod.GET)
	public String getDojStudent(ModelMap model, Student student){
		String name = null;
		if(student.getFname() != null){
			name = student.getFname();
		}
		if(student.getLname() != null){
			name = name + " " +student.getLname();
		}
		if(student.getAddress() != null){
			name = name+" "+student.getAddress();
		}
		if(student.getCourse() != null){
			name = name+" "+student.getCourse();
		}
		model.put("name", name);
		return "home";
	}
	
	@RequestMapping(value = "/dojstudent", method=RequestMethod.GET)
	public String student(){
		return "student";
	}
	
	@RequestMapping(value="/dojstudent", method=RequestMethod.POST)
	public String getStudent(ModelMap model, Student student){
		String name = null;
		if(student.getFname() != null){
			name = student.getFname();
		}
		if(student.getLname() != null){
			name = name + " " +student.getLname();
		}
		if(student.getAddress() != null){
			name = name+" "+student.getAddress();
		}
		if(student.getCourse() != null){
			name = name+" "+student.getCourse();
		}
		model.put("name", name);
		return "home";
	}
	
	//1. Carrying data with redirect request using URL template like Path Variable
	@RequestMapping(value = "/hellostudent", method=RequestMethod.GET)
	public String helloStudent(Model model){
		String name = "Sumit";
		model.addAttribute("name", name);
		return "redirect:/welcomestudent-{name}";
		
		//return "redirect:/welcomestudent-"+name;
	}
	
	@RequestMapping(value = "/welcomestudent-{name}", method=RequestMethod.GET)
	public String welcomeStudent(@PathVariable String name, Model model){
		model.addAttribute("fname", name);
		return "student";
	}
	
	////2. Carrying data with redirect request using session 
	@RequestMapping(value = "/hello-student", method=RequestMethod.GET)
	public String helloDojStudent(Model model, HttpServletRequest request){
		Student student = new Student();
		HttpSession session = request.getSession();
		String name = "Sumit";
		student.setFname(name);
		session.setAttribute("student", student);
		model.addAttribute("student", student);
		return "redirect:/welcome-student";
	}
	
	@RequestMapping(value = "/welcome-student", method=RequestMethod.GET)
	public String welcomeDojStudent(Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		Student student = (Student) session.getAttribute("student");
		model.addAttribute("fname", student.getFname());
		session.removeAttribute("student");
		return "student";
	}
	
////3. Carrying data with redirect request using flash attribute 
	@RequestMapping(value = "/hello-doj-student", method=RequestMethod.GET)
	public String helloDojStudentFlash(RedirectAttributes model){
		Student student = new Student();
		String name = "Sumit";
		student.setFname(name);
		model.addAttribute("name", name);
		model.addFlashAttribute("student", student);
		return "redirect:/welcome-doj-student-{name}";
	}
	
	@RequestMapping(value = "/welcome-doj-student-{name}", method=RequestMethod.GET)
	public String welcomeDojStudentFlash(Model model, @PathVariable String name){
		Boolean flag = model.containsAttribute("student");
		if(flag){
			model.addAttribute("fname", name);
		}
		return "student";
	}
	
}
