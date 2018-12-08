package com.newsaggregator.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.newsaggregator.models.NewsSnippet;

import org.json.*;

@Component
public class HeadlineFetchUtil {


	
	//The 2-letter ISO 3166-1 code of the country you want to get headlines for. 
	//Possible options: ae ar at au be bg br ca ch cn co cu cz de eg fr gb gr hk hu id ie il
	//in it jp kr lt lv ma mx my ng nl no nz ph pl pt ro rs ru sa se sg si sk th tr tw ua us ve za .
	//Note: you can't mix this param with the sources param.
	public  List<NewsSnippet> fetchHeadline(String country) throws JSONException
	{
		JSONObject jsonObj = null;

		final String uri = "https://newsapi.org/v2/top-headlines?country=en&apiKey=65c90f5e609b434ab94f2d5e572dd67e";

	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
	    try {
			jsonObj = new JSONObject(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    List<NewsSnippet> newsList = new ArrayList<NewsSnippet>();
	    JSONArray articles = (JSONArray) jsonObj.get("articles");
	    for(int i=0;i<articles.length();i++)
	    {
			NewsSnippet ns=  new NewsSnippet();

	    	JSONObject article =  (JSONObject) articles.get(i);
	    	try {
	    	Object hl = (Object) article.get("title");
	    	Object iu = (Object) article.get("urlToImage");
	    	Object su = (Object) article.get("description");
	    	Object ur = (Object) article.get("url");
	    	
	    	
	    	if(JSONObject.NULL.equals(hl))
	    	{
	    		continue;
	    	}
	    	else {
	    		System.out.println((String)hl);
	    			ns.setHeadline((String)hl);

	    	}
	    		ns.setSummary((String)su);
	    		

	    		String imageUrl = (String)iu;
	    		ns.setImage_url(imageUrl);

	    		String Url = (String)ur;
	    		ns.setSource_link(Url);
	    	String pubTime = (String)article.get("publishedAt");
	    	pubTime = pubTime.split("T")[0];
	    	
	        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-mm-dd");
	        Date pubDate=formatter1.parse(pubTime);  

	        
	    	
	    	ns.setPublish_time(pubDate);
	    	newsList.add(ns);
	    	
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    	
	    	
	    }    
	    
	    
		return newsList;
	}
	public  List<NewsSnippet> fetchScience(String country) throws JSONException
	{
		JSONObject jsonObj = null;

		final String uri = "https://newsapi.org/v2/top-headlines?country=us&category=science&apiKey=65c90f5e609b434ab94f2d5e572dd67e";

	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
	    try {
			jsonObj = new JSONObject(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    List<NewsSnippet> newsList = new ArrayList<NewsSnippet>();
	    JSONArray articles = (JSONArray) jsonObj.get("articles");
	    for(int i=0;i<articles.length();i++)
	    {
			NewsSnippet ns=  new NewsSnippet();

	    	JSONObject article =  (JSONObject) articles.get(i);
	    	try {
	    	Object hl = (Object) article.get("title");
	    	Object iu = (Object) article.get("urlToImage");
	    	Object su = (Object) article.get("description");
	    	Object ur = (Object) article.get("url");
	    	
	    	
	    	if(JSONObject.NULL.equals(hl))
	    	{
	    		continue;
	    	}
	    	else {
	    		System.out.println((String)hl);
	    			ns.setHeadline((String)hl);

	    	}
	    		ns.setSummary((String)su);
	    		

	    		String imageUrl = (String)iu;
	    		ns.setImage_url(imageUrl);

	    		String Url = (String)ur;
	    		ns.setSource_link(Url);
	    	String pubTime = (String)article.get("publishedAt");
	    	pubTime = pubTime.split("T")[0];
	    	
	        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-mm-dd");
	        Date pubDate=formatter1.parse(pubTime);  

	        
	    	ns.setCategory("Science");
	    	ns.setPublish_time(pubDate);
	    	newsList.add(ns);
	    	
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    	
	    	
	    }    
	    
	    
		return newsList;
	}

}
