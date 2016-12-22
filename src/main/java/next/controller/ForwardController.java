package next.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForwardController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(ForwardController.class);
	String forwardUrl;
	
	public ForwardController(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		log.debug("ForwardController : " + forwardUrl);
		return forwardUrl;
	}
}
