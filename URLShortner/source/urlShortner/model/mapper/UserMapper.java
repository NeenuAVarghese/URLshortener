package model.mapper;
import model.dto.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User>{
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
	      User user = new User();
	      Integer userid = new Integer(rs.getInt("userid"));
	      try{
	    	  user.setID(rs.getInt("userid"));
	      }
	      catch(Exception e){
	    	 
	      }
	      try{
	    	  user.setPassword(rs.getString("password"));
	      }
	      catch(Exception e){
	    	  
	      }
	    	  
	      
	      return user;
	   }
}
