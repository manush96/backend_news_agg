package com.newsaggregator.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.newsaggregator.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	public List<User> findUserByUsername(String username);
	public List<User> findUserByDType(String username);

}
