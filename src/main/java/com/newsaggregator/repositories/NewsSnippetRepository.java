package com.newsaggregator.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.newsaggregator.models.NewsSnippet;




public interface NewsSnippetRepository extends CrudRepository<NewsSnippet, Integer> {
	
	public List<NewsSnippet> findNewsSnippetByCategory(String CategoryName);
	
	@Query(value = "select news_snippet.* from news_owner left join news_snippet on news_snippet.id = news_owner.news_id where news_owner.user_id = (select id from user where username = :lastName);", nativeQuery = true)
	public List<NewsSnippet> getNewsSnippetByAgency(@Param("lastName") String username);
}
