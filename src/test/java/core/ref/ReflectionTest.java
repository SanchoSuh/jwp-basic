package core.ref;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());
        
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
        	logger.debug("field : " + field.getName() + ", type :" + field.getType());
        }
        
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method : methods) {
        	logger.debug("method : " + method.getName() + ", modifier : " + method.getModifiers());
        }
        
        
    }
    
    @Test
    public void newInstanceWithConstructorArgs() throws Exception {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
        
        Constructor[] cons = clazz.getDeclaredConstructors();
        for(Constructor con : cons) {
        	User user = (User)con.newInstance("Sancho", "11", "inyong", "22@hanmail.con");
        }
    }
    
    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());
        
        try {
        	Field stringField = clazz.getDeclaredField("name");
        	stringField.setAccessible(true);
        	Student student = new Student();
        	stringField.set(student, "Sancho");
        	logger.debug(student.getName());
        }catch(Exception e) {}
        
        
    }
}
