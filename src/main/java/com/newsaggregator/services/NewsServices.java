package com.newsaggregator.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
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
import com.newsaggregator.models.Admin;
import com.newsaggregator.models.Advertisement;
import com.newsaggregator.models.Advertiser;
import com.newsaggregator.models.Agency_Follwers;
import com.newsaggregator.models.Contact;
import com.newsaggregator.models.NewsSnippet;
import com.newsaggregator.models.News_owner;
import com.newsaggregator.models.User;
import com.newsaggregator.repositories.AdminRepository;
import com.newsaggregator.repositories.AdvertisementRepository;
import com.newsaggregator.repositories.AdvertiserRepository;
import com.newsaggregator.repositories.AgencyFollowerRepository;
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
	@Autowired
	AdvertiserRepository advertiserRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	
	@Autowired
	AgencyFollowerRepository afRepo;
	

	@GetMapping("/api/newshome")
	public List<NewsSnippet> getNewsHome() {
		newsSnippetDao.fetchAndInsertHeadlines();
		List<NewsSnippet> a = (List<NewsSnippet>) newsSnippetRepository.findAll();
		Collections.reverse(a);
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
		Admin user = adminRepository.findAdminByUsername(username).get(0);
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
	@GetMapping("/api/user/findAllAgency")
	public List<User> findAllAgency() {
		List<User> x = userRepository.findUserByDType("agency");
		return x;
	}
	
	
	@GetMapping("/api/user/findAgencyByUser")
	public List<User> findAgencyByUser(@RequestParam String username){
		User us = userRepository.findUserByUsername(username).get(0);
		List<Agency_Follwers> afs = afRepo.findAgency_FollwerByFollower(us);
		List<User> agen= new ArrayList<User>();
		for(Agency_Follwers a:afs) {
			agen.add(a.getAgency());
		}
		return agen;
	}
	
	
	
	
	@GetMapping("/api/findallusers")
	public List<User> adminSearch() {
		List<User> users = (List<User>) userRepository.findAll();
		return users;
	}
	@GetMapping("/api/advert/findByAdvertiser")
	public List<Advertisement> advertSearch(@RequestParam String username) {
		List<Advertisement> c = advertRepository.getAdvertByAdvertiser(username);
		if (c.size() > 0) {
			Collections.reverse(c);
			return c;
		} else {
			c = new ArrayList<Advertisement>();
			Advertisement e = new Advertisement();
			e.setTitle("There is no data here!!");
			c.add(e);
			return c;
		}
	}
	
	@GetMapping("/api/agency/findByAgency")
	public List<NewsSnippet> newsSearchByAgency(@RequestParam String username) {
		List<NewsSnippet> c = newsSnippetRepository.getNewsSnippetByAgency(username);
		if (c.size() > 0) {
			Collections.reverse(c);
			return c;
		} else {
			c = new ArrayList<NewsSnippet>();
			NewsSnippet e = new NewsSnippet();
			e.setHeadline("There is no data here!!");
			c.add(e);
			return c;
		}
	}

	@GetMapping("/api/advert/find")
	public List<Advertisement> findAdvert() {
		List<Advertisement> x = (List<Advertisement>) advertRepository.findAll();
		if (x.size() > 0) {
			List<Advertisement> c = new ArrayList<>();
			Random rand = new Random();
			for (int j = 0; j < 5; j++) {
				c.add(x.get(rand.nextInt(x.size())));
			}
			return c;
		} else {
			Advertisement a = new Advertisement();
			a.setTitle("there are no advertisements");
			x.add(a);
			return x;
		}
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

	// End of Find Endpoints
	// All Insert Endpoints.
	@PostMapping(path = "/api/registration", consumes = "application/json")
	public void registration(@RequestBody Object user) {
		User user1 = new User();
		user1.setFirstname(((java.util.LinkedHashMap) user).get("firstname").toString());
		user1.setLastname(((java.util.LinkedHashMap) user).get("lastname").toString());
		user1.setUsername(((java.util.LinkedHashMap) user).get("username").toString());
		user1.setPassword(((java.util.LinkedHashMap) user).get("password").toString());
		user1.setPreference(((java.util.LinkedHashMap) user).get("preference").toString());
		user1.setEmail(((java.util.LinkedHashMap) user).get("email").toString());
		userRepository.save(user1);
	}

	@PostMapping("/api/news/insert")
	public String insertNews(@RequestBody Object news) {
		NewsSnippet user1 = new NewsSnippet();
		user1.setId(1);
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
		Advertiser x = new Advertiser();
		List<User> user = userRepository.findUserByUsername(((java.util.LinkedHashMap) ad).get("source").toString());
		if (user.size() > 0) {
			advertRepository.save(user1);
			x.setUser(user.get(0));
			x.setAd(user1);
		
			advertiserRepository.save(x);
			return "successful";
		} else {
			return "unsuccessful";
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
	@GetMapping("/api/Agency/insert")
	public void insertFollowers(@RequestParam String s,@RequestParam String t) {
		List<User> c = userRepository.findUserByUsername(s);
		if(c.size()>0) {
			String []x = t.split(",");
			for (int i = 0; i < x.length; i++) {
				User u1= userRepository.findById(Integer.parseInt(x[i])).get();
				Agency_Follwers a= new Agency_Follwers();
				a.setAgency(u1);
				a.setFollower(c.get(0));
				afRepo.save(a);
			}	
		}
	}
	// End of Insert Endpoints
	// Start of Update Endpoints
	@PostMapping("/api/news/update")
	public String updateNews(@RequestBody Object news) {
		Optional<NewsSnippet> x = newsSnippetRepository
				.findById(Integer.parseInt(((java.util.LinkedHashMap) news).get("id").toString()));
		if (x.isPresent()) {
			LinkedHashMap l = new LinkedHashMap<>();
			l = (LinkedHashMap)news;
			newsSnippetRepository.updateNewsSnippet(l.get("title").toString(), l.get("imgUrl").toString(),l.get("desc").toString(), l.get("newsUrl").toString(), l.get("story").toString(), Integer.parseInt(l.get("id").toString()));
			return "successful";
		}else {
			return "News does not exist";
		}
	}

	@PostMapping("/api/user/update")
	public String updateUser(@RequestBody Object user) {
		Optional<User> x = userRepository
				.findById(Integer.parseInt(((java.util.LinkedHashMap) user).get("id").toString()));
		if (x.isPresent()) {
			userRepository.deleteById(Integer.parseInt(((java.util.LinkedHashMap) user).get("id").toString()));
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
			return "User does not exist";
		}
	}
	@PostMapping("/api/advert/update")
	public String updateAdvertisment(@RequestBody Object advertisement) {
		Optional<Advertisement> x = advertRepository
				.findById(Integer.parseInt(((java.util.LinkedHashMap) advertisement).get("id").toString()));
		if (x.isPresent()) {
			LinkedHashMap l = new LinkedHashMap<>();
			l = (LinkedHashMap)advertisement;
			advertRepository.updateAdvertiser(l.get("full_link").toString(), l.get("image_url").toString(), l.get("title").toString(), Integer.parseInt(l.get("id").toString()));
			return "Successful";
		} else {
			return "news does not exist";
		}
	}
	// End of Update Endpoints
	// Start of Delete Endpoints
	@GetMapping("/api/deleteuser")
	public void deleteUser(@RequestParam int id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent())
			userRepository.delete(user.get());
	}
	@RequestMapping(value = "/api/api/user/unfollow", method = RequestMethod.DELETE)
	public void deleteFollower(@RequestParam("user_id") String username,@RequestParam int agency_id) {

		User us= userRepository.findUserByUsername(username).get(0);
		User ag = userRepository.findById(agency_id).get();
		List<Agency_Follwers> af = afRepo.findAgency_FollwerByAgency(ag);
		for(Agency_Follwers a:af) {
			if(a.getFollower().getId() == us.getId()) {
				afRepo.delete(a);
			}
		}
		
	
	}
	
	@RequestMapping(value = "/api/deleteNews/{id}", method = RequestMethod.DELETE)
	public void deleteNews(@PathVariable("id") int itemId) {
		newsSnippetRepository.deleteById(itemId);
	}
	@RequestMapping(value = "/api/deleteAdvert/{id}", method = RequestMethod.DELETE)
	public void deleteAdvert(@PathVariable("id") int itemId) {
		advertRepository.deleteById(itemId);
	}
	@GetMapping("/api/user/deleteself")
	public void deleteUser(@RequestParam String username,@RequestParam int id) {
		Optional<User> agency = userRepository.findById(id);
		if (agency.isPresent()) {
			User user = userRepository.findUserByUsername(username).get(0);
			List<Agency_Follwers> af = afRepo.findAgency_FollwerByAgency(agency.get());
			for(Agency_Follwers f:af)
			{
				if(f.getFollower().getId() == user.getId())
				{
					afRepo.delete(f);
				}
			}
			
			
		}
			
	}
	// End of Delete Endpoints
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
				Collections.reverse(a);
				for (int j = 0; j < 5; j++) {
					x.add(a.get(j));
				}
				break;
			case "Entertainment":
				newsSnippetDao.fetchAndInsertEntertainment();
				a = (List<NewsSnippet>) newsSnippetRepository.findNewsSnippetByCategory("Entertainment");
				for (int j = 0; j < 5; j++) {
					x.add(a.get(j));
				}
				break;
			case "Science":
				newsSnippetDao.fetchAndInsertScience();
				a = (List<NewsSnippet>) newsSnippetRepository.findNewsSnippetByCategory("Science");
				for (int j = 0; j < 5; j++) {
					x.add(a.get(j));
				}
				break;
			default:
				break;
			}
		}
		return x;
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
	
	
	@GetMapping("/api/advert/findOne")
	public Advertisement findOne(@RequestParam int id)
	{
		Advertisement user = new Advertisement();
		user = (Advertisement) advertRepository.findById(id).get();
		return user;
	}

	private List<String> parsePreference(String s) {
		String sa[] = s.split(",");
		return Arrays.asList(sa);
	}
}