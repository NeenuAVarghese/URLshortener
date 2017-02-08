package model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import model.dto.User;
import model.dto.UserUrl;
import model.mapper.UserMapper;
import model.mapper.UserUrlMapper;

@Service
public class UserURLDaoImpl implements UserURLDao{
	/*
	 * This class contains the implementation for UserUrlDao Interface
	 */
	@Inject private BasicDataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;


	@Autowired
	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void addUrlToUserList(String username, String shortUrl, String longUrl){
		/*
		 * 1. Get user ID
		 * 2. Check if url already exists 
		 * 		YES:
		 * 			return
		 * 		NO:
		 * 			Add new value
		 * 			Format: 
		 * 			UserID, ShortUrl, LongURL
		 */
		int userid = getUserId(username);
		/////////if-else for userid == 0
		if(!checkIfUrlExistsForUser(userid, shortUrl)){
			String SQL = "insert into userUrlList (userid, shortUrl, longUrl) values (?, ?, ?)";
			Object[] params = new Object[] { userid, shortUrl, longUrl };
			try{
				jdbcTemplateObject.update( SQL, params);
				System.out.println("Added to userUrl list");
			}
			catch(Exception e){
				System.out.println("Exception occured when adding to user list");
				/*Put Stack trace into logger file*/
			}
		}
		else{
			return;
		}


	}
	
	@Override
	public void deleteUserListValue(String username, String urlToRemove){
		/*
		 * 1. Get user ID
		 * 2. Check if url already exists 
		 * 		YES:
		 * 			Delete value from URL
		 * 		NO:
		 * 			return
		 */
		System.out.println(" In delete -> db");
		int userid = getUserId(username);
		/////////if-else for userid == 0
		if(checkIfUrlExistsForUser(userid, urlToRemove)){
			String SQL = "delete from userUrlList where userid = ? and shortUrl = ?";
			System.out.println("Executing deletie");
			Object[] params = new Object[] { userid, urlToRemove };
			try{
				jdbcTemplateObject.update( SQL, params);
				System.out.println("Removed from User Url List");
			}
			catch(Exception e){
				System.out.println("Exception occured when deleting from user list"+e);
				/*Put Stack trace into logger file*/
			}
		}
		else{
			return;
		}
	}

	@Override
	public int getUserId(String username){
		String SQL = "select userid from Users where username = (?)";
		Object[] params = new Object[] { username };
		UserMapper userMapper = new UserMapper();

		try{
			User user = this.jdbcTemplateObject.queryForObject(SQL, params, userMapper);
			return user.getID();
		}
		catch(EmptyResultDataAccessException e){
			System.out.println("Could not find User ID ofr a user");
			return 0;
		}
		catch(Exception e){
			System.out.println("Exception occured while finding for UserID");
			/*Put Stack trace into logger file */
			System.out.println(e);
			return 0;
		}
	}

	@Override
	public List<UserUrl> getUserUrlList(String username){
		int userid = getUserId(username);
		///////if-else for userid = 0
		String SQL = "select shortUrl, longUrl from UserUrlList where userid = (?)";
		Object[] params = new Object[] { userid };
		UserUrlMapper userUrlMapper = new UserUrlMapper();
		try{
			List<UserUrl> userUrls = this.jdbcTemplateObject.query(SQL, params, userUrlMapper);
			return userUrls;
		}
		catch(Exception e){
			System.out.println("Error in retrieving user Url list");
			return null;
		}
	}

	@Override
	public boolean checkIfUrlExistsForUser(int userid, String shortUrl){

		String SQL = "select shortUrl from UserUrlList where userid = (?)";
		Object[] params = new Object[] { userid };
		try{
			List<String> shortUrls  = this.jdbcTemplateObject.queryForList(SQL, params, String.class);

			if(shortUrls!= null)  {
				for(String url: shortUrls)  {
					if(shortUrl.equals(url)){
						return true;
					}
				}
			}
			else{
				return false;
			}
		}
		catch(Exception e){
			System.out.println("Error in retrieving user Url list");
			return false;
		}
		return false;
	}


}


/*
public UserUrlList(String shortUrl, String longUrl){
	this.userUrlList.put(shortUrl, longUrl);
}

public void addNewUserListValue(String shortUrl, String longUrl){
	this.userUrlList.put(shortUrl, longUrl);

}

public boolean deleteUserListValue(String urlToRemove){

	for(Iterator<Map.Entry<String, String>> it = userUrlList.entrySet().iterator(); it.hasNext(); ) {
		Map.Entry<String, String> entry = it.next();
		if(entry.getKey().equals(urlToRemove)) {
			it.remove();
			return true;
		}
	}
	return false;	
}

public Map<String, String> getUserUrlList(){
	return this.userUrlList;
}
 */
