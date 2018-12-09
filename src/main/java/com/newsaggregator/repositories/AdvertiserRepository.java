package com.newsaggregator.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.newsaggregator.models.Advertisement;
import com.newsaggregator.models.Advertiser;

public interface AdvertiserRepository extends CrudRepository<Advertiser, Integer> {
	

}
