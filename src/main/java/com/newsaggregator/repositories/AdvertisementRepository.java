package com.newsaggregator.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.newsaggregator.models.Advertisement;

public interface AdvertisementRepository extends CrudRepository<Advertisement, Integer>{
	@Modifying(clearAutomatically = true)
	@Query(value = "update advertisement set full_link = :full_link,image_url= :img_url,title= :title where id = :id", nativeQuery = true)
	public void updateAdvertiser(@Param("full_link") String full_link,@Param("img_url") String img_url,@Param("title") String title,@Param("id") int id);
}
