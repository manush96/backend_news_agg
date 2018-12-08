package com.newsaggregator.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.newsaggregator.models.Admin;
import com.newsaggregator.models.User;

public interface AdminRepository extends CrudRepository<Admin, Integer>{
	public List<Admin> findAdminByUsername(String username);

}
