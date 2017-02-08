package model.mapper;
import model.dto.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserUrlMapper implements RowMapper<UserUrl>{
	public UserUrl mapRow(ResultSet rs, int rowNum) throws SQLException {
		  UserUrl userurl = new UserUrl();
		  
		  
		  try{
			  userurl.setShortUrl(rs.getString("shortUrl"));
	      }
	      catch(Exception e){
	    	  
	      }
	      try{
	    	  userurl.setLongUrl(rs.getString("longUrl"));
	      }
	      catch(Exception e){
	    	  
	      }

	      return userurl;
	   }
}

