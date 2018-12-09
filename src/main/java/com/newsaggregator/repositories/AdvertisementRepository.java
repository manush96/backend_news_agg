package com.newsaggregator.repositories;

import org.springframework.data.repository.CrudRepository;

import com.newsaggregator.models.Advertisement;

public interface AdvertisementRepository extends CrudRepository<Advertisement, Integer>{

}
