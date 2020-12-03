package com.example.demo.dao;

import com.example.demo.model.Item;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Cart;

import java.util.List;

public interface CartRepository extends CrudRepository<Cart,Long>{
	Cart findById(Integer id);
	public List<Cart> findByUserid(Integer userid);
	Cart findByItemid(Integer itemid);
}
