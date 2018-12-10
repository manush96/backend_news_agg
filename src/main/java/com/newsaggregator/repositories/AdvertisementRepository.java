package com.newsaggregator.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.newsaggregator.models.Advertisement;
import com.newsaggregator.models.NewsSnippet;



@Transactional
public interface AdvertisementRepository extends CrudRepository<Advertisement, Integer>{
	
	
	@Query(value = "select advertisement.* from advertiser join advertisement on advertisement.id = advertiser.ad_id where advertiser.user_id = (select id from user where username = :lastName);", nativeQuery = true)
	public List<Advertisement> getAdvertByAdvertiser(@Param("lastName") String username);
	
	
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update advertisement set full_link = :full_link,img_url= :img_url,title= :title where id = :id", nativeQuery = true)
	public void updateAdvertiser(@Param("full_link") String full_link,@Param("img_url") String img_url,@Param("title") String title,@Param("id") int id);
}
