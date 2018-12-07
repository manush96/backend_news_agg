package com.newsaggregator.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsaggregator.models.NewsSnippet;
import com.newsaggregator.models.User;
import com.newsaggregator.repositories.UserRepository;

@RestController
@CrossOrigin(origins="http://localhost:4200", allowCredentials="true", allowedHeaders = "*")
public class UserServices {

	@Autowired
	UserRepository userRepository;
	
	
	@GetMapping("api/myfeed")
	public List<NewsSnippet> getNewsFeed(@RequestParam int userId)
	{
		return  null;
	}
	
	
	
	@PostMapping("api/registerUser")
	public boolean userRegistration(@RequestBody User user)
	{
		return false;
	}
	
}
