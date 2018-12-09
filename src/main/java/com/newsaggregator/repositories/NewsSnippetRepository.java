package com.newsaggregator.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.newsaggregator.models.NewsSnippet;




public interface NewsSnippetRepository extends CrudRepository<NewsSnippet, Integer> {
	
	public List<NewsSnippet> findNewsSnippetByCategory(String CategoryName);
	
	@Query(value = "select * from news_owner left join news_snippet on news_snippet.id = news_owner.news_id", nativeQuery = true)
	public List<NewsSnippet> getNewsSnippetByAgency();
}
