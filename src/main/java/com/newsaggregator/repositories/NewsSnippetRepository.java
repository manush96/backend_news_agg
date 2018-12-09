package com.newsaggregator.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.newsaggregator.models.NewsSnippet;

import edu.northeastern.cs5200.models.Enrollment;



public interface NewsSnippetRepository extends CrudRepository<NewsSnippet, Integer> {
	public List<NewsSnippet> findNewsSnippetByCategory(String CategoryName);
	@Query(value = "Select * from NewsSnippet join News_owner Like :StudentId", nativeQuery = true)
	List<NewsSnippet> getNewsSnippetByAgency(@Param("StudentId") int StudentId);
}
