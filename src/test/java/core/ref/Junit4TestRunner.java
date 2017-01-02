package core.ref;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Test;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        
        /*
        Annotation[] ants = clazz.getAnnotations();
        
        for(Annotation ant : ants) {
        	System.out.println(ant.toString());
        	if(ant.toString().contains("Test"))
        		
        }*/
        
        Method[] methods = clazz.getMethods();
        
        for(Method method : methods) {
        	if(method.isAnnotationPresent(MyTest.class))
        		method.invoke(clazz.newInstance());
        }
    }
}
