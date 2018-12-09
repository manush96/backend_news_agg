package com.newsaggregator.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsaggregator.daos.NewsSnippetDao;
import com.newsaggregator.models.Advertisement;
import com.newsaggregator.models.Contact;
import com.newsaggregator.models.NewsSnippet;
import com.newsaggregator.models.News_owner;
import com.newsaggregator.models.User;
import com.newsaggregator.repositories.AdvertisementRepository;
import com.newsaggregator.repositories.ContactRepository;
import com.newsaggregator.repositories.NewsSnippetRepository;
import com.newsaggregator.repositories.News_ownerRepository;
import com.newsaggregator.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
public class NewsServices {

	@Autowired
	NewsSnippetRepository newsSnippetRepository;
	@Autowired
	NewsSnippetDao newsSnippetDao;
	@Autowired
	UserRepository userRepository;
	@Autowired
	News_ownerRepository ownerRepository;

	@Autowired
	AdvertisementRepository advertRepository;

	@Autowired
	ContactRepository contactRepository;

	@GetMapping("/api/newshome")
	public List<NewsSnippet> getNewsHome() {
		newsSnippetDao.fetchAndInsertHeadlines();
		List<NewsSnippet> a = (List<NewsSnippet>) newsSnippetRepository.findAll();
		return a;
	}

	// All Login EndPoints
	@GetMapping("/api/login")
	public List<String> userLogin(@RequestParam String username, @RequestParam String password) {
		List<String> resList = new ArrayList<String>();

		User user = userRepository.findUserByUsername(username).get(0);
		if (user.getUsername() == null) {
			resList.add("false");
			return resList;
		} else if (user.getPassword().equals(password)) {
			resList.add("true");
			return resList;
		} else {
			resList.add("false");
			return resList;
		}
	}

	@GetMapping("/api/admin/login")
	public List<String> adminLogin(@RequestParam String username, @RequestParam String password) {

		List<String> resList = new ArrayList<String>();

		User user = userRepository.findUserByUsername(username).get(0);
		if (user.getUsername() == null) {
			resList.add("false");
			return resList;
		} else if (user.getPassword().equals(password)) {
			resList.add("true");
			return resList;
		} else {
			resList.add("false");
			return resList;
		}
	}

	@GetMapping("/api/agency/login")
	public List<String> agencyLogin(@RequestParam String username, @RequestParam String password) {

		List<String> resList = new ArrayList<String>();

		User user = userRepository.findUserByUsername(username).get(0);

		if (user.getUsername() == null) {
			resList.add("false");
			return resList;
		} else if (user.getPassword().equals(password)) {
			if (user.getdType().equals("agency")) {
				resList.add("true");
				return resList;
			} else {
				resList.add("false");
				return resList;
			}

		} else {
			resList.add("false");
			return resList;
		}
	}

	@GetMapping("/api/advert/login")
	public List<String> advertLogin(@RequestParam String username, @RequestParam String password) {

		List<String> resList = new ArrayList<String>();

		User user = userRepository.findUserByUsername(username).get(0);

		if (user.getUsername() == null) {
			resList.add("false");
			return resList;
		} else if (user.getPassword().equals(password)) {
			if (user.getdType().equals("advertiser")) {
				resList.add("true");
				return resList;
			} else {
				resList.add("false");
				return resList;
			}

		} else {
			resList.add("false");
			return resList;
		}
	}
	// End of Login Endpoints

	// All find EndPoints
	@GetMapping("/api/user/findOne")
	public User findOne(@RequestParam String username) {
		User user = new User();
		user = (User) userRepository.findUserByUsername(username).get(0);
		return user;
	}

	@GetMapping("/api/findallusers")
	public List<User> adminSearch() {
		List<User> users = (List<User>) userRepository.findAll();
		return users;
	}

	@GetMapping("/api/agency/findByAgency")
	public List<NewsSnippet> newsSearchByAgency(@RequestParam String username) {
		List<NewsSnippet> c =newsSnippetRepository.getNewsSnippetByAgency(username);
		if(c.size()>0) {
			return c;
		}
		else {
			c = new ArrayList<NewsSnippet>();
			NewsSnippet e = new NewsSnippet();
			e.setHeadline("There is no data here!!");
			c.add(e);
			return c;
		}
	}
	@GetMapping("/api/advert/find")
	public List<Advertisement> findAdvert() {
		List<Advertisement> x  = (List<Advertisement>) advertRepository.findAll();
		if(x.size()>0) {
			List<Advertisement> c  = new ArrayList<>();
			Random rand = new Random();
			for (int j=0;j < 5;j++)
			{
				c.add(x.get(rand.nextInt(x.size())));
			}
			return c;
		}else {
			Advertisement a = new Advertisement();
			a.setTitle("there are no advertisements");
			x.add(a);
			return x;
		}
	}

	// End of Find Endpoints

	// All Insert Endpoints.
	@PostMapping(path = "/api/registration", consumes = "application/json")
	public void registration(@RequestBody Object user) {
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

	// End of Insert Endpoints
	@GetMapping("/api/deleteuser")
	public void deleteUser(@RequestParam int id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent())
			userRepository.delete(user.get());
	}

