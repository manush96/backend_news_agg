package com.newsaggregator.repositories;

import org.springframework.data.repository.CrudRepository;

import com.newsaggregator.models.Contact;

public interface ContactRepository extends CrudRepository<Contact, Integer>{

}
