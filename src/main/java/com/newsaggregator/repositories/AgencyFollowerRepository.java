package com.newsaggregator.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.newsaggregator.models.Agency_Follwers;
import com.newsaggregator.models.User;

public interface AgencyFollowerRepository extends CrudRepository<Agency_Follwers, Integer>{
	public List<Agency_Follwers> findAgency_FollwerByAgency(User agency);
}
