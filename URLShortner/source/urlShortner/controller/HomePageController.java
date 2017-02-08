package controller;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import formObjects.LoginForm;
import formObjects.ShortToLongForm;
import formObjects.SignUpForm;
import model.dao.GlobalURLDao;



/*
 * This servlet handles requests and responses to/from the Home Page
 */



@Controller
public class HomePageController{
		
	@Inject private GlobalURLDao globalurlDao;


	@RequestMapping(value="/getLongUrl", method=RequestMethod.GET)
	public View getLongURL(@ModelAttribute("shortToLong") ShortToLongForm form, HttpServletRequest request){
		/*
		 *1. Get Short URL from the request parameter
		 *2. Validate with the domain name
		 *	2.a Check if the entered SHort URL exists in the DB
		 *		YES:
		 *		1. Get the Long URL from DB
		 *		2. Set request Attribute 'longUrl' to be the returned value
		 *		3. Forward the request to home.jsp 
		 *		NO:
		 *		1. Set the value of returned parameter to undefined
		 *		2. Forward the request to home.jsp 
		 *3. If URL not validated with the domain name
		 *		a. Set the value of returned parameter to undefined
		 *		b. Forward the request to home.jsp 
		 */
		String shUrl = form.getShortUrl();
		HttpSession session = request.getSession();

		if(shUrl.startsWith("http://localhost:8080/URLShortner/")){
			if(globalurlDao.shortUrlexists(shUrl)){
				String longUrl = globalurlDao.getoriLongURL(shUrl);
				session.setAttribute("longUrl", longUrl);
				System.out.println("longurl:"+longUrl);
			}
			else{
				session.setAttribute("longUrl", "undefined");
				System.out.println("longurl: undefined");
			}		
		}
		else{
			session.setAttribute("longUrl", "undefined");
		}	
		return new RedirectView("/home", true);
	}
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
	 public String home(Map<String, Object> model)
	    {
			model.put("signUpForm", new SignUpForm());
			model.put("loginForm", new LoginForm());
			model.put("shortToLong", new ShortToLongForm());
	        return "home";
	    }

}
