package com.speed.kinship.controller;

import com.speed.kinship.model.Identity;
import com.speed.kinship.model.User;

public interface UserHandler {

	/**
	 * judge if the login legal or illegal
	 * @param username
	 * @param password
	 * @param identity
	 * @return
	 */
	public User login(String username, String password, Identity identity); 
	
	/**
	 * judge if the register legal or illegal
	 * @param username
	 * @param password
	 * @param identity
	 * @return
	 */
	public User register(String username, String password, Identity identity);
	
}
