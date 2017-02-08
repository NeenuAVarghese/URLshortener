package controller;

import model.dao.AuthDao;

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
import formObjects.SignUpForm;
import model.dao.AuthDao;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import model.dto.User;
/*
 * This servlet handles requests and responses to/from the Home Page for SignUP/Login Requests
 */

@Controller
public class LoginController
{

 @Inject private AuthDao authentication;

	
	@RequestMapping(value="/signUp", method=RequestMethod.POST)
	public View createUser(@ModelAttribute("signUpForm") SignUpForm form, HttpServletRequest request)
    {
		String username = form.getNew_username();
		String password = form.getNew_password();
		
		HttpSession session = request.getSession();
		
		
		if(username==null || password == null ||!authentication.signupUsr(username, password)){
			session.setAttribute("signupFailed", "true");
			return new RedirectView("/home", true);
		}
		else{
			session.setAttribute("username", username);
			request.changeSessionId();
			System.out.println("success");
			
			return new RedirectView("/userprofile", true);
		}
    }
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public View createUser(@ModelAttribute("loginForm") LoginForm form, HttpServletRequest request)
    {
		String username = form.getusername();
		String password = form.getPassword();

		HttpSession session = request.getSession();
		
		if(username!=null && password != null){
			User user = authentication.loginUser(username, password);
			if(user == null){
				session.setAttribute("loginFailed", "Enter valid detials !");
				return new RedirectView("/home", true);
			}
			else{
				if(password.equals(user.getPassword())){
					session.setAttribute("username", username);
					request.changeSessionId();
					return new RedirectView("/userprofile", true);
				}
				else{
					session.setAttribute("loginFailed", "Username or Password is incorrect !");
					return new RedirectView("/home", true);
				}
			}
		}
		session.setAttribute("loginFailed", "Enter valid detials !");
		return new RedirectView("/home", true);
    }
	
	
}
