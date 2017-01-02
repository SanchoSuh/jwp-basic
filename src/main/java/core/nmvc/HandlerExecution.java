package core.nmvc;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;

public class HandlerExecution {
	
	private Object declaredObject;
	private Method method;
	
	public HandlerExecution(Object object, Method method) {
		this.declaredObject = object;
		this.method = method;
	}
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
        	return (ModelAndView) method.invoke(declaredObject, request, response);
        } catch(Exception e) {}
    	
    	return null;
    }
}
