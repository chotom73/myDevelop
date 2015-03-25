package com.apress.prospringmvc.bookstore.web.config;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.apress.prospringmvc.bookstore.domain.BookSearchCriteria;

public class RestfulArgumentResolver implements WebArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter param,NativeWebRequest req) throws Exception {
		// TODO Auto-generated method stub
		Class type = param.getParameterType();
   Method method = param.getMethod();
   
   System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4");

   if (type.equals(BookSearchCriteria.class)) {
	   System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	   System.out.println("type : " + type.getName());
	   System.out.println("method : " + method.getName());
	   System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
   } else if (type.equals(String.class)) {
	   HttpServletRequest request = (HttpServletRequest) req.getNativeRequest();

     String name = param.getParameterName();
     Object value = request.getParameter(name);

     if (value != null) {
    	 return value;
            }
        }

   return UNRESOLVED;
	}

}
