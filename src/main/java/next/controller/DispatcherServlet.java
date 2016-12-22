package next.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.web.filter.RequestMapping;

@WebServlet(name="dispatcher", urlPatterns="/", loadOnStartup=1)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("In DispatcherServlet");
		log.debug(req.getRequestURI());
		
		Controller controller = RequestMapping.getController(req.getRequestURI());
		if(controller == null)
			log.debug("Null returned for Controller");
		
		try {
			String returnUrl = controller.execute(req, resp);
			log.debug(returnUrl);
			
			if(returnUrl.startsWith("redirect:/")) {
				int index = returnUrl.indexOf(":");	
				String subReturnUrl = returnUrl.substring(index+1);
				log.debug(subReturnUrl);
				resp.sendRedirect(subReturnUrl);
			} else {
				RequestDispatcher rd = req.getRequestDispatcher(returnUrl);
				rd.forward(req, resp);
			}
									    
		} catch(Exception e) {
			log.debug("controller.execute() exception");
		}
	}
	
	
}
