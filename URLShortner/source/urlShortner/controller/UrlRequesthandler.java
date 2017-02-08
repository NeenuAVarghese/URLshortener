package controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import model.dao.GlobalURLDao;
import model.dao.UserURLDao;
import model.dto.UrlMappingList;

/*
 * This servlet handles requests and responses for all responses for the domainname/short/* URL
 */


@Controller
public class UrlRequesthandler{

	@Inject private GlobalURLDao globalurlDao;
	
	@RequestMapping("/short/*")
	protected View urlRedirect(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		/*
		 * 1. Get URL from request parameters
		 * 2. Append http://localhost:8080 to the URL
		 * 3. Check if the short URL exists in DB
		 * 		3.a YES
		 * 			1. Get the corresponding long url
		 * 			2. Add the count for URL vists
		 * 			3. Try to make a coonection the the Long/original URL
		 * 			4. Get response code
		 * 			5. Check if Response Code is 22
		 * 				YES:
		 * 				a. Redirect to the Original URL
		 * 				No:
		 * 				a. Redirect to Error Page
		 * 		3.b No
		 * 			Redirect to Error Page
		 */
		
		String shortUrl = request.getRequestURI();
		shortUrl = "http://localhost:8080" + shortUrl;
		
		UrlMappingList urlList = globalurlDao.getLongURL(shortUrl);
		if(urlList != null){
			globalurlDao.addURLVisitCount(shortUrl);
			String longURL = urlList.getLongUrl();
			int responseCode = 404;
			try{
				HttpURLConnection huc = (HttpURLConnection) new URL(longURL).openConnection();
				responseCode = huc.getResponseCode();
			}
			catch(Exception e){
				responseCode = 404;
			}

			if (responseCode == 200) {
				//response.sendRedirect(longURL);
				return new RedirectView(longURL, true);
			}
			else{
				return new RedirectView("/errorPage", true);
				
			}
		}else{
		
			return new RedirectView("/errorPage", true);
		}
	}
}