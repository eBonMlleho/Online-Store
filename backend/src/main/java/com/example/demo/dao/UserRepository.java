package com.example.demo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.User;


public interface UserRepository extends CrudRepository<User,Long>{
	User findByEmail(String email);
	User findById(Integer id);
}
