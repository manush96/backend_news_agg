package com.newsaggregator.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.newsaggregator.models.Comment;
import com.newsaggregator.models.NewsSnippet;

public interface CommentRepository extends CrudRepository<Comment, Integer>{
	public List<Comment> findCommentByNews(NewsSnippet news);

}
