package com.newsaggregator.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsaggregator.daos.NewsSnippetDao;
import com.newsaggregator.models.NewsSnippet;
import com.newsaggregator.models.User;
import com.newsaggregator.repositories.NewsSnippetRepository;
import com.newsaggregator.repositories.UserRepository;

@RestController
@CrossOrigin(origins="http://localhost:4200",allowCredentials="true",allowedHeaders = "*")
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
    	
    	
    @GetMapping("/api/user/findOne")
	public User findOne(@RequestParam String username)
	{
		User user = new User();
		user = (User) userRepository.findUserByUsername(username).get(0);
		
		
		
		return user;
	}
	@GetMapping("/api/admin-login")
	public List<String> agencyLogin(@RequestParam String username,@RequestParam String password)
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
			if(user.getdType()=="agency") {
				resList.add("true");
				return resList;
			}
			else {
				resList.add("false");
				return resList;
			}
			
		}
		else
		{
			resList.add("false");
			return resList;
		}
	}
	@PostMapping(path = "/api/registration",consumes = "application/json")
	public void registration(@RequestBody Object user)
	{
		System.out.println(user.getClass()); 
	
		System.out.println(((java.util.LinkedHashMap) user).get("firstname"));
		User user1 = new User();
		user1.setFirstname(((java.util.LinkedHashMap) user).get("firstname").toString());
		user1.setLastname(((java.util.LinkedHashMap) user).get("lastname").toString());
		user1.setUsername(((java.util.LinkedHashMap) user).get("username").toString());
		user1.setPassword(((java.util.LinkedHashMap) user).get("password").toString());
		user1.setPreference(((java.util.LinkedHashMap) user).get("preference").toString());
		user1.setEmail(((java.util.LinkedHashMap) user).get("email").toString());
		
		
		userRepository.save(user1);
	}
	@GetMapping("/api/findallusers")
	public List<User> adminSearch()
	{
		List<User> users = (List<User>)userRepository.findAll();
		
		return users;
	}
	@GetMapping("/api/deleteuser")
	public void deleteUser(@RequestParam int id)
	{
		Optional<User> user = userRepository.findById(id);
	if(user.isPresent())
		userRepository.delete(user.get());
	}
	@GetMapping("/api/myfeed")
	public List<NewsSnippet> myfeed(@RequestParam String username)
	{
		User user = userRepository.findUserByUsername(username).get(0);
		List<String> s = parsePreference(user.getPreference());
		List<NewsSnippet> x = new ArrayList<>();
		for (int i = 0; i < s.size(); i++) {
			switch (s.get(i)) {
			case "Sport":
				newsSnippetDao.fetchAndInsertSports();
				List<NewsSnippet> a = (List<NewsSnippet>) newsSnippetRepository.findNewsSnippetByCategory("Sport");
				x.addAll(a);
				break;
			case "Entertainment":
				newsSnippetDao.fetchAndInsertEntertainment();
				a = (List<NewsSnippet>) newsSnippetRepository.findNewsSnippetByCategory("Entertainment");
				x.addAll(a);
				break;
			case "Science":
				newsSnippetDao.fetchAndInsertScience();
				a = (List<NewsSnippet>) newsSnippetRepository.findNewsSnippetByCategory("Science");
				x.addAll(a);
				break;
			default:
				break;
			}
			
		}
		return x;
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
	@GetMapping("/api/busniesshome")
	public List<NewsSnippet> getBusniessHome()
	{
		newsSnippetDao.fetchAndInsertBusniess();
		List<NewsSnippet> a = (List<NewsSnippet>) newsSnippetRepository.findNewsSnippetByCategory("Busniess");
		return a;
	}
	private List<String> parsePreference(String s) {
	String sa[] = s.split(",");
	return Arrays.asList(sa);
	}
//	private User parseJSON(String s) {
//		s = s.substring(1, s.length()-1);
//		String s1[] = s.split(", ");
//		for(int i = 0,)
//		return null;
//		
//	}
}
