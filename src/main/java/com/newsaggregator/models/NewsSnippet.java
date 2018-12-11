package com.newsaggregator.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="news_snippet")
public class NewsSnippet {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String headline;
	private String summary;
	private String image_url;
	private Date publish_time;
	private String source_link;
	private String category;
	private String fullStory;
	
	@OneToMany(mappedBy = "news", cascade = CascadeType.REMOVE)
	private List<News_owner> publisher;
	
	
	@OneToMany(mappedBy="news",cascade = CascadeType.REMOVE,fetch=FetchType.EAGER)
	private List<Comment> comments;
	
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public String getSource_link() {
		return source_link;
	}
	public void setSource_link(String source_link) {
		this.source_link = source_link;
	}
	public Date getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(Date publish_time) {
		this.publish_time = publish_time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFullStory() {
		return fullStory;
	}
	public void setFullStory(String fullStory) {
		this.fullStory = fullStory;
	}
	
}
