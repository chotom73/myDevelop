package com.apress.prospringmvc.bookstore.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.apress.prospringmvc.bookstore.config.InfrastructureContextConfiguration;
import com.apress.prospringmvc.bookstore.config.TestDataContextConfiguration;
import com.apress.prospringmvc.bookstore.web.config.WebMvcContextConfiguration;

public class BookstoreWebApplicationInitializer implements WebApplicationInitializer {

	private static final Logger logger = LoggerFactory.getLogger(BookstoreWebApplicationInitializer.class);
	private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		registerListener(servletContext);
		registerDispatcherServlet(servletContext) ;
	}
	
	private void registerListener(final ServletContext servletContext) {
		AnnotationConfigWebApplicationContext rootContext = createContext(InfrastructureContextConfiguration.class, TestDataContextConfiguration.class);
		servletContext.addListener(new ContextLoaderListener(rootContext));
	}
	
	private void registerDispatcherServlet(final ServletContext servletContext) {
		WebApplicationContext dispatcherContext = createContext(WebMvcContextConfiguration.class);
		DispatcherServlet dispatcherServlet = new DispatcherServlet(dispatcherContext);
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER_SERVLET_NAME, dispatcherServlet);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}

	private AnnotationConfigWebApplicationContext createContext(final Class<?>... annotatedClasses) {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(annotatedClasses);
		return context;
	}
	
}