	@RequestMapping(value = "/api/deleteNews/{id}", method = RequestMethod.DELETE)
	public void deleteNews(@PathVariable("id") int itemId) {
		newsSnippetRepository.deleteById(itemId);
	}

//	@PutMapping("/api/user/update")
//	public void deleteUser(@RequestParam User user)
//	{
//		
//	if(user.isPresent())
//		userRepository.delete(user.get());
//	}
	@GetMapping("/api/myfeed")
	public List<NewsSnippet> myfeed(@RequestParam String username) {
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

	@GetMapping("/api/news/findone")
	public NewsSnippet getNews(@RequestParam int id) {
		Optional<NewsSnippet> x = newsSnippetRepository.findById(id);
		if (x.isPresent()) {
			return x.get();
		} else {
			NewsSnippet y = new NewsSnippet();
			y.setHeadline("News Not Found");
			return y;
		}
	}

	@PostMapping("/api/news/insert")
	public String insertNews(@RequestBody Object news) {
		NewsSnippet user1 = new NewsSnippet();
		user1.setHeadline(((java.util.LinkedHashMap) news).get("title").toString());
		user1.setImage_url(((java.util.LinkedHashMap) news).get("imgUrl").toString());
		user1.setSummary(((java.util.LinkedHashMap) news).get("desc").toString());
		user1.setSource_link(((java.util.LinkedHashMap) news).get("newsUrl").toString());
		user1.setFullStory(((java.util.LinkedHashMap) news).get("story").toString());

		News_owner x = new News_owner();
		List<User> user = userRepository.findUserByUsername(((java.util.LinkedHashMap) news).get("source").toString());
		if (user.size() > 0) {
			newsSnippetRepository.save(user1);

			x.setUser(user.get(0));
			x.setNews(user1);
			ownerRepository.save(x);
			return "successful";
		} else {
			return "unsuccessful";
		}

	}
	@PostMapping("/api/advert/insert")
	public String insertAdvert(@RequestBody Object ad) {

		Advertisement user1 = new Advertisement();
		user1.setFull_link(((java.util.LinkedHashMap) ad).get("full_link").toString());
		user1.setTitle(((java.util.LinkedHashMap) ad).get("title").toString());
		user1.setImg_url(((java.util.LinkedHashMap) ad).get("image_url").toString());
		advertRepository.save(user1);
		

		return "succcess";
	}

	@PostMapping("/api/news/update")
	public String updateNews(@RequestBody Object news) {

		Optional<NewsSnippet> x = newsSnippetRepository
				.findById(Integer.parseInt(((java.util.LinkedHashMap) news).get("id").toString()));
		if (x.isPresent()) {
			newsSnippetRepository.deleteById(Integer.parseInt(((java.util.LinkedHashMap) news).get("id").toString()));
			NewsSnippet user1 = new NewsSnippet();
			user1.setId(Integer.parseInt(((java.util.LinkedHashMap) news).get("id").toString()));
			user1.setHeadline(((java.util.LinkedHashMap) news).get("title").toString());
			user1.setImage_url(((java.util.LinkedHashMap) news).get("imgUrl").toString());
			user1.setSummary(((java.util.LinkedHashMap) news).get("desc").toString());
			user1.setSource_link(((java.util.LinkedHashMap) news).get("newsUrl").toString());
			user1.setFullStory(((java.util.LinkedHashMap) news).get("story").toString());
			newsSnippetRepository.save(user1);
			return "Successful";
		} else {
			return "news does not exist";
		}

	}

	@PostMapping("/api/user/update")
	public String updateUser(@RequestBody Object user) {

		Optional<User> x = userRepository
				.findById(Integer.parseInt(((java.util.LinkedHashMap) user).get("id").toString()));
		if (x.isPresent()) {
			newsSnippetRepository.deleteById(Integer.parseInt(((java.util.LinkedHashMap) user).get("id").toString()));
			User user1 = new User();
			user1.setId(Integer.parseInt(((java.util.LinkedHashMap) user).get("id").toString()));
			user1.setFirstname(((java.util.LinkedHashMap) user).get("firstname").toString());
			user1.setLastname(((java.util.LinkedHashMap) user).get("lastname").toString());
			user1.setUsername(((java.util.LinkedHashMap) user).get("username").toString());
			user1.setPassword(((java.util.LinkedHashMap) user).get("password").toString());
			user1.setEmail(((java.util.LinkedHashMap) user).get("email").toString());
			user1.setPreference(((java.util.LinkedHashMap) user).get("pref").toString());
			user1.setdType("reader");
			userRepository.save(user1);
			return "Successful";
		} else {
			return "news does not exist";
		}

	}
	

	@PostMapping("/api/contact/insert")
	public void insertContact(@RequestBody Object cont) {

		Contact user1 = new Contact();
		user1.setEmail(((java.util.LinkedHashMap) cont).get("email").toString());
		user1.setMessage(((java.util.LinkedHashMap) cont).get("message").toString());
		user1.setName(((java.util.LinkedHashMap) cont).get("name").toString());
		contactRepository.save(user1);

	}

	@GetMapping("/api/sportshome")
	public List<NewsSnippet> getSportsHome() {
		newsSnippetDao.fetchAndInsertSports();
		List<NewsSnippet> a = (List<NewsSnippet>) newsSnippetRepository.findNewsSnippetByCategory("Sport");
		return a;
	}

	@GetMapping("/api/entertainmenthome")
	public List<NewsSnippet> getEntertainmentHome() {
		newsSnippetDao.fetchAndInsertEntertainment();
		List<NewsSnippet> a = (List<NewsSnippet>) newsSnippetRepository.findNewsSnippetByCategory("Entertainment");
		return a;
	}

	@GetMapping("/api/sciencehome")
	public List<NewsSnippet> getSpringHome() {
		newsSnippetDao.fetchAndInsertScience();
		List<NewsSnippet> a = (List<NewsSnippet>) newsSnippetRepository.findNewsSnippetByCategory("Science");
		return a;
	}

	@GetMapping("/api/busniesshome")
	public List<NewsSnippet> getBusniessHome() {
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