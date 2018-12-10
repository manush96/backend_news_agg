package com.newsaggregator.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Agency_Follwers {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	
	@ManyToOne()
	private User follower;
	
	@ManyToOne()
	private User agency;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getFollower() {
		return follower;
	}

	public void setFollower(User follower) {
		this.follower = follower;
	}

	public User getAgency() {
		return agency;
	}

	public void setAgency(User agency) {
		this.agency = agency;
	}
	
	
	
	
	
}
