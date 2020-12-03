package com.example.demo.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Item;

public interface ItemRepository extends CrudRepository<Item,Long>{
	
	Item findById(Integer id);
//	@Query("select i from Item i where i.title like %:key%")
	public List<Item> findByTitleOrDescriptionIgnoreCaseContaining(String key1,String key2);
	public List<Item> findAllByTitleLike(String key);
	public List<Item> findByUserid(Integer id);
	public List<Item> findByIsSold(Integer id);
}
