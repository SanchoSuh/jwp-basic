package core.ref;

import java.lang.reflect.Method;

import org.junit.Test;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method : methods) {
        	if(isTest(method.getName()))
        		method.invoke(clazz.newInstance());
        }
        
    }
    
    public boolean isTest(String methodName) {
    	return methodName.startsWith("test");
    }
}
