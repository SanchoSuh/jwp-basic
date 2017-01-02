package core.nmvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.ref.Junit4Test;
import core.ref.MyTest;

public class AnnotationHandlerMapping {
    private Object[] basePackage;
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
    	   	
    	Reflections reflections = new Reflections(this.basePackage);
    	Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
    	
    	logger.debug(String.valueOf(annotated.size()));
    	
    	Map<Class<?>, Object> map = new HashMap<>();
    		
    	try {
	    	for(Iterator it = annotated.iterator() ; it.hasNext() ; ) {
	    		Class clazz = (Class)it.next();
	    		clazz.newInstance();
		    	
	    		//map.put(key, value)
		    	logger.debug(clazz.getName());
		    	
		    	Method[] methods = clazz.getDeclaredMethods();
		    	for(Method method : methods) {
		    		if(method.isAnnotationPresent(RequestMapping.class)) {
		    			RequestMapping rm = method.getAnnotation(RequestMapping.class);
		    			logger.debug("value : " + rm.value() + ", method : " + rm.method());
		    			logger.debug(method.getName());
		    			
		    			HandlerKey handlerKey = createHandlerKey(rm);
		    			
		    			handlerExecutions.put(handlerKey, new HandlerExecution(clazz.newInstance(), method));
		    		}
		        		
		    	}
	    	}
    	}catch(Exception e) {
    		logger.debug("initialize() exception");
    	}
        
    }
    
    public HandlerKey createHandlerKey(RequestMapping rm){
    	return new HandlerKey(rm.value(),rm.method());
    } 

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }
}
