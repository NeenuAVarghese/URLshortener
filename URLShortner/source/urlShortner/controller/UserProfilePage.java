package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import formObjects.LongToShortForm;
import model.dao.*;
import model.dto.*;

@Controller
public class UserProfilePage{
	
	@Inject private GlobalURLDao globalurlDao;

	@Inject private UserURLDao userurlDao;

	@RequestMapping("/logout")
	public View logout(HttpServletRequest request)
			throws ServletException, IOException
	{
		System.out.println("logout");
		HttpSession session = request.getSession();
		session.invalidate();
		return new RedirectView("/home", true);
	}


	@RequestMapping("/shortenURL")
	private View shortenURL(@RequestParam("longUrl") String longUrl , HttpServletRequest request)
			throws ServletException, IOException
	{	
		//Todo - Need to handle per user longurl
		//Todo - Handle if null in URL/
		/*
		 * 1. Get all session Details - Long URL, and Username
		 * 2. Check if Url is null; if null send error // may be do the check in UI itself *******************
		 * 3. Check if shortened URL already exists
		 * 		YES:
		 * 		Get shortened URL 
		 *      Add shortened URL and Long URl to UserUrlList
		 *  NO:
		 *  	Create Shortened URL
		 *  	Add shortened URL to and Long URL to UserUrList
		 *  	Add URL to Global urlCount and initialize it to 0
		 *  	Add URL to Global urlMapping
		 */
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		UrlMappingList urlList = globalurlDao.getshortURL(longUrl);
		if(urlList == null){
			String shortUrl = globalurlDao.addNewValueToGlobalURLList(longUrl);
			if(shortUrl != null){
				userurlDao.addUrlToUserList(username, shortUrl, longUrl);
			}
			else{
				System.out.println("Something went wrong in processing the long URL");
			}
		}
		else{
			userurlDao.addUrlToUserList(username, urlList.getShortUrl(), longUrl);
		}
		return new RedirectView("/userprofile", true);
	}
	
	
	@RequestMapping("/userprofile")
	private String loadPage(Map<String, Object> model, HttpServletRequest request)
	{
		
		model.put("longToShortForm", new LongToShortForm());
		/*
		 * 1. Check if UserUrlList exists for current user
		 * 	YES:
		 * 		a. Get the list of shortenedUrl for a user
		 * 		b. Set request Attributes
		 * 			Attributes:
		 * 			b.1 username = Username of Current User
		 * 			b.2 links = UserUrlList  - Map of all long and short urls by user 
		 * 			b.3 linksCount = GLobal Map of url visits count
		 * 		c. Send the control over to the userprofileJsp
		 */
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		request.setAttribute("username", username);
		List<UserUrl> userurl = userurlDao.getUserUrlList(username);
		if(userurl != null){
			request.setAttribute("links", userurl);
		}
		else{
			request.setAttribute("links", null);
		}
		//return hashmap having shorturl,visitcount. and set to linksCount.
		 HashMap<String,Integer> globalUrlCount = globalurlDao.getAllVisitCountMap(userurl);		 
		 request.setAttribute("linksCount", globalUrlCount);
		 
		return "userprofile";
	}


	@RequestMapping(value="/delete", method=RequestMethod.POST)
	private View deleteUrlFromUserList (@RequestParam("urlToRemove") String urlToRemove, HttpServletRequest request)
			throws ServletException, IOException{
		/*

		 * a. Get the list object of shortenedUrl for a user
		 * b. Delete entry from url list object
		 * c.Send the control over to the userprofileJsp
		 */
		System.out.println("In delete");
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		userurlDao.deleteUserListValue(username, urlToRemove);
		return new RedirectView("/userprofile", true);
	}

}