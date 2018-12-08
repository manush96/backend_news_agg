package com.newsaggregator.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsaggregator.daos.NewsSnippetDao;
import com.newsaggregator.models.NewsSnippet;
import com.newsaggregator.models.User;
import com.newsaggregator.repositories.NewsSnippetRepository;
import com.newsaggregator.repositories.UserRepository;

@RestController
@CrossOrigin(origins="http://localhost:4200", allowCredentials="true", allowedHeaders = "*")
public class NewsServices {

	
	@Autowired
	NewsSnippetRepository newsSnippetRepository;
	@Autowired
	NewsSnippetDao newsSnippetDao;
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/api/newshome")
	public List<NewsSnippet> getNewsHome()
	{
		newsSnippetDao.fetchAndInsertHeadlines();
		List<NewsSnippet> a = (List<NewsSnippet>) newsSnippetRepository.findAll();
		return a;
	}
	@GetMapping("/api/login")
	public List<String> userLogin(@RequestParam String username,@RequestParam String password)
	{
		List<String> resList = new ArrayList<String>();
		
		User user = userRepository.findUserByUsername(username).get(0);
		if(user.getUsername()== null)
		{
			resList.add("false");
			return resList;
		}
		else if(user.getPassword().equals(password))
		{
			resList.add("true");
			return resList;
		}
		else
		{
			resList.add("false");
			return resList;
		}
	}
	@GetMapping("/api/admin-login")
	public List<String> adminLogin(@RequestParam String username,@RequestParam String password)
	{
		List<String> resList = new ArrayList<String>();
		
		User user = userRepository.findUserByUsername(username).get(0);
		if(user.getUsername()== null)
		{
			resList.add("false");
			return resList;
		}
		else if(user.getPassword().equals(password))
		{
			resList.add("true");
			return resList;
		}
		else
		{
			resList.add("false");
			return resList;
		}
	}
	@GetMapping("/api/sportshome")
	public List<NewsSnippet> getSportsHome()
	{
		newsSnippetDao.fetchAndInsertSports();
		List<NewsSnippet> a = (List<NewsSnippet>) newsSnippetRepository.findNewsSnippetByCategory("Sport");
		return a;
	}
	@GetMapping("/api/entertainmenthome")
	public List<NewsSnippet> getEntertainmentHome()
	{
		newsSnippetDao.fetchAndInsertEntertainment();
		List<NewsSnippet> a = (List<NewsSnippet>) newsSnippetRepository.findNewsSnippetByCategory("Entertainment");
		return a;
	}
	@GetMapping("/api/sciencehome")
	public List<NewsSnippet> getSpringHome()
	{
		newsSnippetDao.fetchAndInsertScience();
		List<NewsSnippet> a = (List<NewsSnippet>) newsSnippetRepository.findNewsSnippetByCategory("Science");
		return a;
	}
}
