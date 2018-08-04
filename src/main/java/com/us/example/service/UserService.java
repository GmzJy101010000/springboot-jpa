package com.us.example.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.data.domain.Page;

import com.us.example.bean.User;

/**
 * The Interface UserService.
 */
public interface UserService {

	/**
	 * Gets the user by name.
	 *
	 * @param username the user name
	 * @return the user by name
	 */
	
	public User getUserByName(String username);

	Page<User> findAll(User user,String page,String size);
	
	
	
}
