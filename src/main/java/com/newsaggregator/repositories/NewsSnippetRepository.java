package com.newsaggregator.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.newsaggregator.models.NewsSnippet;



public interface NewsSnippetRepository extends CrudRepository<NewsSnippet, Integer> {
	public List<NewsSnippet> findNewsSnippetByCategory(String CategoryName);
}
