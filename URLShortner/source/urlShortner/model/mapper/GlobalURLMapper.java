package model.mapper;
import model.dto.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class GlobalURLMapper implements RowMapper<UrlMappingList>{

	public UrlMappingList mapRow(ResultSet rs, int rowNum) throws SQLException {
		UrlMappingList globalUrlDb = new UrlMappingList();
		globalUrlDb.setVisitCount(rs.getInt("visitCount"));
		//globalUrlDb.setlongUrl(rs.getString("longUrl"));
	      
	    return globalUrlDb;
	}
}