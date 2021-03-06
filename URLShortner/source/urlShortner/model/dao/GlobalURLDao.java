package model.dao;
import java.util.List;
import java.util.HashMap;

import model.dto.*;

public interface GlobalURLDao {
	public String addNewValueToGlobalURLList(String longUrl);
	public UrlMappingList getshortURL(String longUrl);
	public UrlMappingList getLongURL(String shortUrl);
	public UrlMappingList getVisitCountList(String shortUrl);
	// passing all user shorturls to get only the needed visit count and returning to Ui as Map
	public HashMap<String,Integer> getAllVisitCountMap(List<UserUrl> userurl);
	public void addURLVisitCount(String shortUrl);
	public Boolean shortUrlexists(String shortUrl);
    public String getoriLongURL(String shortUrl);
	public String shortenUrl(String longUrl);
}
