package model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import model.mapper.UserMapper;
import model.mapper.ShortUrlMapper;
import model.mapper.LongUrlMapper;
import model.dto.*;
import java.util.HashMap;
import model.mapper.GlobalURLMapper;
import model.mapper.LongUrlMapper;

@Service
public class GlobalURLDaoImpl implements GlobalURLDao{
	@Inject private BasicDataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public String addNewValueToGlobalURLList(String longUrl){
		/*
		 * 1. Process Method for getting new shortURL
		 * 2. Add to global list
		 * 	  Format:
		 * 	  UrlID, shortUrl, longUrl, visitCOunt
		 */
		
		String shortUrl = shortenUrl(longUrl);
		String SQL = "insert into GlobalUrlDB (shortUrl, longUrl, visitCount) values (?, ?, ?)";
		Object[] params = new Object[] { shortUrl, longUrl, 0 };
		try{
			jdbcTemplateObject.update( SQL, params);
			return shortUrl;
		}
		catch(Exception e){
			System.out.println("Exception occured while user tried to shorten URL");
			/*Put Stack trace into logger file*/
			System.out.println(e);
			return null;
		}
	}
	
	@Override
	public UrlMappingList getshortURL(String longUrl){
		String SQL = "select shortUrl from GlobalUrlDB where longUrl =  (?)";
		Object[] params = new Object[] { longUrl };
		ShortUrlMapper mapper = new ShortUrlMapper();
		
		try{
			UrlMappingList urlList = this.jdbcTemplateObject.queryForObject( SQL, params, mapper);
			return urlList;
			
		}
		catch(EmptyResultDataAccessException e){
			System.out.println("Shortened URL does not exists for the entered Long URL");
			return null;
		}
		catch(Exception e){
			System.out.println("Some other Exception");
			return null;
		}
	
	}
	
	@Override
	public UrlMappingList getLongURL(String shortUrl){
		
		String SQL = "select longUrl from GlobalUrlDB where shortUrl =  (?)";
		Object[] params = new Object[] { shortUrl };
		GlobalURLMapper mapper = new GlobalURLMapper();
		try{
			UrlMappingList urlList = this.jdbcTemplateObject.queryForObject( SQL, params, mapper);
			return urlList;
		}catch(EmptyResultDataAccessException e){
			e.printStackTrace();
			return null;
		}catch(Exception e){
			System.out.println("Some other Exception"+e);
			return null;
		}
		
	}
	
	//returns visitCount
	@Override
	public UrlMappingList getVisitCountList(String shortUrl){
		//in progress - some code still needs to be added
		String SQL = "select visitCount from GlobalUrlDB where shortUrl =  (?)";
		Object[] params = new Object[] { shortUrl };
		GlobalURLMapper mapper = new GlobalURLMapper();
		try{
			UrlMappingList globalCountList = this.jdbcTemplateObject.queryForObject( SQL, params, mapper);
			return globalCountList;
		}catch(EmptyResultDataAccessException e){		
				 			e.printStackTrace();
				 			return null;
		}catch(Exception e){
			System.out.println("Some other Exception");
			return null;
		}
		
	}
	
	@Override
	public HashMap<String,Integer> getAllVisitCountMap(List<UserUrl> userurl){		
		 		HashMap<String,Integer> globalUrlCount = new HashMap<>();		
		 				
		 		for(UserUrl userUrlElement: userurl){		
		 			String shorturlTemp = userUrlElement.getShortUrl();		
		 			Integer visitCountTemp = getVisitCountList(shorturlTemp).getVisitCount();		
		 			globalUrlCount.put(shorturlTemp, visitCountTemp);		
		 		}		
		 				
		 		return globalUrlCount;
	}
	
	@Override
	public void addURLVisitCount(String shortUrl){		
		 		/*		
		 		 * get visit count from getVisitCountList in count variable		
		 		 * increment count variable		
		 		 * update visit count in GlobalUrlDB table*/		
		  				  		
		 		int visitCount = getVisitCountList(shortUrl).getVisitCount();		
		 		visitCount++;		
		 		String SQL = "UPDATE GlobalUrlDB SET visitCount = (?) WHERE shortUrl = (?)";		
		 		Object[] params = new Object[] { visitCount, shortUrl };		
		 		try{		
		 			jdbcTemplateObject.update( SQL, params);		
		 		}		
		 		catch(Exception e){		
		 			e.printStackTrace();		
		 		}		
		 		
		  	}
	
	@Override
	public String getoriLongURL(String shortUrl){
		String SQL = "select longUrl from GlobalUrlDB where shortUrl =  (?)";
		System.out.println("sql:"+SQL);
		Object[] params = new Object[] { shortUrl };
		LongUrlMapper mapper = new LongUrlMapper();
		String lUrl="";
		
		try{
			List<UserUrl> userurls = new ArrayList<UserUrl>();
			List<Map<String, Object>> rows = jdbcTemplateObject.queryForList(SQL, params);
			
			for (Map row : rows) {
				UserUrl userUrl = new UserUrl();
				userUrl.setLongUrl(String.valueOf(row.get("longUrl")));
				lUrl=String.valueOf(row.get("longUrl"));
				userurls.add(userUrl);
			}
			System.out.println("long:"+lUrl);
			return lUrl;
			
		}
		catch(EmptyResultDataAccessException e){
			System.out.println("Shortened URL does not exists in the Database");
			return null;
		}
		catch(Exception e){
			System.out.println("Some other Exception");
			return null;
		}
		

	}
	
	@Override
	public String shortenUrl(String longUrl){
		/*
		 * 1. convert longUrl into 36 bit hash value
		 * 2. Take Max bits
		 * 3. Convert to String with base 36 encoding
		 * 4. Return value
		 */
		String encodedUrl = "";
		Integer hashKey = (int) UUID.nameUUIDFromBytes(longUrl.getBytes()).getMostSignificantBits();
		encodedUrl = Integer.toString(hashKey, 36);
		return "http://localhost:8080/URLShortner/short/" +encodedUrl;
	}


	@Override
	public Boolean shortUrlexists(String shortUrl) {
		// TODO Auto-generated method stub
		System.out.println("check if short url exist");
		
		String SQL = "select longUrl from GlobalUrlDB where shortUrl =  (?)";
		Object[] params = new Object[] { shortUrl };
		LongUrlMapper mapper = new LongUrlMapper();
		
		try{
			UrlMappingList urlList = this.jdbcTemplateObject.queryForObject( SQL, params, mapper);			
		}
		catch(EmptyResultDataAccessException e){
			System.out.println("Shortened URL does not exists in the Database");
			return false;
		}
		catch(Exception e){
			System.out.println("Some other Exception");
			return false;
		}
		return true;
	}
}
