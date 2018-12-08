package com.newsaggregator.repositories;

import org.springframework.data.repository.CrudRepository;

import com.newsaggregator.models.Admin;

public interface AdminRepository extends CrudRepository<Admin, Integer>{

}
