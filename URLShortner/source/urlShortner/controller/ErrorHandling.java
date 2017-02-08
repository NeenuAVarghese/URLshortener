package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/*
 * Unhandled requests will be redirected to this servlet
 */

@Controller
public class ErrorHandling extends HttpServlet{

	
	@RequestMapping("/errorPage")
	protected String errorPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		/*
		 * Feferenced from: https://www.tutorialspoint.com/servlets/servlets-exception-handling.htm
		 * 
		 */
		HttpSession session = request.getSession();

		/*
		 * Get Error Codes in a Variable
		 */
		Throwable throwable = (Throwable)
				request.getAttribute("javax.servlet.error.exception");

		Integer statusCode = (Integer)
				request.getAttribute("javax.servlet.error.status_code");

		String servletName = (String)
				request.getAttribute("javax.servlet.error.servlet_name");
		if (servletName == null){
			servletName = "Unknown";
		}

		String requestUri = (String)
				request.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null){
			requestUri = "Unknown";
		}

		/*
		 * Check error cause
		 * Sent appropriate request attribute 
		 * Forward the request to a JSP
		 */
		if (throwable == null && statusCode == null){
			request.setAttribute("errorMessage", "Error - Information is missing");
		}else if (statusCode != null){
			request.setAttribute("errorMessage", "Status Code: "+ statusCode.toString());
		}else{
			String errMessage = "For the Servlet: "+servletName +", Following exception has occured: " +throwable.getClass().getName();
			request.setAttribute("errorMessage", errMessage);
		}
		return "error404";
	}
}
