package com.newsaggregator.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.newsaggregator.models.NewsSnippet;



@Transactional	
public interface NewsSnippetRepository extends JpaRepository<NewsSnippet, Integer> {
	
	public List<NewsSnippet> findNewsSnippetByCategory(String CategoryName);
	
	@Query(value = "select news_snippet.* from news_owner join news_snippet on news_snippet.id = news_owner.news_id where news_owner.user_id = (select id from user where username = :lastName);", nativeQuery = true)
	public List<NewsSnippet> getNewsSnippetByAgency(@Param("lastName") String username);
	
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update news_snippet set headline = :headline,image_url= :imgageurl,summary= :summary,source_link = :sourcelink ,full_story= :fullstory where id = :id", nativeQuery = true)
	public void updateNewsSnippet(@Param("headline") String headline,@Param("imgageurl") String imgageurl,@Param("summary") String summary,@Param("sourcelink") String source_link,@Param("fullstory") String fullstory,@Param("id") int id);
}
