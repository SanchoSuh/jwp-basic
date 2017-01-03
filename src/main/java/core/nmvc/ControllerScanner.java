package core.nmvc;

import java.util.Set;

import org.reflections.Reflections;

public class ControllerScanner {
	private Reflections reflections;
	
	public ControllerScanner(Reflections reflections) {
		this.reflections = reflections;
	}
	
	public Set<Class<?>> getControllers(Class<?> annotation) {
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith((Class)annotation);
		
		return annotated;
	}
}

