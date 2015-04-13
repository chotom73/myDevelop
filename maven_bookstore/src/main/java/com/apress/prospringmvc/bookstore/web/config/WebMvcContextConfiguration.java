package com.apress.prospringmvc.bookstore.web.config;

import java.util.List;

import om.apress.prospringmvc.bookstore.converter.StringToEntityConverterTest;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.apress.prospringmvc.bookstore.domain.Cart;
import com.apress.prospringmvc.bookstore.domain.Category;
import com.apress.prospringmvc.bookstore.web.interceptor.CommonDataInterceptor;
import com.apress.prospringmvc.bookstore.web.interceptor.SecurityHandlerInterceptor;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.apress.prospringmvc.bookstore" })
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter { //WebMvcConfigurationSupport {
	
	public RequestMappingHandlerAdapter rquestMappingHandlerAdapter() {
		return null;
	}
	
	// Interceptor 등록 Method Overried (추가 등록할 Interceptor 여기에)
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration registration;
		registration = registry.addInterceptor(localeChangeInterceptor());
		//registration.addPathPatterns("/customers/**");
		registry.addWebRequestInterceptor(commonDataInterceptor());
		registry.addInterceptor(new SecurityHandlerInterceptor()).addPathPatterns("/customer/account", "/cart/checkout");
	}
	
	@Bean
	public HandlerInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}
	
	@Bean
	public WebRequestInterceptor commonDataInterceptor() {
		return new CommonDataInterceptor();
	}
	
	/*
	@Override
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		// TODO Auto-generated method stub
		RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
		handlerMapping.setInterceptors(getAllInterceptor());
		return handlerMapping;
	}	*/
	
	
	/*
	@Override
	@Bean
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter();
		ConfigurableWebBindingInitializer webBindingInitializer = (ConfigurableWebBindingInitializer)adapter.getWebBindingInitializer();
		webBindingInitializer.setDirectFieldAccess(true);
		
		return adapter;
	}
	*/
	
	/*
	private Object[] getAllInterceptor() {
		// TODO Auto-generated method stub
		return null;
	}
	*/


	@Bean(name = "userHandlerMethodArgumentResolver")
	public UserHandlerMethodArgumentResolver userHandlerMethodArgumentResolver() {
	    return new UserHandlerMethodArgumentResolver();
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userHandlerMethodArgumentResolver());
	}

	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**/*").addResourceLocations("classpath:/META-INF/web-resources/");
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/index.htm").setViewName("index");
	}
	
	@Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");		
		return viewResolver;
	}
	
	@Override
	public void addFormatters(final FormatterRegistry registry) {
			registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
			registry.addConverter(categoryConverter());
		}
	
	@Bean
	public StringToEntityConverterTest categoryConverter() {
		return new StringToEntityConverterTest(Category.class);
    }
	

	
	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:/messages");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}
	
	
	@Bean
	@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public Cart cart() {
		return new Cart();
	}
	
}
