package com.newsaggregator.repositories;

import org.springframework.data.repository.CrudRepository;

import com.newsaggregator.models.Advertisement;
import com.newsaggregator.models.Advertiser;

public interface AdvertiserRepository extends CrudRepository<Advertiser, Integer> {

}
