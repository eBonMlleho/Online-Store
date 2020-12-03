package com.example.demo.model;



import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "items") 
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	private String title;
	private String description;
	private double price;
	private Integer userid;
	private Integer isSold=0;
	private String comments;
	private Integer buyerid;
	
	public Item() {
		// TODO Auto-generated constructor stub
		this.isSold=0;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getIsSold() {
		return isSold;
	}

	public void setIsSold(Integer isSold) {
		this.isSold = isSold;
	}

	public String getComments(){return comments;}

	public void setComments(String comments){this.comments=comments;}

	public Integer getBuyerid(){return buyerid;}

	public void setBuyerid(Integer buyerid){this.buyerid=buyerid;}


}
