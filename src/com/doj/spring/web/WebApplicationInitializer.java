package com.doj.spring.web;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.doj.spring.web.config.SpringWebConfiguration;

//this file is equivalent to web.xml
public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	//Configuration for non web components like services, daos, repos, etc.
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}
	
	//Specifying Spring MVC configuration class "SpringWebConfiguration.class" it equivalent to *-servlet.xml file
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{SpringWebConfiguration.class};
	}

	//Mapping dispatcher server to "/" i.e. Servlet Mapping in the web.xml 
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}
}
