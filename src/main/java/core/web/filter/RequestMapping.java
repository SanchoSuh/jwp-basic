package core.web.filter;

import java.util.HashMap;
import java.util.Map;

import next.controller.Controller;
import next.controller.ForwardController;
import next.controller.HomeController;
import next.controller.ListUserController;

public class RequestMapping {
	private static Map<String, Controller> requestMap = new HashMap<String, Controller>(); 
	
	static {
		requestMap.put("/",  new HomeController());
		requestMap.put("/users", new ListUserController());
		requestMap.put("/users/loginForm", new ForwardController("/user/login.jsp"));
	}
	
	public static Controller getController(String requestUrl) {
		return requestMap.get(requestUrl);
	}
}
