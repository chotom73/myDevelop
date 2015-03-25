package com.apress.prospringmvc.bookstore.web.config;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.apress.prospringmvc.bookstore.annotation.ExternalUserDetails;
import com.apress.prospringmvc.bookstore.domain.BookSearchCriteria;
import com.apress.prospringmvc.bookstore.web.BookstoreWebApplicationInitializer;

import org.springframework.data.domain.PageRequest;

public class UserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(BookstoreWebApplicationInitializer.class);

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		System.out.println("#######################################################################################");
		System.out.println("parameter.getParameterName() :" + parameter.getParameterName());
		System.out.println("parameter.getParameterAnnotation() :" + parameter.getParameterType().toString());
		return (parameter.getParameterAnnotation(ExternalUserDetails.class) != null);
		//return false;
	}

	@Override
	public Object resolveArgument(MethodParameter param,
																					ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
																					WebDataBinderFactory binderFactory) throws Exception {
		
		if (BookSearchCriteria.class.isAssignableFrom(param.getParameterType())) {
		//if (ClassUtils.is.isAssignableValue(param.getParameterType(), BookSearchCriteria.class)) {
			HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
			
			BookSearchCriteria bookSearchCriteria = new BookSearchCriteria();
			bookSearchCriteria.setTitle(request.getParameter("title"));
			
			return bookSearchCriteria;
		} else {
		
			Map<String, Object> commandMap = new HashMap<String, Object>();
			HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();			
			Enumeration<?> enumeration = request.getParameterNames();
	 
			while(enumeration.hasMoreElements()){
				String key = (String) enumeration.nextElement();
				String[] values = request.getParameterValues(key);
				if(values!=null){
					commandMap.put(key, (values.length > 1) ? values:values[0] );
				}
			}
			return commandMap;
		}
	}
}
